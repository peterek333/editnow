package pl.pl.mgr.editnow.controller;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.dto.ActionRequest;
import pl.pl.mgr.editnow.dto.ActionType;
import pl.pl.mgr.editnow.dto.ImageType;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {

  @PostMapping
  public ActionRequest startAction() {
    return new ActionRequest(ActionType.MORPHOLOGY, "base64", ImageType.JPG, ImmutableMap.of("param1", 1, "anotherParam", 22));
  }

  @PostMapping("/test")
  public ActionRequest startActionTest(@RequestBody ActionRequest actionRequest) {
    return actionRequest;
  }


}
