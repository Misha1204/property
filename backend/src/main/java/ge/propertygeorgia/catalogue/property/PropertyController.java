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

    @PostMapping
    public void postProperty(@RequestBody Property property){
        propertyService.postProperty(property);
    }

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @PostMapping(path = "/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();
        return new ResponseEntity<Object>("The file has been uploaded.", HttpStatus.OK);
    }

    @DeleteMapping(path = "{propertyId}")
    public void deleteProperty(@PathVariable("propertyId") Long propertyId){
        propertyService.deleteProperty(propertyId);
    }
    
    @PutMapping(path = "{propertyId}")
    public void updateProperty(@PathVariable("propertyId") long propertyId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String image){
        propertyService.updateProperty(propertyId, name, address, description, image);
    }
}
