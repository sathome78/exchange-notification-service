package me.exrates.service;

import me.exrates.config.ApplicationProps;
import me.exrates.model.Email;
import me.exrates.model.EmailSenderType;
import me.exrates.model.ListingRequest;
import me.exrates.model.MessageFormatterUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Log4j2
@Service
public class SendMailServiceImpl implements SendMailService {

    private final static ExecutorService EXECUTORS = Executors.newCachedThreadPool();
    private final static ExecutorService SUPPORT_MAIL_EXECUTORS = Executors.newCachedThreadPool();
    private static final String UTF8 = "UTF-8";
    @Autowired
    ApplicationProps props;
    @Autowired
    @Qualifier("SupportMailSender")
    private JavaMailSender supportMailSender;
    @Autowired
    @Qualifier("MandrillMailSender")
    private JavaMailSender mandrillMailSender;
    @Autowired
    @Qualifier("InfoMailSender")
    private JavaMailSender infoMailSender;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public void sendMail(Email email) {
        SUPPORT_MAIL_EXECUTORS.execute(() -> {
            try {
                sendMail(email.toBuilder()
                                .from(props.getSupportEmail())
                                .build(),
                        supportMailSender);
            } catch (Exception ex) {
                log.error(ex);
                sendMail(email.toBuilder()
                                .from(props.getInfoEmail())
                                .build(),
                        infoMailSender);
            }
        });
    }

    public void sendMailMandrill(Email email) {
        SUPPORT_MAIL_EXECUTORS.execute(() -> {
            try {
                sendByType(email, EmailSenderType.valueOf(props.getMailType()));
            } catch (Exception e) {
                log.error(e);
                sendMail(email.toBuilder()
                                .from(props.getSupportEmail())
                                .build(),
                        supportMailSender);
            }
        });
    }

    private void sendByType(Email email, EmailSenderType type) {
        switch (type) {
            case gmail: {
                sendInfoMail(email);
                break;
            }
            case mandrill: {
                sendMail(email.toBuilder()
                                .from(props.getMandrillEmail())
                                .build(),
                        mandrillMailSender);
                break;
            }
        }
    }

    @Override
    public void sendInfoMail(Email email) {
        if (props.getAllowedOnly()) {
            String[] allowedEmails = props.getAllowedEmailsList().split(",");
            if (Stream.of(allowedEmails).noneMatch(mail -> mail.equals(email.getTo()))) {
                return;
            }
        }
        EXECUTORS.execute(() -> {
            try {
                sendMail(email.toBuilder()
                                .from(props.getInfoEmail())
                                .build(),
                        activeProfile.equalsIgnoreCase("prod") ? mandrillMailSender : infoMailSender);
            } catch (MailException ex) {
                log.error(ex);
                sendMail(email.toBuilder()
                                .from(props.getSupportEmail())
                                .build(),
                        supportMailSender);
            }
        });
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
                message.setText(prepareTemplate(email.getMessage()), true);
                if (email.getAttachments() != null) {
                    for (Email.Attachment attachment : email.getAttachments())
                        message.addAttachment(attachment.getName(), attachment.getResource(), attachment.getContentType());
                }
            });
            log.info("Email sent: {}, duration {} seconds", email, stopWatch.getTime(TimeUnit.SECONDS));
        } catch (Exception ex) {
            log.error("Could not send email {}. Reason: {}", email, ex.getMessage());
        }
    }

    @Override
    public void sendFeedbackMail(String senderName, String senderMail, String messageBody, String mailTo) {
        sendMail(Email.builder()
                .from(senderMail)
                .to(mailTo)
                .message(messageBody)
                .subject(String.format("Feedback from %s -- %s", senderName, senderMail))
                .build());
    }

    @Override
    public void sendListingRequestEmail(ListingRequest request) {
        final String name = request.getName();
        final String email = request.getEmail();
        final String telegram = request.getTelegram();
        final String text = request.getText();

        sendInfoMail(Email.builder()
                .to(props.getListingEmail())
                .subject(props.getListingSubject())
                .message(MessageFormatterUtil.format(name, email, telegram, text))
                .build());
    }

    @PreDestroy
    public void destroy() {
        EXECUTORS.shutdown();
        SUPPORT_MAIL_EXECUTORS.shutdown();
    }

    private String prepareTemplate(String text) {
        File file;
        String html;
        try {
            file = ResourceUtils.getFile("classpath:email/template.html");
            byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            html = new String(encoded, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            return text;
        }
        html = html.replace("{::text::}", text);
        return html;
    }
}
