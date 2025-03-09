package com.igriss.ListIn.config.Images.compressor;

import com.igriss.ListIn.config.Images.CompressedMultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;


@Slf4j
public class FileCompressor {

    public static MultipartFile compressFile(MultipartFile file) throws IOException {
        return switch (Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase()) {
            case "jpg", "jpeg", "png" -> file;
            case "mkv", "mp4", "mov" -> file;
            default -> file;
        };
    }


    /*private static MultipartFile compressImage(MultipartFile file) {
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


    public static MultipartFile compressVideo(MultipartFile inputFile) throws IOException {
        // Convert MultipartFile to a temporary File
        File tempInputFile = File.createTempFile("input-", ".mp4");
        inputFile.transferTo(tempInputFile);

        // Use a ByteArrayOutputStream for the compressed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(tempInputFile)) {
            grabber.start(); // Start the grabber before using its properties

            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                    outputStream,
                    grabber.getImageWidth(),
                    grabber.getImageHeight(),
                    grabber.getAudioChannels())) {

                recorder.setFormat("mp4");
                // Use the software-based x265 encoder
                recorder.setVideoCodecName("libx265");
                recorder.setVideoBitrate(500000); // Adjust bitrate as needed
                recorder.setFrameRate(grabber.getFrameRate());
                recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
                // Audio settings
                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
                recorder.setAudioBitrate(128000);
                recorder.setSampleRate(grabber.getSampleRate());

                // Set movflags to allow non-seekable output (fragmented MP4)
                recorder.setOption("movflags", "frag_keyframe+empty_moov");

                recorder.start();

                Frame frame;
                while ((frame = grabber.grabFrame()) != null) {
                    recorder.record(frame);
                }

                recorder.stop();
            }
            grabber.stop();
        } catch (Exception e) {
            throw new RuntimeException("Error compressing video: " + e.getMessage(), e);
        } finally {
            tempInputFile.delete(); // Clean up the temporary file
        }

        // Convert the ByteArrayOutputStream to MultipartFile using your helper method
        return convertIntoMultipart(inputFile, outputStream);
    }
*/

    private static MultipartFile convertIntoMultipart(MultipartFile file, ByteArrayOutputStream fileContent) {
        return new CompressedMultipartFile(
                file.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                fileContent.toByteArray());
    }
}