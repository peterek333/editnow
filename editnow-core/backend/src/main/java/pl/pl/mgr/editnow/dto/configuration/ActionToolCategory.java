package pl.pl.mgr.editnow.dto.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionToolCategory {
  PREPROCESSING("Pre-processing"),      //Przetwarzanie wstępne
  SEGMENTATION("Segmentation"),   //Segmentacja obrazu
  DETECT_EDGES("Edge detection"),  //Detekcja krawędzi
  MORPHOLOGY_OPERATIONS("Morphological operations")  //Operacje morfologiczne
  ;

  private final String name;

}
