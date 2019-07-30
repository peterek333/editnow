package pl.pl.mgr.editnow.service.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "finishedAction")
public class FinishedActionReceiver {

  //TODO change to service - related work to user
  private String lastPhotoName;

}
