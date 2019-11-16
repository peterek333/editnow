package pl.pl.mgr.editnow.configuration.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.mock.MockData;
import pl.pl.mgr.editnow.mock.ActionCodeImporter;
import pl.pl.mgr.editnow.mock.TestData;
import pl.pl.mgr.editnow.service.FileStorageService;
import pl.pl.mgr.editnow.service.configuration.ActionToolConfiguration;

@Component
@RequiredArgsConstructor
public class DatabaseDataConfiguration {

  private final FileStorageService fileStorageService;
  private final MockData mockData;
  private final ActionCodeImporter actionCodeImporter;
  private final ActionToolConfiguration actionToolConfiguration;

  private final TestData testData;

  @EventListener(ApplicationReadyEvent.class)
  public void createDirectoriesAndMockData() {
    fileStorageService.createDirectories();
    mockData.mock(fileStorageService);

    actionCodeImporter.importActionCodes();
    actionToolConfiguration.insertData();

//    testData.insertData();
  }

}
