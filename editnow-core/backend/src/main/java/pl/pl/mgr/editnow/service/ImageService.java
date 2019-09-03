package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionQueueItem;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ImageDetails;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.repository.ImageRepository;
import pl.pl.mgr.editnow.service.queue.ActionSender;
import pl.pl.mgr.editnow.service.util.ImageFactory;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final FileStorageService fileStorageService;
    private final ActionSender actionSender;
    private final UserService userService;

    private final ImageFactory imageFactory;

    private final ImageRepository imageRepository;
    private final ActionRepository actionRepository;

    public ImageDetails getTestSkiImage() throws IOException {
        showFiles("./");
        String imageBase64 = fileStorageService.loadImageInBase64("skis.jpeg");
//        byte[] imageBytes = Files.readAllBytes(Paths.get("files/skis.jpeg"));
//        byte[] imageBytes = Files.readAllBytes(Paths.get("../files/skis_resized.jpg"));


        return new ImageDetails(
          imageBase64,
          ImageType.JPG);
    }

    public String transformToGrayscale(ActionRequest actionRequest) {
        String actionName = "grayscale";
        Image inputImage = saveInputImage(actionRequest);
        Image outputImage = createOutputImageMetadata(actionName, inputImage.getName(), actionRequest.getImageType());

        Action action = new Action();
        action.setInputImage(inputImage);
        action.setOutputImage(outputImage); //TODO set when output image was created
        action.setStatus(ActionStatus.PENDING);
//        action.setUser(userService.getUserFromContext());
        actionRepository.save(action);

      ActionQueueItem actionQueueItem = ActionQueueItem.builder() //TODO mapper
        .actionId(action.getId())
        .actionName(actionName)
        .inputImageName(inputImage.getName())
        .imageBase64(actionRequest.getImageBase64())
        .build();

        actionSender.send(actionQueueItem);

        return action.getOutputImage().getName();
    }

    public Image createOutputImageMetadata(String actionName, String inputImageName, ImageType imageType) {
        Image image = new Image();
        image.setName(actionName + "_" + inputImageName);
        image.setType(imageType);
        imageRepository.save(image);

        return image;
    }

    public Image saveInputImage(ActionRequest actionRequest) {
      String imageName = "testUUID";
    //    fileStorageService.saveRenamedToUUID(actionRequest.getImageBase64(), actionRequest.getImageType());

      Image image = imageFactory.getInputImage(imageName, actionRequest.getImageType());
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


    public ImageDetails getBase64Image(String imageName) throws IOException {
        //TODO create mapper
        Image image = imageRepository.findByName(imageName);

        return new ImageDetails(
          fileStorageService.loadImageInBase64(imageName),
          image.getType());
    }
}
