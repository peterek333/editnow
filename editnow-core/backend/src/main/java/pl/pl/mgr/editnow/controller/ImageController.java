package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ImageDetails;
import pl.pl.mgr.editnow.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/{imageName}")
  public ImageDetails getImage(@PathVariable String imageName) throws IOException {
    return imageService.getBase64ImageDetails(imageName);
  }

}
