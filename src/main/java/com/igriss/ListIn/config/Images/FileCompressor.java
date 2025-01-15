package com.igriss.ListIn.config.Images;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;


@Slf4j
public class FileCompressor {

    public static MultipartFile compressFile(MultipartFile file){
        return switch (Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase()) {
            case "jpg", "jpeg", "png" ->
                    compressImage(file);
            case "mkv","mp4", "mov" ->
                    compressVideo(file);
            default -> file;
        };
    }

    private static MultipartFile compressImage(MultipartFile file) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(file.getInputStream())
                    .scale(1)
                    .outputFormat("jpg")
                    .outputQuality(0.5)
                    .toOutputStream(outputStream);
        }catch (IOException e){
             log.error("Image Compress Failed Exception {}",e.getMessage());
        }

        return convertIntoMultipart(file,outputStream);
    }

    private static MultipartFile compressVideo(MultipartFile file){
        return file;
    }

    private static MultipartFile convertIntoMultipart(MultipartFile file, ByteArrayOutputStream fileContent) {
        return new CompressedMultipartFile(
                file.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                fileContent.toByteArray());
    }
}