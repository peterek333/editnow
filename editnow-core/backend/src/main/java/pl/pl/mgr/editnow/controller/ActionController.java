package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.service.ActionService;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {

  private final ActionService actionService;

  @PostMapping
  public Action startAction(@RequestBody ActionRequest actionRequest) {
    return actionService.startAction(actionRequest);
  }

  @PostMapping("/test")
  public ActionRequest startActionTest(@RequestBody ActionRequest actionRequest) {
    return actionRequest;
  }

  @PostMapping("/chain/new")
  public ActionChain newActionChain() {
    return actionService.newActionChain();
  }

}
