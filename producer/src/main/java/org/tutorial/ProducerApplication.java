package org.tutorial;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableScheduling
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class);
  }

  @Bean
  CommandLineRunner commandLineRunner(AmqpTemplate amqpTemplate) {
    return args -> {
      for (int counterValue = 0; counterValue < 100; counterValue++) {
        System.out.println("Send counter " + counterValue);
        var message = new Message(Integer.toString(counterValue).getBytes());
        amqpTemplate.send("counter", "cnt", message);
      }
    };
  }

  //@Component
  static class Publisher {

    private final AmqpTemplate amqpTemplate;
    private final AtomicInteger counter = new AtomicInteger(0);

    public Publisher(AmqpTemplate amqpTemplate) {
      this.amqpTemplate = amqpTemplate;
    }

    //@Scheduled(fixedRate = 2000)
    public void sendCounter() {
      var counterValue = counter.incrementAndGet();
      //System.out.println("Send counter " + counterValue);
      var message = new Message(Integer.toString(counterValue).getBytes());
      amqpTemplate.send("counter", "cnt", message);
    }
  }
}
