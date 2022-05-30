package ge.propertygeorgia.catalogue.property;

import ge.propertygeorgia.catalogue.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/property")
public class PropertyController {

    private final PropertyService propertyService;
    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @Value("${file.path}")
    String FILE_PATH;
    @Value("${image.path}")
    String IMAGE_PATH;


    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping(path = "/{language}")
    public List<PropertyDTO> getPropertyDTOs(@PathVariable("language") String language) {
        return propertyService.getPropertyDTOs(language);
    }

    @GetMapping()
    public List<Property> getPropertyDTOs() {
        return propertyService.getProperties();
    }

    @GetMapping(path = "/{propertyId}/{language}")
    public PropertyDTO getPropertyDTO(@PathVariable("propertyId") Long propertyId
    , @PathVariable("language") String language) {
        return propertyService.getPropertyDTO(propertyId, language);
    }

    @GetMapping()
    public Property getProperty(@PathVariable("propertyId") Long propertyId
           ) {
        return propertyService.getProperty(propertyId);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postProperty(@RequestPart(value = "image1", required = false) MultipartFile image1
            , @RequestPart(value = "image2", required = false) MultipartFile image2
            , @RequestPart(value = "image3", required = false) MultipartFile image3
            , @RequestPart(value = "image4", required = false) MultipartFile image4
            , @RequestPart(value = "file", required = false) MultipartFile file
            , @RequestPart(value = "name", required = false) String name
            , @RequestPart(value = "title", required = false) String title
            , @RequestPart(value = "city", required = false) String city
            , @RequestPart(value = "country", required = false) String country
            , @RequestPart(value = "description", required = false) String description
            , @RequestPart(value = "nameEng", required = false) String nameEng
            , @RequestPart(value = "titleEng", required = false) String titleEng
            , @RequestPart(value = "cityEng", required = false) String cityEng
            , @RequestPart(value = "countryEng", required = false) String countryEng
            , @RequestPart(value = "descriptionEng", required = false) String descriptionEng
    ) {
        String[] images = new String[4];
        String fileAddress;
        images[0] = FileUtils.postFile(image1, IMAGES_DIRECTORY, IMAGE_PATH);
        images[1] = FileUtils.postFile(image2, IMAGES_DIRECTORY, IMAGE_PATH);
        images[2] = FileUtils.postFile(image3, IMAGES_DIRECTORY, IMAGE_PATH);
        images[3] = FileUtils.postFile(image4, IMAGES_DIRECTORY, IMAGE_PATH);
        fileAddress = FileUtils.postFile(file, FILE_DIRECTORY, FILE_PATH);

        Property property = new Property();
        property.setName(name);
        property.setTitle(title);
        property.setCity(city);
        property.setCountry(country);
        property.setDescription(description);
        property.setNameEng(nameEng);
        property.setTitleEng(titleEng);
        property.setCityEng(cityEng);
        property.setCountryEng(countryEng);
        property.setDescriptionEng(descriptionEng);
        property.setImages(images);
        property.setFile(fileAddress);
        long id = propertyService.postProperty(property);
        return new ResponseEntity<Object>("Property section has been created.", HttpStatus.OK);
    }


    @DeleteMapping(path = "/{propertyId}")
    public void deleteProperty(@PathVariable("propertyId") long propertyId) {
        propertyService.deleteProperty(propertyId);
    }

    @PutMapping(path = "/{propertyId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public void updateProperty(@PathVariable("propertyId") long propertyId
            , @RequestPart(value = "image1", required = false) MultipartFile image1
            , @RequestPart(value = "image2", required = false) MultipartFile image2
            , @RequestPart(value = "image3", required = false) MultipartFile image3
            , @RequestPart(value = "image4", required = false) MultipartFile image4
            , @RequestPart(value = "file", required = false) MultipartFile file
            , @RequestPart(value = "name", required = false) String name
            , @RequestPart(value = "title", required = false) String title
            , @RequestPart(value = "city", required = false) String city
            , @RequestPart(value = "country", required = false) String country
            , @RequestPart(value = "description", required = false) String description
            , @RequestPart(value = "nameEng", required = false) String nameEng
            , @RequestPart(value = "titleEng", required = false) String titleEng
            , @RequestPart(value = "cityEng", required = false) String cityEng
            , @RequestPart(value = "countryEng", required = false) String countryEng
            , @RequestPart(value = "descriptionEng", required = false) String descriptionEng
            , @RequestPart(value = "imageAddress1", required = false) String imageAddress1
            , @RequestPart(value = "imageAddress2", required = false) String imageAddress2
            , @RequestPart(value = "imageAddress3", required = false) String imageAddress3
            , @RequestPart(value = "imageAddress4", required = false) String imageAddress4
            , @RequestPart(value = "fileAddress", required = false) String fileAddress) {
        String[] oldImages = propertyService.getProperty(propertyId).getImages();
        String oldFile = propertyService.getProperty(propertyId).getFile();
        String[] images = new String[4];

        images[0] = FileUtils.updateFile(image1, imageAddress1, IMAGES_DIRECTORY, IMAGE_PATH);
        images[1] = FileUtils.updateFile(image2, imageAddress2, IMAGES_DIRECTORY, IMAGE_PATH);
        images[2] = FileUtils.updateFile(image3, imageAddress3, IMAGES_DIRECTORY, IMAGE_PATH);
        images[3] = FileUtils.updateFile(image4, imageAddress4, IMAGES_DIRECTORY, IMAGE_PATH);
        fileAddress = FileUtils.updateFile(file, fileAddress, FILE_DIRECTORY, FILE_PATH);

        FileUtils.deleteFiles(oldImages, images);
        FileUtils.deleteFiles(new String[]{oldFile}, new String[]{fileAddress});

        propertyService.updateProperty(propertyId, name, title, city, country, description,
                nameEng, titleEng, cityEng, countryEng, descriptionEng, images, fileAddress);
    }

}
