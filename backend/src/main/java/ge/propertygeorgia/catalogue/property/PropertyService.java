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

    public Property getProperty(long propertyId) {
        if (propertyRepository.existsById(propertyId)) {
            return propertyRepository.findById(propertyId).get();
        }
        return null;
    }

    public long postProperty(Property property) {
        return propertyRepository.save(property).getId();
    }

    public void deleteProperty(long propertyId) {
        if (propertyRepository.existsById(propertyId)) {
            propertyRepository.deleteById(propertyId);
        }
    }

    @Transactional
    public void updateProperty(long propertyId, String name, String title, String city, String country, String description,
                               String nameEng, String titleEng, String cityEng, String countryEng, String descriptionEng, String[] images, String file) {
        if (propertyRepository.existsById(propertyId)) {
            Property property = propertyRepository.findById(propertyId)
                            .orElse(null);
            if (name != null) property.setName(name);
            if (title != null) property.setTitle(title);
            if (city != null) property.setCity(city);
            if (country != null) property.setCountry(country);
            if (description != null) property.setDescription(description);
            if (nameEng != null) property.setNameEng(nameEng);
            if (titleEng != null) property.setTitleEng(titleEng);
            if (cityEng != null) property.setCityEng(cityEng);
            if (countryEng != null) property.setCountryEng(countryEng);
            if (descriptionEng != null) property.setDescriptionEng(descriptionEng);
            if (images != null) property.setImages(images);
            if (file !=null) property.setFile(file);
        }
    }


}
