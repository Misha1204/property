package ge.propertygeorgia.catalogue.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<PropertyDTO> getPropertyDTOs(String language) {
        return propertyRepository
                .findAll()
                .stream()
                .map(property -> createPropertyDTO(property, language))
                .collect(Collectors.toList());
    }

    public List<Property> getProperties() {
        return propertyRepository
                .findAll();
    }

    public PropertyDTO getPropertyDTO(long propertyId, String language) {
        if (propertyRepository.existsById(propertyId)) {
            Property property = propertyRepository.findById(propertyId).get();
            return createPropertyDTO(property, language);
        }
        return null;
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

    public PropertyDTO createPropertyDTO(Property property, String language){
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setImages(property.getImages());
        propertyDTO.setFile(property.getFile());
        if(language.equals("geo")) {
            propertyDTO.setName(property.getName());
            propertyDTO.setTitle(property.getTitle());
            propertyDTO.setCity(property.getCity());
            propertyDTO.setCountry(property.getCountry());
            propertyDTO.setDescription(property.getDescription());
        }else {
            propertyDTO.setName(property.getNameEng());
            propertyDTO.setTitle(property.getTitleEng());
            propertyDTO.setCity(property.getCityEng());
            propertyDTO.setCountry(property.getCountryEng());
            propertyDTO.setDescription(property.getDescriptionEng());
        }
        return propertyDTO;
    }
}
