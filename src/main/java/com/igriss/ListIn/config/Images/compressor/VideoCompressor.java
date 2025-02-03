package com.igriss.ListIn.config.Images.compressor;

import com.igriss.ListIn.config.Images.CompressedMultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;

@Slf4j
public class VideoCompressor {

    public static MultipartFile compressVideo(MultipartFile videoFile) throws Exception {
        // Create temporary files for processing
        File tempInputFile = File.createTempFile("input", ".mp4");
        File tempOutputFile = File.createTempFile("output", ".mp4");

        try {
            // Write input video to temp file
            try (FileOutputStream fos = new FileOutputStream(tempInputFile)) {
                fos.write(videoFile.getBytes());
            }

            // Initialize grabber and recorder
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(tempInputFile);
            FFmpegFrameRecorder recorder = null;

            try {
                grabber.start();

                // Configure recorder
                recorder = new FFmpegFrameRecorder(
                        tempOutputFile,
                        grabber.getImageWidth(),
                        grabber.getImageHeight(),
                        grabber.getAudioChannels()
                );

                // Set compression parameters
                recorder.setVideoCodec("h264");
                recorder.setFormat("mp4");
                recorder.setVideoBitrate(grabber.getVideoBitrate() / 2); // Reduce bitrate by half
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
            } finally {
                grabber.stop();
                grabber.release();

                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                }
            }

            // Read the compressed file and create MultipartFile
            byte[] compressedBytes = Files.readAllBytes(tempOutputFile.toPath());

            return CompressedMultipartFile.builder()
                    .name(videoFile.getName())
                    .originalFilename(videoFile.getOriginalFilename())
                    .content(compressedBytes)
                    .contentType(videoFile.getContentType())
                    .build();

        } finally {
            // Cleanup temporary files
            log.info("Temporary Input file deleted: {}", tempInputFile.delete());
            log.info("Temporary Output file deleted: {}", tempOutputFile.delete());
        }
    }
}