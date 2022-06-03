package ge.propertygeorgia.catalogue.subscriber;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository){
        this.subscriberRepository = subscriberRepository;
    }

    public List<Subscriber> getSubsribers() {
        return subscriberRepository.findAll();
    }

    public void postSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

    public Path exportToCSV() throws IOException {
        List<Subscriber> subscribers = getSubsribers();

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "subscribers_" + currentDateTime + ".csv";

        String[] headers = {"Subscriber ID"
                , "Name"
                , "E-Mail"
                , "Phone"
                , "Address"
                , "Property ID"
                , "Property Name"
                , "Property Title"
                , "Date"
        };

        FileWriter out = new FileWriter(filename);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(headers))) {
            for (Subscriber subscriber : subscribers) {
                printer.printRecord(subscriber);
            }
        }

        return FileSystems.getDefault().getPath(filename);
    }

}
