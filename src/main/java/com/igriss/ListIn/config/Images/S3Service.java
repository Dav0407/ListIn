package com.igriss.ListIn.config.Images;

import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.bucket.link}")
    private String bucketLink;

    public List<Map<String, String>> uploadFile(UUID uuid, List<MultipartFile> files) {
        return files.stream().map(file->{

            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = uuid + "_" + System.currentTimeMillis() + "." +ext;
            try {

                s3Client.putObject(PutObjectRequest.builder()
                                .bucket(bucketName)
                                .bucket(bucketName)
                                .key(fileName)
                                .contentType(file.getContentType())
                                .cacheControl("public, max-age=2592000")
                                .build(),
                        RequestBody.fromInputStream(file.getInputStream(),file.getSize())
                        );
                String fileUrl = String.format("%s/%s", bucketLink, fileName);
                return Collections.singletonMap("fileName", fileUrl);

            } catch (IOException e) {

                throw new RuntimeException(e);

            }
        }).collect(Collectors.toList());
    }

    public List<String> getFileUrl(UUID uuid) {

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(uuid + "_")
                .build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response
                .contents()
                .stream()
                .map(s3Object -> String.format("%s/%s", bucketLink, s3Object.key()))
                .collect(Collectors.toList());
    }

    public void deleteFile(UUID postId) {

        DeleteObjectRequest request = DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(postId.toString())
                .build();
        s3Client.deleteObject(request);
    }
}