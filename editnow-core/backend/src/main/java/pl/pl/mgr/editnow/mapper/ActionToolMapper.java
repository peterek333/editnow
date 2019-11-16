package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ActionTool;
import pl.pl.mgr.editnow.dto.ActionToolDto;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActionToolMapper {

  public ActionToolDto map(ActionTool actionTool) {
    ActionToolDto actionToolDto = new ActionToolDto();
    actionToolDto.setName(actionTool.getActionType().name());

    return actionToolDto;
  }

  public List<ActionToolDto> mapList(List<ActionTool> actionTools) {
    List<ActionToolDto> actionToolDtos = new ArrayList<>();

    for (ActionTool actionTool: actionTools) {
      actionToolDtos.add(map(actionTool));
    }

    return actionToolDtos;
  }

}
