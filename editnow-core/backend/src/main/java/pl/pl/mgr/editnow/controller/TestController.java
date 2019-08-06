package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ImageRepository;

//TODO remove after cloud tests
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  private final ImageRepository imageRepository;

  @GetMapping("/database")
  public Image testDatabase() {
    Image test = imageRepository.findByName("test");

    if (test == null) {
      Image image = new Image();
      image.setType(ImageType.PNG);
      image.setName("test");

      test = imageRepository.save(image);
    }

    return test;
  }

}
