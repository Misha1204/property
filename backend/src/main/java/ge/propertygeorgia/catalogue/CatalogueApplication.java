package ge.propertygeorgia.catalogue;

import ge.propertygeorgia.catalogue.property.Property;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
public class CatalogueApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatalogueApplication.class, args);
	}

}
