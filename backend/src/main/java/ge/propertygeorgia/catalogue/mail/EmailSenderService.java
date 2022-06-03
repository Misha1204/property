package ge.propertygeorgia.catalogue.mail;


import ge.propertygeorgia.catalogue.subscriber.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SubscriberService subscriberService;

    @Scheduled(cron = "0 00 18 ? * *")
    public void sendMail() throws MessagingException, IOException {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("toppropertiesservice@gmail.com");
            helper.setTo("Info@propertygeorgia.ge");
            helper.setSubject("Top Properties Subscribers" + LocalDate.now());
            helper.setText("");
            FileSystemResource file
                    = new FileSystemResource(subscriberService.exportToCSV());
            helper.addAttachment("Subscribers_" + LocalDate.now().toString() + ".csv", file);

            mailSender.send(message);

    }
}
