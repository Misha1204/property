package ge.propertygeorgia.catalogue.property;

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
            , @RequestPart("sectionData") Property property
    ) throws IOException {

        File myImage1 = new File(IMAGES_DIRECTORY + image1.getOriginalFilename());
        myImage1.createNewFile();
        FileOutputStream fos1 = new FileOutputStream(myImage1);
        fos1.write(image1.getBytes());
        fos1.close();

        File myImage2 = new File(IMAGES_DIRECTORY + image2.getOriginalFilename());
        myImage2.createNewFile();
        FileOutputStream fos2 = new FileOutputStream(myImage2);
        fos2.write(image2.getBytes());
        fos2.close();

        File myImage3 = new File(IMAGES_DIRECTORY + image3.getOriginalFilename());
        myImage3.createNewFile();
        FileOutputStream fos3 = new FileOutputStream(myImage3);
        fos3.write(image3.getBytes());
        fos3.close();

        File myImage4 = new File(IMAGES_DIRECTORY + image4.getOriginalFilename());
        myImage4.createNewFile();
        FileOutputStream fos4 = new FileOutputStream(myImage4);
        fos4.write(image4.getBytes());
        fos4.close();

        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        property.setImages(new String[]{"assets/images/" + image1.getOriginalFilename(),
                "assets/images/" + image2.getOriginalFilename(),
                "assets/images/" + image3.getOriginalFilename(),
                "assets/images/" + image4.getOriginalFilename()});
        property.setFile("assets/" + file.getOriginalFilename());
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
}
