package me.exrates.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    ApplicationProps props;

    @Bean(name = "SesMailSender")
    public JavaMailSenderImpl sesMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(props.getMailSesHost());
        mailSenderImpl.setPort(Integer.parseInt(props.getMailSesPort()));
        mailSenderImpl.setProtocol(props.getMailSesProtocol());
        mailSenderImpl.setUsername(props.getMailSesUser());
        mailSenderImpl.setPassword(props.getMailSesPassword());
        final Properties javaMailProps = mailSenderImpl.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.ssl.trust", props.getMailSesHost());
        return mailSenderImpl;
    }

    @Bean(name = "SendGridSender")
    public JavaMailSenderImpl sendGridSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(props.getMailSendGridHost());
        mailSenderImpl.setPort(Integer.parseInt(props.getMailSendGridPort()));
        mailSenderImpl.setProtocol(props.getMailSendGridProtocol());
        mailSenderImpl.setUsername(props.getMailSendGridUser());
        mailSenderImpl.setPassword(props.getMailSendGridPassword());
        final Properties javaMailProps = mailSenderImpl.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", false);
        javaMailProps.put("mail.smtp.ssl.trust", props.getMailSendGridHost());
        return mailSenderImpl;
    }
}
