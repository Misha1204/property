package ge.propertygeorgia.catalogue.slider;



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
@RequestMapping (path = "/api/slider")
public class SliderController {

    private final SliderService sliderService;

    @Autowired
    public SliderController(SliderService slierService) {
        this.sliderService = slierService;
    }

    @GetMapping
    public List<Slider> getSliders() {
        return sliderService.getSliders();
    }

    @PostMapping
    public void postSlider(@RequestBody Slider slider) {
       sliderService.postSlider(slider);
    }

    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;

    @PostMapping(path = "/uploadImage/{sliderId}")
    public ResponseEntity<Object> uploadImages(@PathVariable("sliderId") Long sliderId,
                                               @RequestParam("image") MultipartFile image) throws IOException {

        File myImage = new File(IMAGES_DIRECTORY + image.getOriginalFilename());
        myImage.createNewFile();
        FileOutputStream fos = new FileOutputStream(myImage);
        fos.write(image.getBytes());
        fos.close();

        sliderService.updateSlider(sliderId, null, "assets/images/" + image.getOriginalFilename());
        return new ResponseEntity<Object>("The image has been uploaded.", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{sliderId}")
    public void deleteProperty(@PathVariable("sliderId") long sliderId) {
        sliderService.deleteSlider(sliderId);
    }

    @PutMapping(path = "/{sliderId}")
    public void updateProperty(@PathVariable("sliderId") long sliderId,
                               @RequestParam(required = false) String link,
                               @RequestParam(required = false) String image) {
        sliderService.updateSlider(sliderId, link, image);
    }

}
