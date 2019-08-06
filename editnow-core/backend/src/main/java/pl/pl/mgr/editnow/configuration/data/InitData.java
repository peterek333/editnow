package pl.pl.mgr.editnow.configuration.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.mock.MockData;
import pl.pl.mgr.editnow.service.FileStorageService;

@Component
@RequiredArgsConstructor
public class InitData {

  private final FileStorageService fileStorageService;
  private final MockData mockData;

  @EventListener(ApplicationReadyEvent.class)
  public void createDirectoriesAndMockData() {
    fileStorageService.createDirectories();
    mockData.mock(fileStorageService);
  }

}
