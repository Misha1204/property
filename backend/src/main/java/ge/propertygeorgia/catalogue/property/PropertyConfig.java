package ge.propertygeorgia.catalogue.property;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PropertyConfig {

    @Bean
    CommandLineRunner commandLineRunner(PropertyRepository propertyRepository){
        return args -> {
            Property apartment1 = new Property(
                    "Apartment Complex",
                    "Avenue 7",
                    "Lorem ipsum"
            );
            Property apartment2 = new Property(
                    "Mansion",
                    "Avenue 9",
                    "Lorem ipsum"
            );
            propertyRepository.saveAll(
                    List.of(apartment1, apartment2)
            );
        };
    }
}
