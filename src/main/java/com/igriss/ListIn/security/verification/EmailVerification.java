package com.igriss.ListIn.security.verification;

import com.igriss.ListIn.dto.RegisterRequestDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.igriss.ListIn.security.verification.Message.PASSWORD;

@RequiredArgsConstructor

@Service
@Slf4j
public class EmailVerification {
    private final JavaMailSender javaMailSender;
    @Async
    public void sendMail(RegisterRequestDTO user) {
         PASSWORD=Integer.toString((int)(Math.random()*900000)+100000);
        var mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage;
        try {
            mailMessage=new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Email verification");
            mailMessage.setText(Message.VerificationMessage(),true);
            mailMessage.setFrom("ListIn verification <noreply@chat.com>");
        } catch (MessagingException e) {
            log.error("Failed to send mail: ",e.getCause());
        }
    }

    public boolean verifyMail(String code) {
        return code.equals(PASSWORD);
    }

}
