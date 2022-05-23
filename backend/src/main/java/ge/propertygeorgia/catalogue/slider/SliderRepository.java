package ge.propertygeorgia.catalogue.slider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository <Slider, Long> {
}
