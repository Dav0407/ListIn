package com.igriss.ListIn.security.email_verification;

import com.igriss.ListIn.security.security_dto.EmailVerificationRequestDTO;
import com.igriss.ListIn.exceptions.EmailNotFoundException;
import com.igriss.ListIn.exceptions.LoadHTMLTemplateFailedException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final long CODE_EXPIRATION_MINUTES = 5;
    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;
    private final ConcurrentHashMap<String, CodeDetails> verificationCodes = new ConcurrentHashMap<>();

    @Async
    public void sendVerificationEmail(EmailVerificationRequestDTO user) throws EmailNotFoundException {

        String code = generateVerificationCode();
        storeVerificationCode(user.getEmail(), code);

        var mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage;

        try {
            log.warn("MimeMessageHelper");

            mailMessage = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            log.warn("getEmail");

            mailMessage.setTo(user.getEmail());
            log.warn("setSubject");

            mailMessage.setSubject("Email Verification");
            log.warn("loadHTMLTemplate");

            mailMessage.setText(loadHTMLTemplate(code), true);
            log.warn("setFrom");

            mailMessage.setFrom("ListIn verification <noreply@chat.com>");

            log.warn("send");

            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {

            throw new EmailNotFoundException("Failed to send email");
        }
    }

    public boolean verifyCode(String email, String code) {

        CodeDetails codeDetails = verificationCodes.get(email);

        if (codeDetails != null && !isCodeExpired(codeDetails) && code.equals(codeDetails.code())) {

            verificationCodes.remove(email);

            return true;
        }

        return false;
    }

    private boolean isCodeExpired(CodeDetails codeDetails) {

        return codeDetails.generatedTime().plusMinutes(CODE_EXPIRATION_MINUTES).isBefore(LocalDateTime.now());
    }

    private void storeVerificationCode(String email, String code) {

        verificationCodes.put(email, new CodeDetails(code, LocalDateTime.now()));

        Executors.newSingleThreadScheduledExecutor().schedule(() -> verificationCodes.remove(email), CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
    }

    private String loadHTMLTemplate(String code) throws LoadHTMLTemplateFailedException {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/email_template.html");
            log.warn("Resource exists: " + resource.exists());
            log.warn("Resource description: " + resource.getDescription());

            try (InputStream inputStream = resource.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                StringBuilder contentBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    contentBuilder.append(line).append(System.lineSeparator());

                return contentBuilder.toString().replace("{{CODE}}", code);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoadHTMLTemplateFailedException("Failed to load HTML template: " + e.getMessage());
        }
    }



    private String generateVerificationCode() {

        return Integer.toString((int) (Math.random() * 90000) + 10000);
    }

    private record CodeDetails(String code, LocalDateTime generatedTime) {}
}