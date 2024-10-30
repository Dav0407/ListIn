
package com.igriss.ListIn.config.Images;

import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class FilesController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> file,UUID postId, UUID userId) throws IOException {

        return ResponseEntity.ok(s3Service.uploadFile(postId,file));
    }

    @GetMapping("/download")
    public ResponseEntity<String> getFileUrl(@RequestParam("user") UUID user) {
        return ResponseEntity.ok(s3Service.getFileUrl(user).toString());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("file") UUID fileName) {
        s3Service.deleteFile(fileName);
        return ResponseEntity.ok("File deleted successfully");
    }
}