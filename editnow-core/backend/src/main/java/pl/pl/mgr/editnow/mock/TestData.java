package pl.pl.mgr.editnow.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.dto.action.ActionStatus;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.repository.ActionChainRepository;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.repository.ImageRepository;
import pl.pl.mgr.editnow.repository.UserRepository;
import pl.pl.mgr.editnow.service.configuration.InitializationDatabaseData;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestData implements InitializationDatabaseData {

  private final ActionChainRepository actionChainRepository;
  private final ActionRepository actionRepository;
  private final ImageRepository imageRepository;
  private final UserRepository userRepository;

  @Override
  public void insertData() {
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
    Action actionMorphology = createAction(ActionType.MORPHOLOGY_TRANSFORM, user);

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
    if (actionType == ActionType.MORPHOLOGY_TRANSFORM) {
      Map<String, Integer> parametersMorphology = new LinkedHashMap<>();
      parametersMorphology.put("kernelRows", 4);
      parametersMorphology.put("kernelCols", 5);
      parametersMorphology.put("morphType", 2);
      action.setParameters(parametersMorphology);
    }

    imageRepository.save(inputImage);
    imageRepository.save(outputImage);
    actionRepository.save(action);

    return action;
  }

}
