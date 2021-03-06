package cz.psencik.coffeemachine.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("${mail.sender}")
    private String sender;

    public void sendMail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        try {
            emailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("could not send email", e);
        }
    }
}
