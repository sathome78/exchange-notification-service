package me.exrates.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProps {

    @Value("${mail_support.host}")
    private String mailSupportHost;
    @Value("${mail_support.port}")
    private String mailSupportPort;
    @Value("${mail_support.protocol}")
    private String mailSupportProtocol;
    @Value("${mail_support.user}")
    private String mailSupportUser;
    @Value("${mail_support.password}")
    private String mailSupportPassword;
    
    @Value("${mail_mandrill.host}")
    private String mailMandrillHost;
    @Value("${mail_mandrill.port}")
    private String mailMandrillPort;
    @Value("${mail_mandrill.protocol}")
    private String mailMandrillProtocol;
    @Value("${mail_mandrill.user}")
    private String mailMandrillUser;
    @Value("${mail_mandrill.password}")
    private String mailMandrillPassword;
    
    @Value("${mail_info.host}")
    private String mailInfoHost;
    @Value("${mail_info.port}")
    private String mailInfoPort;
    @Value("${mail_info.protocol}")
    private String mailInfoProtocol;
    @Value("${mail_info.user}")
    private String mailInfoUser;
    @Value("${mail_info.password}")
    private String mailInfoPassword;

    @Value("${mail_info.allowedOnly}")
    private Boolean allowedOnly;
    @Value("${mail_info.allowedEmails}")
    private String allowedEmailsList;
    @Value("${default_mail_type}")
    private String mailType;
    @Value("${support.email}")
    private String supportEmail;
    @Value("${mandrill.email}")
    private String mandrillEmail;
    @Value("${info.email}")
    private String infoEmail;
    @Value("${listing.email}")
    private String listingEmail;
    @Value("${listing.subject}")
    private String listingSubject;


    public String getMailSupportHost() {
        return mailSupportHost;
    }

    public String getMailSupportPort() {
        return mailSupportPort;
    }

    public String getMailSupportProtocol() {
        return mailSupportProtocol;
    }

    public String getMailSupportUser() {
        return mailSupportUser;
    }

    public String getMailSupportPassword() {
        return mailSupportPassword;
    }

    public String getMailMandrillHost() {
        return mailMandrillHost;
    }

    public String getMailMandrillPort() {
        return mailMandrillPort;
    }

    public String getMailMandrillProtocol() {
        return mailMandrillProtocol;
    }

    public String getMailMandrillUser() {
        return mailMandrillUser;
    }

    public String getMailMandrillPassword() {
        return mailMandrillPassword;
    }

    public String getMailInfoHost() {
        return mailInfoHost;
    }

    public String getMailInfoPort() {
        return mailInfoPort;
    }

    public String getMailInfoProtocol() {
        return mailInfoProtocol;
    }

    public String getMailInfoUser() {
        return mailInfoUser;
    }

    public String getMailInfoPassword() {
        return mailInfoPassword;
    }

    public Boolean getAllowedOnly() {
        return allowedOnly;
    }

    public String getAllowedEmailsList() {
        return allowedEmailsList;
    }

    public String getMailType() {
        return mailType;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public String getMandrillEmail() {
        return mandrillEmail;
    }

    public String getInfoEmail() {
        return infoEmail;
    }

    public String getListingEmail() {
        return listingEmail;
    }

    public String getListingSubject() {
        return listingSubject;
    }
}
