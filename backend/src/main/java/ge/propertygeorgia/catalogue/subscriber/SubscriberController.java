package ge.propertygeorgia.catalogue.subscriber;

import ge.propertygeorgia.catalogue.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService){
        this.subscriberService = subscriberService;
    }

    @GetMapping
    public List<Subscriber> getSubsribers(){
        return subscriberService.getSubsribers();
    }

    @PostMapping
    public void postSubscriber(@RequestBody Subscriber subscriber){
        subscriberService.postSubscriber(subscriber);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Subscriber> subscribers = subscriberService.getSubsribers();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Subscriber ID"
                , "Name"
                , "E-Mail"
                , "Phone"
                , "Address"
                , "Property ID"
                };
        String[] nameMapping = {"id"
                , "name"
                , "email"
                , "phone"
                , "address"
                , "propertyId"
                };

        csvWriter.writeHeader(csvHeader);

        for (Subscriber subscriber : subscribers) {
            csvWriter.write(subscriber, nameMapping);
        }

        csvWriter.close();

    }
}
