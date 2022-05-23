package ge.propertygeorgia.catalogue.slider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SliderService {

    private final SliderRepository sliderRepository;

    @Autowired
    public SliderService(SliderRepository slierRepository) {
        this.sliderRepository = slierRepository;
    }

    public List<Slider> getSliders() {
        return sliderRepository.findAll();
    }

    public void postSlider(Slider slider) {
        sliderRepository.save(slider);
    }

    public void deleteSlider(long sliderId){
        if (sliderRepository.existsById(sliderId)) {
            sliderRepository.deleteById(sliderId);
        }
    }

    @Transactional
    public void updateSlider(long sliderId, String link, String image) {
        if (sliderRepository.existsById(sliderId)) {
            Slider slider = sliderRepository.findById(sliderId)
                    .orElse(null);
            if (link != null) slider.setLink(link);
            if (image != null) slider.setImage(image);
        }
    }
}
