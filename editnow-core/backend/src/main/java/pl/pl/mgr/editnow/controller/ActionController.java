package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.dto.ImageType;
import pl.pl.mgr.editnow.service.ActionCodeService;
import pl.pl.mgr.editnow.service.ActionService;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {

  private final ActionService actionService;
  private final ActionCodeService actionCodeService;

  @PostMapping
  public Action startAction(@RequestBody ActionRequest actionRequest) {
    return actionService.startAction(actionRequest);
  }

  @PostMapping("/test")
  public ActionRequest startActionTest(@RequestBody ActionRequest actionRequest) {
    return actionRequest;
  }

  @PostMapping("/test2")
  public ActionRequest startActionTest2() {
    return new ActionRequest(ActionType.MORPHOLOGY_TRANSFORM, "", ImageType.JPG, null);
  }

  @PostMapping("/chain/new")
  public ActionChain newActionChain() {
    return actionService.newActionChain();
  }

  @GetMapping("/code/generate")
  public String generateCodeFromActionChain() {
    return actionCodeService.generateCodeFromActionChain();
  }

}
