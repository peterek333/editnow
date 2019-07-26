package pl.pl.mgr.editnow.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Value("${queue.name.action}")
  private String actionQueueName;

  @Bean
  public Queue actionQueue() {
    return new Queue(actionQueueName);
  }

}
