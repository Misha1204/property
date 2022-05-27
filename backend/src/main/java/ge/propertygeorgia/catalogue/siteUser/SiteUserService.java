package ge.propertygeorgia.catalogue.siteUser;

import ge.propertygeorgia.catalogue.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void logIn(long siteUserId, boolean loggedIn) {
        if (siteUserRepository.existsById(siteUserId)) {
            SiteUser siteUser = siteUserRepository.findById(siteUserId)
                    .orElse(null);
            siteUser.setLoggedIn(loggedIn);
        }
    }

    @Transactional
    public void logOut(long siteUserId, boolean loggedOut) {
        if (siteUserRepository.existsById(siteUserId)) {
            SiteUser siteUser = siteUserRepository.findById(siteUserId)
                    .orElse(null);
            siteUser.setLoggedIn(loggedOut);
        }
    }

    public boolean isLoggedIn(long siteUserId) {
            return siteUserRepository.findById(siteUserId).get().isLoggedIn();
    }
}
