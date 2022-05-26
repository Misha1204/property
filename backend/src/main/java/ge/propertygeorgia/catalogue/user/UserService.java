package ge.propertygeorgia.catalogue.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public int createUser(String username, String password) {
        int hashCode = password.hashCode();
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashCode);

        this.userRepository.save(user);
        return hashCode;
    }
}
