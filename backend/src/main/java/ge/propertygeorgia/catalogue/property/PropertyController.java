package ge.propertygeorgia.catalogue.property;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
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
    ) throws IOException {
        String imageName1 = saveFile(image1, IMAGES_DIRECTORY, image1.getOriginalFilename());
        String imageName2 = saveFile(image2, IMAGES_DIRECTORY, image2.getOriginalFilename());
        String imageName3 = saveFile(image3, IMAGES_DIRECTORY, image3.getOriginalFilename());
        String imageName4 = saveFile(image4, IMAGES_DIRECTORY, image4.getOriginalFilename());
        String fileName = saveFile(file, FILE_DIRECTORY, file.getOriginalFilename());

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
        property.setImages(new String[]{ "assets/images/" + imageName1,
                "assets/images/" + imageName2,
                "assets/images/" + imageName3,
                "assets/images/" + imageName4});
        property.setFile("assets/" + fileName);
        long id = propertyService.postProperty(property);
        return new ResponseEntity<Object>("sent", HttpStatus.OK);
    }


//    @PostMapping(path = "/uploadImages/{propertyId}")
//    public ResponseEntity<Object> uploadImages(@PathVariable("propertyId") Long propertyId,
//                                               ) throws IOException {
//
//
//
//        propertyService.updateProperty(propertyId, null, null, null,
//                null, null, null, null,
//                null, null, null,
//               ,
//                null);
//        return new ResponseEntity<Object>("The images have been uploaded.", HttpStatus.OK);
//    }


//    @PostMapping(path = "/uploadFile/{propertyId}")
//    public ResponseEntity<Object> uploadFile(@PathVariable("propertyId") long propertyId, @RequestParam("file") MultipartFile file) throws IOException {
//
//        propertyService.updateProperty(propertyId, null, null, null, null,
//                null, null, null, null, null, null, null, "assets/" + file.getOriginalFilename());
//        return new ResponseEntity<Object>("The file has been uploaded.", HttpStatus.OK);
//    }

    @DeleteMapping(path = "/{propertyId}")
    public void deleteProperty(@PathVariable("propertyId") long propertyId) {
        propertyService.deleteProperty(propertyId);
    }

    @PutMapping(path = "/{propertyId}")
    public void updateProperty(@PathVariable("propertyId") long propertyId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String city,
                               @RequestParam(required = false) String country,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String nameEng,
                               @RequestParam(required = false) String titleEng,
                               @RequestParam(required = false) String cityEng,
                               @RequestParam(required = false) String countryEng,
                               @RequestParam(required = false) String descriptionEng,
                               @RequestParam(required = false) String[] images,
                               @RequestParam(required = false) String file) {
        propertyService.updateProperty(propertyId, name, title, city, country, description,
                nameEng, titleEng, cityEng, countryEng, descriptionEng, images, file);
    }

    public static String saveFile(MultipartFile file, String directory, String fileName) {
        try {
            Files.write(Paths.get(directory + fileName)
                    , file.getBytes()
                    , StandardOpenOption.CREATE);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
