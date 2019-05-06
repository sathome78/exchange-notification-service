package me.exrates.listener;

import lombok.extern.log4j.Log4j2;
import me.exrates.config.RabbitConfig;
import me.exrates.model.Email;
import me.exrates.model.ListingRequest;
import me.exrates.service.SendMailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EmailListener {

    @Autowired
    SendMailService sendMailService;

    @RabbitListener(queues = RabbitConfig.EMAIL_MANDRILL_QUEUE)
    public void processEmailMandrill(Email email) {
        sendMailService.sendMailMandrill(email);
    }

    @RabbitListener(queues = RabbitConfig.EMAIL_INFO_QUEUE)
    public void processEmailInfo(Email email) {
        sendMailService.sendInfoMail(email);
    }

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void processEmail(Email email) {
        sendMailService.sendMail(email);
    }

    @RabbitListener(queues = RabbitConfig.EMAIL_LISTING_REQUEST_QUEUE)
    public void processEmailInfo(ListingRequest listingRequest) {
        sendMailService.sendListingRequestEmail(listingRequest);
    }
}
