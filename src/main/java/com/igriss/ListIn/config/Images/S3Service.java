package com.igriss.ListIn.config.Images;

import io.netty.util.concurrent.CompleteFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.bucket.link}")
    private String bucketLink;

    @Value("${cloud.aws.s3.cache-control}")
    private String cached;
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    public List<String> uploadFile(List<String> uuids, List<MultipartFile> files) {
        List<String> urls =new ArrayList<>();

        for (int i=0;i<files.size();i++) {
            var file = files.get(i);
            String id = uuids.get(i);
            log.info("generated uuid: {}", id);
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = id + "." + ext;
            urls.add(String.format("%s/%s", bucketLink, fileName));
            CompletableFuture.runAsync(()->saveFiles(fileName,file),executorService);
        }
        return urls;
    }


    @Async
    public void saveFiles(String fileName, MultipartFile file){
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .cacheControl(cached)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFileUrl(String uuid) {

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(uuid)
                .build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response
                .contents()
                .stream()
                .map(s3Object -> String.format("%s/%s", bucketLink, s3Object.key()))
                .collect(Collectors.toList());
    }

    @Async
    public void deleteFiles(List<String> id) {
        id.forEach(uuid -> {
            DeleteObjectRequest request = DeleteObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(uuid)
                    .build();
            s3Client.deleteObject(request);
        });
    }
}