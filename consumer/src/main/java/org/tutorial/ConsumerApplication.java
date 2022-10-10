package org.tutorial;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class);
  }

  @RabbitListener(queues = "counter1", exclusive = true, concurrency = "1-1")
  public void read(int data) throws InterruptedException {
    System.out.println(getThreadName() + ": i have got from queue counter " + data);
    TimeUnit.MILLISECONDS.sleep(2437L);
  }

  private String getThreadName(){
    return Thread.currentThread().getName();
  }

  @Bean
  Queue queue() {
    return new Queue("counter", true);
  }
}
