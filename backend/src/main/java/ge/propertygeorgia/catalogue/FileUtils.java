package ge.propertygeorgia.catalogue;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class FileUtils {
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
        boolean result = new File("./src/main/java/resources/static/" + fileAddress).delete();
        System.out.println(result);
    }
}
