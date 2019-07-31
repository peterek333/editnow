package pl.pl.mgr.editnow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.dto.ImageType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

  private static final String IMAGES_DIRECTORY = "../images/";

  @Transactional
  public String saveRenamedToUUID(String imageBase64, ImageType imageType) {
    String fileName = getUUID() + '.' + imageType.getExtension();
    //TODO util for manage file path local/cloud + handle imageBase64 type
    Path filePath = Paths.get(IMAGES_DIRECTORY, fileName);

    try {
      byte[] decodedImageBytes = Base64.getDecoder().decode(imageBase64.getBytes(StandardCharsets.UTF_8));
      Files.write(filePath, decodedImageBytes);
      return fileName;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getUUID() {
    return UUID.randomUUID().toString();
  }


  public String loadImageInBase64(String imageName) throws IOException {
    byte[] imageBytes = Files.readAllBytes(Paths.get(IMAGES_DIRECTORY, imageName));

    return Base64.getEncoder().encodeToString(imageBytes);
  }
}
