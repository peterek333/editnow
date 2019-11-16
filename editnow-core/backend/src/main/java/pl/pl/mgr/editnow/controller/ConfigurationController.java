package pl.pl.mgr.editnow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pl.mgr.editnow.service.configuration.ActionToolService;

@RestController
@RequestMapping("/api/configuration")
@RequiredArgsConstructor
public class ConfigurationController {

  private final ActionToolService actionToolService;

  @GetMapping("/tools")
  public void getActionToolService() {

  }

}
