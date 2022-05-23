package ge.propertygeorgia.catalogue.header;

import ge.propertygeorgia.catalogue.property.Property;
import ge.propertygeorgia.catalogue.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/header")
public class HeaderController {

    private final HeaderService headerService;

    @Autowired
    public HeaderController(HeaderService headerService) {
        this.headerService = headerService;
    }

    @GetMapping()
    public Header getHeader() {
        return headerService.getHeader();
    }

    @PostMapping
    public void postHeader(@RequestBody Header header) {
        headerService.postHeader(header);
    }

    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;

    @PostMapping(path = "/uploadImages")
    public ResponseEntity<Object> uploadImages(@RequestParam("image1") MultipartFile image1,
                                               @RequestParam("image2") MultipartFile image2,
                                               @RequestParam("image3") MultipartFile image3,
                                               @RequestParam("image4") MultipartFile image4) throws IOException {

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

        headerService.updateHeader( null, null,
                new String[]{"assets/images/" + image1.getOriginalFilename(),
                        "assets/images/" + image2.getOriginalFilename(),
                        "assets/images/" + image3.getOriginalFilename(),
                        "assets/images/" + image4.getOriginalFilename()},
                null);
        return new ResponseEntity<Object>("The images have been uploaded.", HttpStatus.OK);
    }

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping(path = "/uploadFiles")
    public ResponseEntity<Object> uploadFile(@RequestParam("file1") MultipartFile file1,
                                             @RequestParam("file2") MultipartFile file2) throws IOException {
        File myFile1 = new File(FILE_DIRECTORY + file1.getOriginalFilename());
        myFile1.createNewFile();
        FileOutputStream fos1 = new FileOutputStream(myFile1);
        fos1.write(file1.getBytes());
        fos1.close();

        File myFile2 = new File(FILE_DIRECTORY + file2.getOriginalFilename());
        myFile2.createNewFile();
        FileOutputStream fos2 = new FileOutputStream(myFile1);
        fos2.write(file2.getBytes());
        fos2.close();

        headerService.updateHeader( null, null, null, new String[]{"assets/" + file1.getOriginalFilename(), "assets/" + file2.getOriginalFilename()});
        return new ResponseEntity<Object>("The file has been uploaded.", HttpStatus.OK);
    }

    @PutMapping
    public void updateHeader(@RequestParam(required = false) String description,
                             @RequestParam(required = false) String descriptionEng,
                             @RequestParam(required = false) String[] images,
                             @RequestParam(required = false) String[] files){
        headerService.updateHeader(description, descriptionEng, images, files);
    }

}
