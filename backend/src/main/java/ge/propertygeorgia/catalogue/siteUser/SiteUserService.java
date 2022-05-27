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
    public boolean logIn(String username, String password, boolean loggedIn) {
        if (siteUserRepository.existsById(1L)) {
            SiteUser siteUser = siteUserRepository.findById(1L)
                    .orElse(null);
            long hashCode = password.hashCode();
            if(siteUser.getUsername().equals(username) && siteUser.getPassword()==hashCode){
                siteUser.setLoggedIn(loggedIn);
                return true;
            }

        }
        return false;
    }

    @Transactional
    public void logOut( boolean loggedOut) {
        if (siteUserRepository.existsById(1L)) {
            SiteUser siteUser = siteUserRepository.findById(1L)
                    .orElse(null);
            siteUser.setLoggedIn(loggedOut);
        }
    }

    public boolean isLoggedIn() {
            return siteUserRepository.findById(1L).get().isLoggedIn();
    }
}
