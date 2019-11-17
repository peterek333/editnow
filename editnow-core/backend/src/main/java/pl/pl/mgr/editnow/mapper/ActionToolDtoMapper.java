package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.ActionToolDto;

@Component
public class ActionToolDtoMapper implements Mapper<ActionTool, ActionToolDto> {

  public ActionToolDto map(ActionTool actionTool) {
    ActionToolDto actionToolDto = new ActionToolDto();
    actionToolDto.setName(actionTool.getActionType().name());

    return actionToolDto;
  }

}
