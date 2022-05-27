package ge.propertygeorgia.catalogue.siteUser;

import javax.persistence.*;

@Entity
@Table
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    private long password;
    private boolean loggedIn;

    public SiteUser() {}

    public SiteUser(long id, String username, int password, boolean loggedIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + password +
                '}';
    }
}
