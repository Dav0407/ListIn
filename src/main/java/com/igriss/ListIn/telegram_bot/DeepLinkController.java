package com.igriss.ListIn.telegram_bot;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deeplink")
public class DeepLinkController {

    @GetMapping
    public ResponseEntity<Void> handleDeepLink(@RequestParam String token, HttpServletRequest request) {
        String appLink = "com.listIn.marketplace://register?token=" + token; // Custom scheme for native app

        // Redirect to app if installed, otherwise go to fallback
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, appLink)
                .build();
    }
}
