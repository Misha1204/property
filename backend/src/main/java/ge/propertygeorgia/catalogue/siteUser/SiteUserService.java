package ge.propertygeorgia.catalogue.siteUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;

    @Autowired
    public SiteUserService(SiteUserRepository siteUserRepository) {

        this.siteUserRepository = siteUserRepository;
    }

    public long createUser(String username, String password) {
        long hashCode = password.hashCode();
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(hashCode);

        siteUserRepository.save(user);
        return hashCode;
    }
}
