package com.igriss.ListIn.config.Images;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Adjust thread pool size as needed

    //todo -> handle the case where S3 is out of memory
    public List<String> uploadFile(List<MultipartFile> files) {
        log.info("Starting sequential stream");
        return files.stream()
                .map(file -> {
                    log.info("Generating URL of file {}", file.getOriginalFilename());
                    String fileId = UUID.randomUUID() + "_" + System.currentTimeMillis();
                    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                    String fileName = fileId + "." + ext;
                    String fileUrl = String.format("%s/%s", bucketLink, fileName);

                    log.info("File URL created {}", fileUrl);

                    // Submit the async task to the executor
                    submitAsyncTask(fileName, file);

                    return fileUrl;
                }).collect(Collectors.toList());
    }

    private void submitAsyncTask(String fileName, MultipartFile file) {
        executorService.submit(() -> {
            try {
                log.info("Compressing file {}", file.getOriginalFilename());
                MultipartFile compressedFile = FileCompressor.compressFile(file);
                log.info("File compressed {}", compressedFile.getOriginalFilename());

                log.info("Uploading file {}", compressedFile.getOriginalFilename());
                saveFiles(fileName, compressedFile);
                log.info("Uploaded file {}", compressedFile.getOriginalFilename());
            } catch (Exception e) {
                log.error("Error processing file {}: {}", file.getOriginalFilename(), e.getMessage());
            }
        });
    }

    private void saveFiles(String fileName, MultipartFile file) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .cacheControl(cached)
                            .contentDisposition("inline")
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Make sure to shut down the executor service properly to avoid memory leaks
    @PreDestroy
    public void shutdownExecutor() {
        try {
            log.info("Shutting down executor service...");
            executorService.shutdown();
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Error shutting down executor: {}", e.getMessage());
            executorService.shutdownNow();
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
    public void deleteFiles(List<String> fileNames) {
        fileNames.forEach(fileName -> {
            DeleteObjectRequest request = DeleteObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.deleteObject(request);
        });
    }
}