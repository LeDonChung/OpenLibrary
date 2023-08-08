package com.open.library.service.impl;

import com.open.library.POJO.Feedback;
import com.open.library.service.MailService;
import com.open.library.service.ThymeleafService;
import com.open.library.utils.request.FeedbackDTO;
import com.open.library.utils.response.FeedbackResponseDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String email;
    private final ThymeleafService thymeleafService;
    @Override
    public void sendResponseFeedback(Feedback feedback, String content, boolean defaultEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(feedback.getEmail());
            helper.setFrom(this.email);
            helper.setSubject("Open Library");

            // content
            Map<String, Object> variables = new HashMap<>();
            variables.put("name", feedback.getName());
            variables.put("default", defaultEmail);
            variables.put("content", content);
            variables.put("message", feedback.getMessage());

            helper.setText(thymeleafService.createContent("send-response-feedback.html", variables), true);
            mailSender.send(message);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
