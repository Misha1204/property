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

    public Property getProperty(Long propertyId) {
        if (propertyRepository.existsById(propertyId)) {
            return propertyRepository.getById(propertyId);
        }
        return null;
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
    public void updateProperty(Long propertyId, String name, String title, String city, String country, String description,
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
            if (images != null) {
                String[] slides = {images[0], images[1], images[2], images[3]};
                property.setImages(slides);
            }
            if (file !=null) property.setFile(file);
        }
    }


}