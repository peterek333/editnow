package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.service.queue.ActionSender;
import pl.pl.mgr.editnow.service.util.Base64Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Base64Converter base64Converter;
    private final FileStorageService fileStorageService;
    private final ActionSender actionSender;

    public String getTestSkiImage() throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get("../files/skis.jpeg"));
//        byte[] imageBytes = Files.readAllBytes(Paths.get("../files/skis_resized.jpg"));

        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public String transformToGrayscale(ActionRequest actionRequest) {
        String imageName =
          fileStorageService.saveRenamedWithUUID(actionRequest.getImageBase64(), actionRequest.getImageType());

        Action action = new Action("grayscale", imageName);
        actionSender.send(action);

        return imageName;
    }

//    public String transformToGrayscale(MultipartFile imageBase64) {
//        String imageName = fileStorageService.saveRenamedWithUUID(imageBase64);
//
//        Action action = new Action("grayscale", imageName);
//        actionSender.send(action);
//
//        return imageName;
//    }

    //TODO remove after debug
    @Deprecated
    public void showFiles(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println("End\n");
    }


}
