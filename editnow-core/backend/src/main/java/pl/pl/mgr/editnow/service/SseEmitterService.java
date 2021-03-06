package pl.pl.mgr.editnow.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.dto.CompletedAction;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SseEmitterService {

  private final Map<String, SseEmitter> emitters = new HashMap<>();

  private final UserService userService;
  private final ActionService actionService;

  @Transactional
  public void addEmitterForUser(SseEmitter emitter) {
    String userUUID = userService.getUserUUIDFromContext();

    emitters.put(userUUID, emitter);
  }

  @Transactional
  public void removeEmitter() {
    String userUUID = userService.getUserUUIDFromContext();

    emitters.remove(userUUID);
  }

  public void sendCompletedAction(CompletedAction completedAction) {
    Action action = actionService.getAction(completedAction.getActionId());

    String userUUID = action.getUser().getUuid();

    SseEmitter emitter = emitters.get(userUUID);
    if (emitter != null) {
      try {
        emitter.send(new Gson().toJson("Completed action"));
      } catch (IOException e) {
        emitters.remove(userUUID); //dead emitter
        e.printStackTrace();
      }
    }
  }

}
