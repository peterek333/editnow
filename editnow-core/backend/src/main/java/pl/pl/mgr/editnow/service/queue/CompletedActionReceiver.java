package pl.pl.mgr.editnow.service.queue;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.dto.CompletedAction;
import pl.pl.mgr.editnow.service.SseEmitterService;

import java.nio.charset.StandardCharsets;

@Service
@RabbitListener(queues = "${queue.name.completed-action}")
@RequiredArgsConstructor
public class CompletedActionReceiver {

  private final SseEmitterService sseEmitterService;

  @RabbitHandler
  public void receive(String subscribedWordJson) {
    sendCompletedAction(subscribedWordJson);
  }

  @RabbitHandler
  public void receive(byte[] subscribedWordBytes) {
    String subscribedWordJson = new String(subscribedWordBytes, StandardCharsets.UTF_8);

    sendCompletedAction(subscribedWordJson);
  }

  private void sendCompletedAction(String subscribedWordJson) {
    CompletedAction completedAction = new Gson().fromJson(subscribedWordJson, CompletedAction.class);

    sseEmitterService.sendCompletedAction(completedAction);
  }

}
