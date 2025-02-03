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
            log.error("Cannot get bytes from video file {}: {}", videoFile.getOriginalFilename(), e.getMessage());
            throw new RuntimeException("Failed to read video file bytes", e);
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(videoBytes);
             FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream)) {

            grabber.start();

            // Retrieve video properties after starting the grabber.
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            int audioChannels = grabber.getAudioChannels();
            int videoBitrate = grabber.getVideoBitrate();
            double frameRate = grabber.getFrameRate();
            int gopSize = (int) (frameRate * 2);

            // Create an output stream for the compressed video.
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream, width, height, audioChannels)) {

                // Set compression parameters
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setFormat("mp4");
                // For non-seekable output (ByteArrayOutputStream), use fragmented MP4 flags.
                recorder.setOption("movflags", "frag_keyframe+empty_moov");
                recorder.setVideoBitrate(videoBitrate > 0 ? videoBitrate / 2 : 800 * 1000); // fallback if bitrate is 0
                recorder.setFrameRate(frameRate);
                recorder.setGopSize(gopSize);

                // Preserve audio if present
                if (audioChannels > 0) {
                    recorder.setAudioChannels(audioChannels);
                    recorder.setAudioBitrate(grabber.getAudioBitrate());
                    recorder.setSampleRate(grabber.getSampleRate());
                }

                recorder.start();

                // Process all frames
                Frame frame;
                while ((frame = grabber.grab()) != null) {
                    recorder.record(frame);
                }

                // Stop the recorder and grabber to flush buffers and finalize the output.
                recorder.stop();
                grabber.stop();

                return convertIntoMultipart(videoFile, outputStream);
            }
        } catch (IOException e) {
            log.error("Video compression failed: {}", e.getMessage());
            throw new RuntimeException("Video compression failed", e);
        }
    }


    private static MultipartFile convertIntoMultipart(MultipartFile file, ByteArrayOutputStream fileContent) {
        return new CompressedMultipartFile(
                file.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                fileContent.toByteArray());
    }
}