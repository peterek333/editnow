package pl.pl.mgr.editnow.service.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.repository.ImageRepository;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageNameFactoryImpl implements ImageNameFactory {

  private static final String STRING_WILDCARD = "%";

  private final ImageRepository imageRepository;

  @Override
  public String generateName(ImageType imageType) {
    return generateUniqueImageName(imageType.getExtension());
  }

  //fixme - nowe uuidy zamiast dolaczanie MEDIAN_BLUR_output_GRAYSCALE_output itd.
  // bo taki dlugi ciag bedzie wydluzac nazwe pliku, przy na prawde duzej liczbie akcji
  // moze przekroczyc dozwolona dlugosc nazwy pliku (tutaj przyklad ograniczen systemow operacyjnych)
  // + dlugosc to wiecej danych do przeslania w kolejce
  private String generateUniqueImageName(String imageTypeExtension) {
    String uniqueImageName = getRandomUUID();
    while (imageRepository.existsImageByNameLike(uniqueImageName + STRING_WILDCARD)) {
      uniqueImageName = getRandomUUID();  //FIXME generowanie nowej nazwy
      // https://stackoverflow.com/questions/2513573/how-good-is-javas-uuid-randomuuid
      log.debug("Generated new unique image name");
    }
    return uniqueImageName + "." + imageTypeExtension;
  }

  private String getRandomUUID() {
    return UUID.randomUUID().toString();
  }

}
