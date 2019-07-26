package pl.pl.mgr.editnow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pl.mgr.editnow.dto.ImageType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

//  @Transactional
//  public String saveRenamedWithUUID(MultipartFile file) {
//    String fileName = getUUID() + ".jpg";
//    //TODO util for manage file path local/cloud + handle imageBase64 type
//    String filePath = "../images/" + fileName;
//    File savedImage = new File(filePath);
//
//    try {
//      file.transferTo(savedImage);
//      return fileName;
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return null;
//  }
  @Transactional
  public String saveRenamedWithUUID(String imageBase64, ImageType imageType) {
    String fileName = getUUID() + '.' + imageType.getExtension();
    //TODO util for manage file path local/cloud + handle imageBase64 type
    Path filePath = Paths.get("../images/", fileName);

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
    boolean unique = false;
    //TODO check exist uuid in directory
//    while(!unique) {
//      String unique =
//    }
    return UUID.randomUUID().toString();
  }


}
