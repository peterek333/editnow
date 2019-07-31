package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ImageDetails;
import pl.pl.mgr.editnow.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/test/skis")
  public ImageDetails getTestSkisImage() throws IOException {
    return imageService.getTestSkiImage();
  }

  @GetMapping("/image/{imageName}")
  public ImageDetails getImage(@PathVariable String imageName) throws IOException {
    return imageService.getBase64Image(imageName);
  }

//  @PostMapping("/transformation/grayscale")
//  public String transformToGrayscale(@RequestParam MultipartFile imageBase64) {
//    return imageService.transformToGrayscale(imageBase64);
//  }

  @PostMapping("/transformation/grayscale")
  public String transformToGrayscale(@RequestBody ActionRequest actionRequest) {
    return imageService.transformToGrayscale(actionRequest);
  }

}
