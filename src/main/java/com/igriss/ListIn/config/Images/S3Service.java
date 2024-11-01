package com.igriss.ListIn.config.Images;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    public List<Map<String, String>> uploadFile(List<String> uuids, List<MultipartFile> files) {
        return IntStream.range(0, files.size())
                .mapToObj(i -> {
                    var file = files.get(i);
                    String id = uuids.get(i);

                    log.info("generated uuid: {}", id);
                    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                    String fileName = id + "." + ext;

                    try {
                        s3Client.putObject(PutObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(fileName)
                                        .contentType(file.getContentType())
                                        .cacheControl(cached)
                                        .build(),
                                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                        );
                        String fileUrl = String.format("%s/%s", bucketLink, fileName);
                        return Collections.singletonMap("fileName", fileUrl);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
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

    public void deleteFile(String id) {

        DeleteObjectRequest request = DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(id)
                .build();
        s3Client.deleteObject(request);
    }
}