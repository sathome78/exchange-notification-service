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

    @Bean(name = "SupportMailSender")
    public JavaMailSenderImpl javaMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(props.getMailSupportHost());
        mailSenderImpl.setPort(Integer.parseInt(props.getMailSupportPort()));
        mailSenderImpl.setProtocol(props.getMailInfoProtocol());
        mailSenderImpl.setUsername(props.getMailSupportUser());
        mailSenderImpl.setPassword(props.getMailSupportPassword());
        final Properties javaMailProps = mailSenderImpl.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.ssl.trust", props.getMailSupportHost());
        return mailSenderImpl;
    }

    @Bean(name = "MandrillMailSender")
    public JavaMailSenderImpl mandrillMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(props.getMailMandrillHost());
        mailSenderImpl.setPort(Integer.parseInt(props.getMailMandrillPort()));
        mailSenderImpl.setProtocol(props.getMailMandrillProtocol());
        mailSenderImpl.setUsername(props.getMailMandrillUser());
        mailSenderImpl.setPassword(props.getMailMandrillPassword());
        final Properties javaMailProps = mailSenderImpl.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.ssl.trust", props.getMailMandrillHost());
        return mailSenderImpl;
    }

    @Bean(name = "InfoMailSender")
    public JavaMailSenderImpl infoMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(props.getMailInfoHost());
        mailSenderImpl.setPort(Integer.parseInt(props.getMailInfoPort()));
        mailSenderImpl.setProtocol(props.getMailInfoProtocol());
        mailSenderImpl.setUsername(props.getMailInfoUser());
        mailSenderImpl.setPassword(props.getMailInfoPassword());
        final Properties javaMailProps = mailSenderImpl.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.ssl.trust", props.getMailInfoHost());
        return mailSenderImpl;
    }
}
