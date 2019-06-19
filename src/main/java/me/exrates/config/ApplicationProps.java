package me.exrates.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProps {

    @Value("${mail_sendgrid.host}")
    private String mailSendGridHost;
    @Value("${mail_sendgrid.port}")
    private String mailSendGridPort;
    @Value("${mail_sendgrid.protocol}")
    private String mailSendGridProtocol;
    @Value("${mail_sendgrid.user}")
    private String mailSendGridUser;
    @Value("${mail_sendgrid.password}")
    private String mailSendGridPassword;

    @Value("${mail_ses.host}")
    private String mailSesHost;
    @Value("${mail_ses.port}")
    private String mailSesPort;
    @Value("${mail_ses.protocol}")
    private String mailSesProtocol;
    @Value("${mail_ses.user}")
    private String mailSesUser;
    @Value("${mail_ses.password}")
    private String mailSesPassword;

    @Value("${main.email}")
    private String mainEmail;
    @Value("${main.email.name}")
    private String mainEmailName;

    public String getMailSesHost() {
        return mailSesHost;
    }

    public String getMailSesPort() {
        return mailSesPort;
    }

    public String getMailSesProtocol() {
        return mailSesProtocol;
    }

    public String getMailSesUser() {
        return mailSesUser;
    }

    public String getMailSesPassword() {
        return mailSesPassword;
    }

    public String getMailSendGridHost() {
        return mailSendGridHost;
    }

    public String getMailSendGridPort() {
        return mailSendGridPort;
    }

    public String getMailSendGridProtocol() {
        return mailSendGridProtocol;
    }

    public String getMailSendGridUser() {
        return mailSendGridUser;
    }

    public String getMailSendGridPassword() {
        return mailSendGridPassword;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public String getMainEmailName() {
        return mainEmailName;
    }
}
