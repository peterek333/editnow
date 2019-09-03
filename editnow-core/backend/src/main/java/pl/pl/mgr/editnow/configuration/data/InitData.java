package pl.pl.mgr.editnow.configuration.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionType;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.mock.MockData;
import pl.pl.mgr.editnow.repository.ActionChainRepository;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.repository.ImageRepository;
import pl.pl.mgr.editnow.repository.UserRepository;
import pl.pl.mgr.editnow.service.FileStorageService;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitData {

  private final FileStorageService fileStorageService;
  private final MockData mockData;
  private final ActionChainRepository actionChainRepository;
  private final ActionRepository actionRepository;
  private final ImageRepository imageRepository;
  private final UserRepository userRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void createDirectoriesAndMockData() {
    fileStorageService.createDirectories();
    mockData.mock(fileStorageService);

    testData();
  }

  public void testData() {
    ActionChain actionChain = new ActionChain();
    Set<Action> actions = new LinkedHashSet<>();
//    actionChainRepository.save(actionChain);

    User user = new User();
    user.setUuid("test");
    user.setActionChain(actionChain);
    userRepository.save(user);

    Action actionGrayscale = createAction(ActionType.GRAYSCALE, user);
    Action actionMorphology = createAction(ActionType.MORPHOLOGY, user);

    actions.add(actionGrayscale);
    actions.add(actionMorphology);
    actionChain.setActions(actions);
    actionChainRepository.save(actionChain);
  }

  private Action createAction(ActionType actionType, User user) {
    Image inputImage = new Image();
    inputImage.setName("input_" + actionType.name());
    inputImage.setType(ImageType.JPG);
    Image outputImage = new Image();
    outputImage.setName("output_" + actionType.name());
    outputImage.setType(ImageType.JPG);

    Action action = new Action();
    action.setInputImage(inputImage);
    action.setOutputImage(outputImage);
    action.setActionType(actionType);
    action.setStatus(ActionStatus.COMPLETED);
    action.setUser(user);

    imageRepository.save(inputImage);
    imageRepository.save(outputImage);
    actionRepository.save(action);

    return action;
  }

}
