package ge.propertygeorgia.catalogue.siteUser;

import ge.propertygeorgia.catalogue.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class SiteUserController {
    private final SiteUserService siteUserService;

    @Autowired
    public SiteUserController(SiteUserService siteUserServicve) {
        this.siteUserService = siteUserServicve;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createUser(@RequestPart(value = "username") String username
            , @RequestPart(value = "password") String password) {
        long hashcode = siteUserService.createUser(username, password);
        return new ResponseEntity<Object>(hashcode, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public boolean login(@RequestPart(value = "username") String username
            , @RequestPart(value = "password") String password){
        return siteUserService.logIn(username, password, true);
    }

    @PostMapping(path = "/logout")
    public void logout() {
        siteUserService.logOut( false);
    }

    @GetMapping()
    public boolean isLoggedIn() {
        return siteUserService.isLoggedIn();
    }

}
