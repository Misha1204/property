package ge.propertygeorgia.catalogue.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
