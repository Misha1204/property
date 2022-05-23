package ge.propertygeorgia.catalogue.slider;

import javax.persistence.*;

@Entity
@Table
public class Slider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String link;
    private String image;

    public Slider(){}

    public Slider(long id, String link, String image) {
        this.id = id;
        this.link = link;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
