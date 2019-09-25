package com.white.stratego.stratego.market.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SendingMailService {

    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private final MailProperties mailProperties;
    private final Configuration templates;

    @Autowired
    SendingMailService(MailProperties mailProperties, Configuration templates){
        this.mailProperties = mailProperties;
        this.templates = templates;
    }

    public boolean sendVerificationMail(String toEmail, String verificationCode) {
        String subject = "Please verify your email";
        String body = "";
        try {
            Template t = templates.getTemplate("email-verification.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("VERIFICATION_URL", verificationCode);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).  log(Level.SEVERE, ex.getMessage(), ex);
        }
        return sendMail(toEmail, subject, body);
    }
//
    private boolean sendMail(String toEmail, String subject, String body) {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            emailSender.send(message);

            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
}