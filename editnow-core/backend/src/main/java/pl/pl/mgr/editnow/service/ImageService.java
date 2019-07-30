package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.repository.ImageRepository;
import pl.pl.mgr.editnow.service.queue.ActionSender;
import pl.pl.mgr.editnow.service.util.Base64Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final FileStorageService fileStorageService;
    private final ActionSender actionSender;

    private final ImageRepository imageRepository;
    private final ActionRepository actionRepository;

    public String getTestSkiImage() throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get("../files/skis.jpeg"));
//        byte[] imageBytes = Files.readAllBytes(Paths.get("../files/skis_resized.jpg"));

        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public long transformToGrayscale(ActionRequest actionRequest) {
        String actionName = "grayscale";
        Image inputImage = saveImage(actionRequest);
        Image outputImage = createOutputImageMetadata(actionName, inputImage.getName(), actionRequest.getImageType());

        Action action = new Action();
        action.setActionName(actionName);
        action.setInputImage(inputImage);
        action.setOutputImage(outputImage);
        action.setStatus(ActionStatus.PENDING);
        actionRepository.save(action);

        actionSender.send(action);

        return action.getId();
    }

    private Image createOutputImageMetadata(String actionName, String inputImageName, ImageType imageType) {
        Image image = new Image();
        image.setName(actionName + "_" + inputImageName);
        image.setType(imageType);
        imageRepository.save(image);

        return image;
    }

    private Image saveImage(ActionRequest actionRequest) {
        String imageName =
          fileStorageService.saveRenamedToUUID(actionRequest.getImageBase64(), actionRequest.getImageType());

        Image image = new Image();
        image.setName(imageName);
        image.setType(actionRequest.getImageType());
        imageRepository.save(image);

        return image;
    }

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
