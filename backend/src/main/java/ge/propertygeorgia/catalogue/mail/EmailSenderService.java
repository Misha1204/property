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


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SubscriberService subscriberService;

    @Scheduled(cron = "0 56 05 ? * *")
    public void sendMail() throws MessagingException, IOException {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("toppropertiesservice@gmail.com");
            helper.setTo("cn528491@gmail.com");
            helper.setSubject("Top Properties Subscribers - List");
            helper.setText("");
            FileSystemResource file
                    = new FileSystemResource(subscriberService.exportToCSV());
            helper.addAttachment("Top Properties Subscribers", file);

            mailSender.send(message);

    }
}
