
package com.igriss.ListIn.config.Images;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class FilesController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(s3Service.uploadFile(file));
    }
}