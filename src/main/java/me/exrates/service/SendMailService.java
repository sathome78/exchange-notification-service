package me.exrates.service;

import me.exrates.model.Email;
import me.exrates.model.ListingRequest;

public interface SendMailService {

    void sendMail(Email email);
}
