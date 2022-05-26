package ge.propertygeorgia.catalogue.property;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import ge.propertygeorgia.catalogue.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RestController
@RequestMapping(path = "api/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public List<Property> getProperties() {
        return propertyService.getProperties();
    }

    @GetMapping(path = "/{propertyId}")
    public Property getProperty(@PathVariable("propertyId") Long propertyId) {
        return propertyService.getProperty(propertyId);
    }


    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postProperty(
            @RequestPart("image1") MultipartFile image1
            , @RequestPart("image2") MultipartFile image2
            , @RequestPart("image3") MultipartFile image3
            , @RequestPart("image4") MultipartFile image4
            , @RequestPart("file") MultipartFile file
            , @RequestPart("name") String name
            , @RequestPart("title") String title
            , @RequestPart("city") String city
            , @RequestPart("country") String country
            , @RequestPart("description") String description
            , @RequestPart("nameEng") String nameEng
            , @RequestPart("titleEng") String titleEng
            , @RequestPart("cityEng") String cityEng
            , @RequestPart("countryEng") String countryEng
            , @RequestPart("descriptionEng") String descriptionEng
    ) {
        String[] images = new String[4];
        String fileAddress;
        images[0] = FileUtils.postFile(image1, IMAGES_DIRECTORY, "assets/images/");
        images[1] = FileUtils.postFile(image2, IMAGES_DIRECTORY, "assets/images/");
        images[2] = FileUtils.postFile(image3, IMAGES_DIRECTORY, "assets/images/");
        images[3] = FileUtils.postFile(image4, IMAGES_DIRECTORY, "assets/images/");
        fileAddress = FileUtils.postFile(file, FILE_DIRECTORY, "assets/");

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

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
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
            , @RequestPart(value = "fileAddress1", required = false) String fileAddress) {
        String[] oldImages = propertyService.getProperty(propertyId).getImages();
        String oldFile = propertyService.getProperty(propertyId).getFile();
        String[] images = new String[4];

        images[0] = FileUtils.updateFile(image1, imageAddress1, IMAGES_DIRECTORY);
        images[1] = FileUtils.updateFile(image2, imageAddress2, IMAGES_DIRECTORY);
        images[2] = FileUtils.updateFile(image3, imageAddress3, IMAGES_DIRECTORY);
        images[3] = FileUtils.updateFile(image4, imageAddress4, IMAGES_DIRECTORY);
        fileAddress = FileUtils.updateFile(file, fileAddress, FILE_DIRECTORY);

        FileUtils.deleteFiles(oldImages, images);
        FileUtils.deleteFiles(new String[]{oldFile}, new String[]{fileAddress});

        propertyService.updateProperty(propertyId, name, title, city, country, description,
                nameEng, titleEng, cityEng, countryEng, descriptionEng, images, fileAddress);
    }

}
