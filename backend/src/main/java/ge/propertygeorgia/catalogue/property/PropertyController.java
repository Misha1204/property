package ge.propertygeorgia.catalogue.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
