package ge.propertygeorgia.catalogue.header;

public class HeaderDTO {
    private String description;
    private String[] images = new String[4];
    private String[] files = new String[2];

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setFiles(String[] files) {
        this.files = files;
    }
}
