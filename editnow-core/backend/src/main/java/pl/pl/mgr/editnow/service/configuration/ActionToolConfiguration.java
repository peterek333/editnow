package pl.pl.mgr.editnow.service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.configuration.ActionToolCategory;
import pl.pl.mgr.editnow.domain.configuration.ParameterInfo;
import pl.pl.mgr.editnow.dto.configuration.ParameterType;
import pl.pl.mgr.editnow.repository.ActionCodeRepository;
import pl.pl.mgr.editnow.repository.ActionToolRepository;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ActionToolConfiguration implements InitializationDatabaseData {

  //TODO usunac po testach
  private final ActionCodeRepository actionCodeRepository;
  private final ActionToolRepository actionToolRepository;

  private final Map<ActionToolCategory, List<ActionType>> actionTypesInCategory = createActionTypesInCategory();
  private final Map<ActionType, List<ParameterInfo>> parameterInfosForActionType = createParameterInfosForActionType();

  private Map<ActionToolCategory, List<ActionType>> createActionTypesInCategory() {
    Map<ActionToolCategory, List<ActionType>> actionTypesInCategory = new HashMap<>();

    actionTypesInCategory.put(ActionToolCategory.PREPROCESSING,
      Arrays.asList(ActionType.GRAYSCALE, ActionType.RGB_CHANNEL, ActionType.ROTATE, ActionType.MEDIAN_BLUR,
        ActionType.GAUSSIAN_BLUR, ActionType.BILATERAL_FILTER));

    actionTypesInCategory.put(ActionToolCategory.MORPHOLOGY_OPERATIONS,
      Arrays.asList(ActionType.MORPHOLOGY_TRANSFORM));

    actionTypesInCategory.put(ActionToolCategory.SEGMENTATION,
      Arrays.asList(ActionType.THRESHOLD));

    return actionTypesInCategory;
  }

  private Map<ActionType, List<ParameterInfo>> createParameterInfosForActionType() {
    Map<ActionType, List<ParameterInfo>> parameterInfosForActionType = new HashMap<>();

    parameterInfosForActionType.put(ActionType.ROTATE,
      Collections.singletonList(new ParameterInfo("angle", ParameterType.INT)));

    parameterInfosForActionType.put(ActionType.MEDIAN_BLUR,
      Collections.singletonList(new ParameterInfo("kSize", ParameterType.INT)));

    parameterInfosForActionType.put(ActionType.GAUSSIAN_BLUR, Arrays.asList(
      new ParameterInfo("kernelWidth", ParameterType.INT),
      new ParameterInfo("kernelHeight", ParameterType.INT),
      new ParameterInfo("sigmaX", ParameterType.INT)));

    parameterInfosForActionType.put(ActionType.BILATERAL_FILTER, Arrays.asList(
      new ParameterInfo("d", ParameterType.INT),
      new ParameterInfo("sigmaColor", ParameterType.INT),
      new ParameterInfo("sigmaSpace", ParameterType.INT)));

    return parameterInfosForActionType;
  }

  @Override
  public void insertData() {
    List<ActionTool> actionTools = new ArrayList<>();

    for(Map.Entry<ActionToolCategory, List<ActionType>> actionTypesInCategoryEntry: actionTypesInCategory.entrySet()) {
      ActionToolCategory actionToolCategory = actionTypesInCategoryEntry.getKey();

      for(ActionType actionType: actionTypesInCategoryEntry.getValue()) {
        ActionTool actionTool = createActionTool(actionToolCategory, actionType);

        actionTools.add(actionTool);
      }
    }

    actionToolRepository.saveAll(actionTools);
  }

  private ActionTool createActionTool(ActionToolCategory actionToolCategory, ActionType actionType) {
    ActionTool actionTool = new ActionTool();
    actionTool.setActionType(actionType);
    actionTool.setActionToolCategory(actionToolCategory);
    actionTool.setParameterInfos(findParameterInfo(actionType));
    actionTool.setDisabled(
      !actionCodeRepository.existsActionCodeByActionType(actionType));

    return actionTool;
  }

  private List<ParameterInfo> findParameterInfo(ActionType actionType) {
    List<ParameterInfo> parameterInfos = parameterInfosForActionType.get(actionType);
    return parameterInfos != null
      ? parameterInfos
      : Collections.emptyList();
  }

}
