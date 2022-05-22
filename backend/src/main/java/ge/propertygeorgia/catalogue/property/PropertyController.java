package ge.propertygeorgia.catalogue.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService){
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

    @PostMapping
    public void postProperty(@RequestBody Property property){
        propertyService.postProperty(property);
    }

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @PostMapping(path = "/uploadFile/{propertyId}")
    public ResponseEntity<Object> uploadFile(@PathVariable("propertyId") Long propertyId, @RequestParam("file") MultipartFile file) throws IOException {
        File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();
        propertyService.updateProperty(propertyId, null, null, null, null,
                null, null, null, null, null, null, null, "assets/" + file.getOriginalFilename());
        return new ResponseEntity<Object>("The file has been uploaded.", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{propertyId}")
    public void deleteProperty(@PathVariable("propertyId") Long propertyId){
        propertyService.deleteProperty(propertyId);
    }
    
    @PutMapping(path = "/{propertyId}")
    public void updateProperty(@PathVariable("propertyId") Long propertyId,
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
                               @RequestParam(required = false) String file){
        propertyService.updateProperty(propertyId, name, title, city, country, description,
                nameEng, titleEng, cityEng, countryEng, descriptionEng, images, file);
    }
}
