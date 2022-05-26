package ge.propertygeorgia.catalogue.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;


@RestController
@RequestMapping(path = "/api/header")
public class HeaderController {

    private final HeaderService headerService;

    @Autowired
    public HeaderController(HeaderService headerService) {
        this.headerService = headerService;
    }

    @GetMapping()
    public Header getHeader() {
        return headerService.getHeader();
    }

    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postProperty(
            @RequestPart("image1") MultipartFile image1
            , @RequestPart("image2") MultipartFile image2
            , @RequestPart("image3") MultipartFile image3
            , @RequestPart("image4") MultipartFile image4
            , @RequestPart("file1") MultipartFile file1
            , @RequestPart("file2") MultipartFile file2
            , @RequestPart("description") String description
            , @RequestPart("descriptionEng") String descriptionEng
    ) throws IOException {
        String[] images = new String[4];
        String[] files = new String[2];
        if (image1 != null) {
            String imageName1 = saveFile(image1, IMAGES_DIRECTORY, image1.getOriginalFilename());
            images[0] = "assets/images/" + imageName1;
        }
        if (image2 != null) {
            String imageName2 = saveFile(image2, IMAGES_DIRECTORY, image2.getOriginalFilename());
            images[1] = "assets/images/" + imageName2;
        }
        if (image3 != null) {
            String imageName3 = saveFile(image3, IMAGES_DIRECTORY, image3.getOriginalFilename());
            images[2] = "assets/images/" + imageName3;
        }
        if (image4 != null) {
            String imageName4 = saveFile(image4, IMAGES_DIRECTORY, image4.getOriginalFilename());
            images[3] = "assets/images/" + imageName4;
        }
        if (file1 != null) {
            String fileName1 = saveFile(file1, FILE_DIRECTORY, file1.getOriginalFilename());
            files[0] = "assets/" + fileName1;
        }
        if (file2 != null) {
            String fileName2 = saveFile(file2, FILE_DIRECTORY, file2.getOriginalFilename());
            files[1] = "assets/" + fileName2;
        }

        Header header = new Header();
        header.setDescription(description);
        header.setDescriptionEng(descriptionEng);
        header.setImages(images);
        header.setFiles(files);

        headerService.postHeader(header);
        return new ResponseEntity<Object>("sent", HttpStatus.OK);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public void updateHeader(@RequestPart(value = "image1", required = false) MultipartFile image1
            , @RequestPart(value = "image2", required = false) MultipartFile image2
            , @RequestPart(value = "image3", required = false) MultipartFile image3
            , @RequestPart(value = "image4", required = false) MultipartFile image4
            , @RequestPart(value = "file1", required = false) MultipartFile file1
            , @RequestPart(value = "file2", required = false) MultipartFile file2
            , @RequestPart(value = "description", required = false) String description
            , @RequestPart(value = "descriptionEng", required = false) String descriptionEng
            , @RequestPart(value = "imageAddress1", required = false) String imageAddress1
            , @RequestPart(value = "imageAddress2", required = false) String imageAddress2
            , @RequestPart(value = "imageAddress3", required = false) String imageAddress3
            , @RequestPart(value = "imageAddress4", required = false) String imageAddress4
            , @RequestPart(value = "fileAddress1", required = false) String fileAddress1
            , @RequestPart(value = "fileAddress2", required = false) String fileAddress2) throws IOException {
        String[] images = new String[4];
        String[] files = new String[2];
        int index = 0;
        int index2 = 0;
        if (imageAddress1 != null){
            images[index++] = imageAddress1;
        }
        if (imageAddress2 != null){
            images[index++] = imageAddress2;
        }
        if (imageAddress3 != null){
            images[index++] = imageAddress3;
        }
        if (imageAddress4 != null){
            images[index] = imageAddress4;
        }
        if (image1 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[index++]);
            images[index] = "";
        } else {
            String imageName1 = saveFile(image1, IMAGES_DIRECTORY, image1.getOriginalFilename());
            images[index] = "assets/images/" + imageName1;
        }
        if (image2 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[index++]);
            images[index] = "";
        } else {
            String imageName2 = saveFile(image2, IMAGES_DIRECTORY, image2.getOriginalFilename());
            images[index] = "assets/images/" + imageName2;
        }
        if (image3 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[index++]);
            images[index] = "";
        } else {
            String imageName3 = saveFile(image3, IMAGES_DIRECTORY, image3.getOriginalFilename());
            images[index] = "assets/images/" + imageName3;
        }
        if (image4 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[index++]);
            images[index] = "";
        } else {
            String imageName4 = saveFile(image4, IMAGES_DIRECTORY, image4.getOriginalFilename());
            images[index] = "assets/images/" + imageName4;
        }
        if (fileAddress1 != null){
            images[index2++] = fileAddress1;
        }
        if (fileAddress2 != null){
            images[index] = fileAddress2;
        }
        if (file1 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getFiles()[index2++]);
            files[index2] = "";
        } else {
            String fileName1 = saveFile(file1, FILE_DIRECTORY, file1.getOriginalFilename());
            files[index2] = "assets/" + fileName1;
        }
        if (file2 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getFiles()[index2++]);
            files[index2] = "";
        } else {
            String fileName2 = saveFile(file2, FILE_DIRECTORY, file2.getOriginalFilename());
            files[index2] = "assets/" + fileName2;
        }

//        if(description==null){
//            description = "";
//        }
//        if(descriptionEng==null){
//            descriptionEng = "";
//        }
        headerService.updateHeader(description, descriptionEng, images, files);
    }

    public static String saveFile(MultipartFile file, String directory, String fileName) {
        try {
            Files.write(Paths.get(directory + fileName)
                    , file.getBytes()
                    , StandardOpenOption.CREATE);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
