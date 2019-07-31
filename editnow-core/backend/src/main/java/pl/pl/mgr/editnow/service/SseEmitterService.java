package pl.pl.mgr.editnow.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.dto.CompletedAction;
import pl.pl.mgr.editnow.repository.ActionRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SseEmitterService {

  private final Map<String, SseEmitter> emitters = new HashMap<>();

  private final UserService userService;

  //TODO create services for repositories?
  private final ActionRepository actionRepository;

  public void addEmitterForUser(SseEmitter emitter) {
    String userUUID = userService.getUserUUIDFromContext();

    emitters.put(userUUID, emitter);
  }

  public void removeEmitter() {
    String userUUID = userService.getUserUUIDFromContext();

    emitters.remove(userUUID);
  }

  public void sendCompletedAction(CompletedAction completedAction) {
    Optional<Action> action = actionRepository.findById(completedAction.getId());

    action.ifPresent(a -> {
      String userUUID = a.getUser().getUuid();
      List<Action> actionChain = a.getUser().getActionChain();

      SseEmitter emitter = emitters.get(userUUID);
      if (emitter != null) {
        try {
          emitter.send(new Gson().toJson("Works"));
        } catch (IOException e) {
          emitters.remove(userUUID); //dead emitter
          e.printStackTrace();
        }
      }
    });
  }

}
