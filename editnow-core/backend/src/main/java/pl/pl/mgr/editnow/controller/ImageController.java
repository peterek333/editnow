package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/test/skis")
  public String getTestSkisImage() throws IOException {
    return imageService.getTestSkiImage();
  }

//  @PostMapping("/transformation/grayscale")
//  public String transformToGrayscale(@RequestParam MultipartFile imageBase64) {
//    return imageService.transformToGrayscale(imageBase64);
//  }

  @PostMapping("/transformation/grayscale")
  public long transformToGrayscale(@RequestBody ActionRequest actionRequest) {
    return imageService.transformToGrayscale(actionRequest);
  }

}
