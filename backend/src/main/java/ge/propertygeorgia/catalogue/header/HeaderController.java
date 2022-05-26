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
import java.util.Arrays;
import java.util.Objects;


@RestController
@RequestMapping(path = "/api/header")
public class HeaderController {

    private final HeaderService headerService;
    @Value("${images.upload-dir}")
    String IMAGES_DIRECTORY;
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @Autowired
    public HeaderController(HeaderService headerService) {
        this.headerService = headerService;
    }

    @GetMapping()
    public Header getHeader() {
        return headerService.getHeader();
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postProperty(
            @RequestPart(value = "image1", required = false) MultipartFile image1
            , @RequestPart(value = "image2", required = false) MultipartFile image2
            , @RequestPart(value = "image3", required = false) MultipartFile image3
            , @RequestPart(value = "image4", required = false) MultipartFile image4
            , @RequestPart(value = "file1", required = false) MultipartFile file1
            , @RequestPart(value = "file2", required = false) MultipartFile file2
            , @RequestPart(value = "description", required = false) String description
            , @RequestPart(value = "descriptionEng", required = false) String descriptionEng
    ) {
        String[] images = new String[4];
        String[] files = new String[2];

        images[0] = postFile(image1, IMAGES_DIRECTORY,"assets/images/" );
        images[1] = postFile(image2, IMAGES_DIRECTORY,"assets/images/" );
        images[2] = postFile(image3, IMAGES_DIRECTORY,"assets/images/" );
        images[3] = postFile(image4, IMAGES_DIRECTORY,"assets/images/" );
        files[0] = postFile(file1, FILE_DIRECTORY,"assets/" );
        files[1] = postFile(file2, FILE_DIRECTORY,"assets/" );


        Header header = new Header();
        header.setDescription(description);
        header.setDescriptionEng(descriptionEng);
        header.setImages(images);
        header.setFiles(files);
        headerService.postHeader(header);
        return new ResponseEntity<Object>("The header has been created.", HttpStatus.OK);
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
            , @RequestPart(value = "fileAddress2", required = false) String fileAddress2) {
        String[] oldImages = headerService.getHeader().getImages();
        String[] oldFiles = headerService.getHeader().getFiles();
        String[] images = new String[4];
        String[] files = new String[2];


//      Files.deleteIfExists(FileSystems.getDefault().getPath(headerService.getHeader().getImages()[0]));
        images[0] = updateFile(image1, imageAddress1, IMAGES_DIRECTORY);
        images[1] = updateFile(image2, imageAddress2, IMAGES_DIRECTORY);
        images[2] = updateFile(image3, imageAddress3, IMAGES_DIRECTORY);
        images[3] = updateFile(image4, imageAddress4, IMAGES_DIRECTORY);
        files[0] = updateFile(file1, fileAddress1, FILE_DIRECTORY);
        files[1] = updateFile(file2, fileAddress2, FILE_DIRECTORY);

        deleteFiles(oldImages, images);
        deleteFiles(oldFiles, files);

        headerService.updateHeader(description, descriptionEng, images, files);
    }

    public static String postFile(MultipartFile file, String directory, String path){
        if (file != null) {
            String fileName = String.valueOf(file.hashCode()).concat(file.getOriginalFilename());
            saveFile(file, directory, fileName);
            return path.concat(fileName);
        }
        return "";
    }

    public static void saveFile(MultipartFile file, String directory, String fileName) {
        try {
            Files.write(Paths.get(directory + fileName)
                    , file.getBytes()
                    , StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String updateFile(MultipartFile file, String fileAddress, String directory) {
        if (file == null) {
            return fileAddress;
        } else {
            return postFile(file, directory,"assets/images/");
        }
    }

    public static void deleteFiles(String[] oldFiles, String[] newFiles) {
        for (String oldFile : oldFiles) {
            boolean delete = true;
            for (String newFile : newFiles) {
                if (Objects.equals(oldFile, newFile)) {
                    delete = false;
                    break;
                }
            }
            if (delete) deleteFile(oldFile);
        }
    }

    public static void deleteFile(String fileAddress){
        boolean result = new File(fileAddress).delete();
    }


}
