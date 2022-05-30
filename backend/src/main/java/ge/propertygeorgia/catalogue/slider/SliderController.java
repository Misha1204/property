package ge.propertygeorgia.catalogue.slider;


import ge.propertygeorgia.catalogue.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/slider")
public class SliderController {

    private final SliderService sliderService;
    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;
    @Value("${image.path}")
    String IMAGE_PATH;


    @Autowired
    public SliderController(SliderService slierService) {
        this.sliderService = slierService;
    }

    @GetMapping
    public List<Slider> getSliders() {
        return sliderService.getSliders();
    }


    @GetMapping(path = "/{sliderId}")
    public Slider getProperty(@PathVariable("sliderId") Long sliderId) {
        return sliderService.getSlider(sliderId);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postSlider(@RequestPart(value = "link", required = false) String link
            , @RequestPart(value = "image", required = false) MultipartFile image) {
        String imageAddress = FileUtils.postFile(image,IMAGES_DIRECTORY,IMAGE_PATH);
        Slider slider = new Slider();
        slider.setLink(link);
        slider.setImage(imageAddress);
        sliderService.postSlider(slider);
        return new ResponseEntity<Object>("The slide container has been created.", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{sliderId}")
    public void deleteProperty(@PathVariable("sliderId") long sliderId) {
        sliderService.deleteSlider(sliderId);
    }

    @PutMapping(path = "/{sliderId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public void updateProperty(@PathVariable("sliderId") long sliderId,
                               @RequestPart(value = "link", required = false) String link,
                               @RequestPart(value = "image", required = false) MultipartFile image,
                               @RequestPart(value = "imageAddress", required = false) String imageAddress) {

        imageAddress = FileUtils.updateFile(image,imageAddress,IMAGES_DIRECTORY, IMAGE_PATH);
        if(Objects.equals(image,sliderService.getSlider(sliderId).getImage())) FileUtils.deleteFile(sliderService.getSlider(sliderId).getImage());

        sliderService.updateSlider(sliderId, link, imageAddress);
    }

}
