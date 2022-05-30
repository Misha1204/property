package ge.propertygeorgia.catalogue.header;

import javax.persistence.*;

@Entity
@Table
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String description;
    private String descriptionEng;
    private String[] images = new String[4];
    private String[] files = new String[2];

    public Header () {

    }

    public Header(long id, String description, String descriptionEng, String[] images, String[] files) {
        this.id = id;
        this.description = description;
        this.descriptionEng = descriptionEng;
        this.images = images;
        this.files = files;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getFiles() {
        return files;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }
}


