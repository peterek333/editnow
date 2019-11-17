package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.domain.Image;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.dto.ActionDto;
import pl.pl.mgr.editnow.dto.action.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionQueueItem;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.mapper.ActionDtoMapper;
import pl.pl.mgr.editnow.repository.ActionRepository;
import pl.pl.mgr.editnow.service.queue.ActionSender;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService {

  private final ActionDtoMapper actionDtoMapper;
  private final UserService userService;
  private final ImageService imageService;

  private final ActionSender actionSender;

  private final ActionRepository actionRepository;

  @Transactional
  public Action completeAction(long actionId) {
    Action action = getAction(actionId);
    action.setStatus(ActionStatus.COMPLETED);

    return action;
  }

  @Transactional
  public ActionDto startAction(ActionRequest actionRequest) {
    User user = userService.getUserFromContext();

    Action action = createAction(actionRequest, user);

    user.getActionChain().getActions().add(action);

    ActionQueueItem actionQueueItem = createActionQueueItem(action, actionRequest.getImageBase64());
    actionSender.send(actionQueueItem);

    return actionDtoMapper.map(action);  //TODO handle action on frontend OR change to outputfilename
  }

  private ActionQueueItem createActionQueueItem(Action action, String imageBase64) {
    return ActionQueueItem.builder()
      .actionId(action.getId())
      .actionName(action.getActionType().name())
      .inputImageName(action.getInputImage().getName())
      .outputImageName(action.getOutputImage().getName())
      .imageBase64(imageBase64)
      .build();
  }

  private Action createAction(ActionRequest actionRequest, User user) {
    Image inputImage = imageService.saveInputImage(actionRequest);
    Image outputImage = imageService.createOutputImageMetadata(
      actionRequest.getActionType().name(), inputImage.getName(), actionRequest.getImageType());

    Action action = new Action();
    action.setActionType(actionRequest.getActionType());
    action.setParameters(actionRequest.getParameters());
    action.setInputImage(inputImage);
    action.setOutputImage(outputImage);
    action.setStatus(ActionStatus.PENDING);
    action.setUser(user);
    actionRepository.save(action);

    return action;
  }

  @Transactional
  public ActionChain newActionChain() {
    ActionChain actionChain = new ActionChain();
    actionChain.setActions(new LinkedHashSet<>());

    User user = userService.getUserFromContext();
    user.setActionChain(actionChain);

    return actionChain;
  }

  public Action getAction(long id) {
    return actionRepository.findById(id);
//      .orElseThrow(() -> new Exception("Action id = " + id + " not found"));
  }

  public List<ActionType> getActionTypes() {
    return Arrays.asList(ActionType.values());
  }
}
