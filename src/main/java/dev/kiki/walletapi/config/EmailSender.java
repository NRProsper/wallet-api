package dev.kiki.walletapi.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSender {

    @Value("${frontend.url}")
    private String FRONTEND_URL;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendPasswordRestEmail(String email, String token) throws MessagingException {
        String resetUrl = FRONTEND_URL + "/reset-password?token="+ token;

        Context context = new Context();
        context.setVariable("resetUrl", resetUrl);
        String content = templateEngine.process("email/password-reset", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Password Reset Request");
        helper.setText(content, true);

        javaMailSender.send(mimeMessage);

    }

}
