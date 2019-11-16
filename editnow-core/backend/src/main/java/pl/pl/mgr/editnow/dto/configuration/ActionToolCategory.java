package pl.pl.mgr.editnow.dto.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionToolCategory {
  PREPROCESSING("Przetwarzanie wstępne"),
  SEGMENTATION("Segmentacja obrazu"),
  DETECT_EDGES("Detekcja krawędzi"),
  MORPHOLOGY_OPERATIONS("Operacje morfologiczne")
  ;

  private final String name;

}
