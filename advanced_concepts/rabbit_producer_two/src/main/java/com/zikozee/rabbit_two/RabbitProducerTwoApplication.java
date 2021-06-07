package com.zikozee.rabbit_two;

import com.zikozee.rabbit_two.entity.DummyMessage;
import com.zikozee.rabbit_two.producer.DummyProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class RabbitProducerTwoApplication implements CommandLineRunner {

    private final DummyProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerTwoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var dummyMessage = new DummyMessage("Now is " + LocalDate.now(), 1);
        producer.sendDummy(dummyMessage);
    }
}
