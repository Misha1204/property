package ge.propertygeorgia.catalogue.property;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table
public class Property {
    @Id
    @SequenceGenerator(
            name = "property_sequence",
            sequenceName = "property_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_sequence"
    )
    private long id;
    private String name;
    private String title;
    private String city;
    private String country;
    @Column(length = 1000)
    private String description;
    private String nameEng;
    private String titleEng;
    private String cityEng;
    private String countryEng;
    @Column(length = 1000)
    private String descriptionEng;
    private String[] images = new String[4];
    private String file;

    public Property() {
    }

    public Property(long id, String name, String title, String city, String country, String description,
                    String nameEng, String titleEng, String cityEng, String countryEng, String descriptionEng,
                    String[] images, String file) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.city = city;
        this.country = country;
        this.description = description;
        this.nameEng = nameEng;
        this.titleEng = titleEng;
        this.cityEng = cityEng;
        this.countryEng = countryEng;
        this.descriptionEng = descriptionEng;
        this.images = images;
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getCityEng() {
        return cityEng;
    }

    public void setCityEng(String cityEng) {
        this.cityEng = cityEng;
    }

    public String getCountryEng() {
        return countryEng;
    }

    public void setCountryEng(String countryEng) {
        this.countryEng = countryEng;
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

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", titleEng='" + titleEng + '\'' +
                ", cityEng='" + cityEng + '\'' +
                ", countryEng='" + countryEng + '\'' +
                ", descriptionEng='" + descriptionEng + '\'' +
                ", images=" + Arrays.toString(images) +
                ", file='" + file + '\'' +
                '}';
    }
}
