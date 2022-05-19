package ge.propertygeorgia.catalogue.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getProperties() {
        return propertyRepository.findAll();
    }

    public void postProperty(Property property) {
        propertyRepository.save(property);
    }

    public void deleteProperty(Long propertyId) {
        if (propertyRepository.existsById(propertyId)) {
            propertyRepository.deleteById(propertyId);
        }
    }

    @Transactional
    public void updateProperty(long propertyId, String name, String address, String description, String image) {
        if (propertyRepository.existsById(propertyId)) {
            Property property = propertyRepository.findById(propertyId)
                            .orElse(null);
            if(name != null) property.setName(name);
            if (address != null) property.setAddress(address);
            if (description != null) property.setDescription(description);
            if (image !=null) property.setImage(image);
        }
    }
}
