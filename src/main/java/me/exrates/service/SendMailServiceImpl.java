package me.exrates.service;

import lombok.extern.log4j.Log4j2;
import me.exrates.config.ApplicationProps;
import me.exrates.model.Email;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class SendMailServiceImpl implements SendMailService {

    private static final String UTF8 = "UTF-8";

    @Autowired
    ApplicationProps props;

    @Autowired
    @Qualifier("SesMailSender")
    private JavaMailSender sesMailSender;

    @Autowired
    @Qualifier("SendGridSender")
    private JavaMailSender sendGridSender;

    public void sendMail(Email email) {
        try {
            sendMail(email.toBuilder()
                            .from(props.getMainEmail())
                            .build(),
                    sesMailSender);
        } catch (Exception ex) {
            log.error(ex);
            sendMail(email.toBuilder()
                            .from(props.getMainEmail())
                            .build(),
                    sendGridSender);
        }
    }

    private void sendMail(Email email, JavaMailSender mailSender) {
        StopWatch stopWatch = StopWatch.createStarted();
        log.debug("try to send email {} to {}, time {}", email.getSubject(), email.getTo());
        try {
            mailSender.send(mimeMessage -> {
                MimeMessageHelper message;
                message = new MimeMessageHelper(mimeMessage, true, UTF8);
                message.setFrom(email.getFrom());
                message.setTo(email.getTo());
                message.setSubject(email.getSubject());
                message.setText(email.getMessage(), true);
                if (email.getAttachments() != null) {
                    for (Email.Attachment attachment : email.getAttachments())
                        message.addAttachment(attachment.getName(), attachment.getResource(), attachment.getContentType());
                }
            });
            log.info("Email sent to: {}, subject {}, duration {} seconds", email.getTo(), email.getSubject(),
                    stopWatch.getTime(TimeUnit.SECONDS));
        } catch (Exception ex) {
            log.error("Could not send email {}. Reason: {}", email, ex.getMessage());
        }
    }
}
