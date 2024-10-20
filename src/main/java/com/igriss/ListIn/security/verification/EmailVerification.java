package com.igriss.ListIn.security.verification;

import com.igriss.ListIn.dto.security_dto.EmailVerificationRequestDTO;
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

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static com.igriss.ListIn.security.verification.Message.CODE;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerification {

    private final JavaMailSender javaMailSender;

    private  LocalDateTime localDateTime;

    private final ResourceLoader resourceLoader;

    @Async
    public void sendMail(EmailVerificationRequestDTO user) throws EmailNotFoundException {

        CODE=Integer.toString((int)(Math.random()*900000)+100000);
        localDateTime = LocalDateTime.now();

        var mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage;

        try{
            mailMessage=new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Email verification");
            mailMessage.setText(loadHTMLTemplate(CODE),true);
            mailMessage.setFrom("ListIn verification <noreply@chat.com>");

        }catch (MessagingException e) {
            throw new EmailNotFoundException("Email not found");
        }

        javaMailSender.send(mimeMessage);
    }

    public boolean verifyMail(String code) {
        return !isCodeExpired() && code.equals(CODE);
    }

    public boolean isCodeExpired() {
        long EXPIRATION = 2;
        return localDateTime.plusMinutes(EXPIRATION).isBefore(LocalDateTime.now());
    }

    private  String loadHTMLTemplate(String code) throws LoadHTMLTemplateFailedException {
        Resource resource = resourceLoader.getResource("classpath:" + "templates/email_template.html");
        String template;
        try{
            template = new String(Files.readAllBytes(resource.getFile().toPath()));
        }catch (IOException exception){
            throw new LoadHTMLTemplateFailedException("Failed to load HTML from template ");
        }
        return template.replace("{{CODE}}", code);
    }
}
