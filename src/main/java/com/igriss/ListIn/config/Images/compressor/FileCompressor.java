package com.igriss.ListIn.config.Images.compressor;

import com.igriss.ListIn.config.Images.CompressedMultipartFile;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;


@Slf4j
public class FileCompressor {

    public static MultipartFile compressFile(MultipartFile file) {
        return switch (Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase()) {
            case "jpg", "jpeg", "png" -> compressImage(file);
            case "mkv", "mp4", "mov" -> compressVideo(file);
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

        } catch (IOException e) {
            log.error("Image Compress Failed Exception {}", e.getMessage());
        }

        return convertIntoMultipart(file, outputStream);
    }

    public static MultipartFile compressVideo(MultipartFile videoFile) {
        byte[] videoBytes;
        try {
            videoBytes = videoFile.getBytes();
        } catch (IOException e) {
            log.error("Can not get bytes from video file: {},{}", videoFile, e.getMessage());
            throw new RuntimeException(e);
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(videoBytes);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
             FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                     outputStream,
                     grabber.getImageWidth(),
                     grabber.getImageHeight(),
                     grabber.getAudioChannels())) {

            grabber.start();

            // Set compression parameters
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setVideoBitrate(grabber.getVideoBitrate() / 2);
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setGopSize((int) grabber.getFrameRate() * 2);

            // Preserve audio if present
            recorder.setAudioChannels(grabber.getAudioChannels());
            recorder.setAudioBitrate(grabber.getAudioBitrate());
            recorder.setSampleRate(grabber.getSampleRate());

            recorder.start();

            // Process frames
            Frame frame;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }

            return convertIntoMultipart(videoFile, outputStream);
        } catch (IOException e) {
            log.error("Video Compress Failed Exception {}", e.getMessage());
        }

        return videoFile;
    }

    private static MultipartFile convertIntoMultipart(MultipartFile file, ByteArrayOutputStream fileContent) {
        return new CompressedMultipartFile(
                file.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                fileContent.toByteArray());
    }
}