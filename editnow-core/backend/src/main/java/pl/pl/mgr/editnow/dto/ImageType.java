package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {
  PNG("png"), JPG("jpg");

  private final String extension;

}
