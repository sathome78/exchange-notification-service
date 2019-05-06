package me.exrates.service;

import me.exrates.model.Email;
import me.exrates.model.ListingRequest;

public interface SendMailService {

    void sendMail(Email email);

    void sendMailMandrill(Email email);

    void sendInfoMail(Email email);

    void sendFeedbackMail(String senderName, String senderMail, String messageBody, String mailTo);

    void sendListingRequestEmail(ListingRequest request);
}
