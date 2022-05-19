package ge.propertygeorgia.catalogue.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
