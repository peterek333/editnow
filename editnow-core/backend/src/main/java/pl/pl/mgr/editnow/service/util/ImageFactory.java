package pl.pl.mgr.editnow.service.util;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.dto.ImageType;

@Component
public class ImageFactory {

  private static final String INPUT_NAME = "input_";
  private static final String OUTPUT_NAME = "output_";

  public Image getInputImage(String name, ImageType imageType) {

    return getImage(INPUT_NAME + name, imageType);
  }

  public Image getOutputImage(String name, ImageType imageType) {

    return getImage(OUTPUT_NAME + name, imageType);
  }

  private Image getImage(String name, ImageType imageType) {
    Image image = new Image();
    image.setName(name);
    image.setType(imageType);

    return image;
  }

}
