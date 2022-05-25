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
    public void updateHeader(@RequestPart("image1") MultipartFile image1
            , @RequestPart("image2") MultipartFile image2
            , @RequestPart("image3") MultipartFile image3
            , @RequestPart("image4") MultipartFile image4
            , @RequestPart("file1") MultipartFile file1
            , @RequestPart("file2") MultipartFile file2
            , @RequestPart("description") String description
            , @RequestPart("descriptionEng") String descriptionEng) throws IOException {
        String[] images = new String[4];
        String[] files = new String[2];
        if (image1 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[0]);
            images[0] = "";
        } else {
            String imageName1 = saveFile(image1, IMAGES_DIRECTORY, image1.getOriginalFilename());
            images[0] = "assets/images/" + imageName1;
        }
        if (image2 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[1]);
            images[1] = "";
        } else {
            String imageName2 = saveFile(image2, IMAGES_DIRECTORY, image2.getOriginalFilename());
            images[1] = "assets/images/" + imageName2;
        }
        if (image3 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[2]);
            images[2] = "";
        } else {
            String imageName3 = saveFile(image3, IMAGES_DIRECTORY, image3.getOriginalFilename());
            images[2] = "assets/images/" + imageName3;
        }
        if (image4 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getImages()[3]);
            images[3] = "";
        } else {
            String imageName4 = saveFile(image4, IMAGES_DIRECTORY, image4.getOriginalFilename());
            images[3] = "assets/images/" + imageName4;
        }
        if (file1 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getFiles()[0]);
            files[0] = "";
        } else {
            String fileName1 = saveFile(file1, FILE_DIRECTORY, file1.getOriginalFilename());
            files[0] = "assets/" + fileName1;
        }
        if (file2 == null) {
            FileSystems.getDefault().getPath(headerService.getHeader().getFiles()[1]);
            files[1] = "";
        } else {
            String fileName2 = saveFile(file2, FILE_DIRECTORY, file2.getOriginalFilename());
            files[1] = "assets/" + fileName2;
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
