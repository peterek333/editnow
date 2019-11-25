package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.dto.action.ActionStatus;
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

    private final ImageFactory imageFactory;

    private final ImageRepository imageRepository;

    public Image createOutputImageMetadata(ImageType imageType) {
        return imageFactory.createOutputImageMetadata(imageType);
    }

    public Image saveInputImage(ActionRequest actionRequest) {
      String imageName =
        fileStorageService.saveRenamedToUUID(actionRequest.getImageBase64(), actionRequest.getImageType());

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


    public ImageDetails getBase64ImageDetails(String imageName) throws IOException {
        //TODO create mapper
        Image image = imageRepository.findByName(imageName);

        return new ImageDetails(
          fileStorageService.loadImageInBase64(imageName),
          image.getType());
    }

    public String getBase64(String imageName) throws IOException {
        return fileStorageService.loadImageInBase64(imageName);
    }

}
