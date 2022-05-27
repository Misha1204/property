package ge.propertygeorgia.catalogue.siteUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
public class SiteUserController {
    private final SiteUserService siteUserService;

    @Autowired
    public SiteUserController(SiteUserService siteUserServicve){
        this.siteUserService = siteUserServicve;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createUser(@RequestPart(value = "username", required = false) String username
            , @RequestPart(value = "password", required = true) String password){
        long hashcode = siteUserService.createUser(username,password);
        return new ResponseEntity<Object>(hashcode, HttpStatus.OK);
    }
}
