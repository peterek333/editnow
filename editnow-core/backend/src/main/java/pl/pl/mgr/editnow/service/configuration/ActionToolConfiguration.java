package pl.pl.mgr.editnow.service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.configuration.ActionToolCategory;
import pl.pl.mgr.editnow.repository.ActionToolRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ActionToolConfiguration implements InitializationDatabaseData {

  private final ActionToolRepository actionToolRepository;

  private final Map<ActionToolCategory, List<ActionType>> actionTypesInCategory = createActionTypesInCategory();

  private Map<ActionToolCategory, List<ActionType>> createActionTypesInCategory() {
    Map<ActionToolCategory, List<ActionType>> actionTypesInCategory = new HashMap<>();

    actionTypesInCategory.put(ActionToolCategory.PREPROCESSING,
      Arrays.asList(ActionType.GRAYSCALE));

    actionTypesInCategory.put(ActionToolCategory.MORPHOLOGY_OPERATIONS,
      Arrays.asList(ActionType.MORPHOLOGY_TRANSFORM));

    return actionTypesInCategory;
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

    return actionTool;
  }

}
