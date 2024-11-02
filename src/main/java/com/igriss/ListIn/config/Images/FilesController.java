
package com.igriss.ListIn.config.Images;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.*;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class FilesController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> file, ArrayList<String> id) {
        id.add(UUID.randomUUID().toString());
        id.add(UUID.randomUUID().toString());
        return ResponseEntity.ok(s3Service.uploadFile(id, file));
    }

    @GetMapping("/download")
    public ResponseEntity<String> getFileUrl(@RequestParam("id") String id) {
        return ResponseEntity.ok(s3Service.getFileUrl(id).toString());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("id") String id) {

        s3Service.deleteFiles(Collections.singletonList(id));

        return ResponseEntity.ok("File deleted successfully");
    }
}