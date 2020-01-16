package pl.pl.mgr.editnow.service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.domain.configuration.ParameterInfoOption;
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
      Arrays.asList(ActionType.GRAYSCALE, ActionType.ROTATE, ActionType.RESIZE, ActionType.MEDIAN_BLUR,
        ActionType.GAUSSIAN_BLUR, ActionType.BILATERAL_FILTER));

    actionTypesInCategory.put(ActionToolCategory.SEGMENTATION,
      Arrays.asList(ActionType.THRESHOLD));

    actionTypesInCategory.put(ActionToolCategory.MORPHOLOGY_OPERATIONS,
      Arrays.asList(ActionType.MORPHOLOGY_TRANSFORM));

    return actionTypesInCategory;
  }

  private Map<ActionType, List<ParameterInfo>> createParameterInfosForActionType() {
    Map<ActionType, List<ParameterInfo>> parameterInfosForActionType = new HashMap<>();

    parameterInfosForActionType.put(ActionType.ROTATE,
      Collections.singletonList(new ParameterInfo("angle", ParameterType.INT)));

    parameterInfosForActionType.put(ActionType.RESIZE, Arrays.asList(
      new ParameterInfo("width", ParameterType.INT),
      new ParameterInfo("height", ParameterType.INT),
      new ParameterInfo("interpolationType", ParameterType.OPTION, createResizeOptions())));

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

    parameterInfosForActionType.put(ActionType.THRESHOLD, Arrays.asList(
      new ParameterInfo("threshold", ParameterType.INT),
      new ParameterInfo("maxval", ParameterType.INT),
      new ParameterInfo("thresholdType", ParameterType.OPTION, createThresholdOptions())));

    parameterInfosForActionType.put(ActionType.MORPHOLOGY_TRANSFORM, Arrays.asList(
      new ParameterInfo("kernelRows", ParameterType.INT),
      new ParameterInfo("kernelCols", ParameterType.INT),
      new ParameterInfo("morphType", ParameterType.OPTION, createMorphologyTransformOptions())));

    return parameterInfosForActionType;
  }

  private List<ParameterInfoOption> createResizeOptions() {
    return Arrays.asList(
      new ParameterInfoOption("INTER_NEAREST", "0"),
      new ParameterInfoOption("INTER_LINEAR", "1"),
      new ParameterInfoOption("INTER_CUBIC", "2"),
      new ParameterInfoOption("INTER_AREA", "3"),
      new ParameterInfoOption("INTER_LANCZOS4", "4")
    );
  }

  private List<ParameterInfoOption> createThresholdOptions() {
    return Arrays.asList(
      new ParameterInfoOption("THRESH_BINARY", "0"),
      new ParameterInfoOption("THRESH_BINARY_INV", "1"),
      new ParameterInfoOption("THRESH_TRUNC", "2"),
      new ParameterInfoOption("THRESH_TOZERO", "3"),
      new ParameterInfoOption("THRESH_TOZERO_INV", "4")
    );
  }

  private List<ParameterInfoOption> createMorphologyTransformOptions() {
    return Arrays.asList(
      new ParameterInfoOption("MORPH_ERODE", "0"),
      new ParameterInfoOption("MORPH_DILATE", "1"),
      new ParameterInfoOption("MORPH_OPEN", "2"),
      new ParameterInfoOption("MORPH_CLOSE", "3"),
      new ParameterInfoOption("MORPH_GRADIENT", "4"),
      new ParameterInfoOption("MORPH_TOPHAT", "5"),
      new ParameterInfoOption("MORPH_BLACKHAT", "6")
    );
  }

  @Override
  public void insertData() {
    List<ActionTool> actionTools = new ArrayList<>();

    for(Map.Entry<ActionToolCategory, List<ActionType>> actionTypesInCategoryEntry: actionTypesInCategory.entrySet()) {
      ActionToolCategory actionToolCategory = actionTypesInCategoryEntry.getKey();

      for(ActionType actionType: actionTypesInCategoryEntry.getValue()) {
        if ( !actionToolRepository.existsActionToolByActionTypeAndActionToolCategory(actionType, actionToolCategory)) {
          ActionTool actionTool = createActionTool(actionToolCategory, actionType);

          actionTools.add(actionTool);
        }
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
