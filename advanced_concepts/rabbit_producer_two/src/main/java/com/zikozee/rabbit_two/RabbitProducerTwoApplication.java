package com.zikozee.rabbit_two;

import com.zikozee.rabbit_two.entity.DummyMessage;
import com.zikozee.rabbit_two.producer.DummyProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
public class RabbitProducerTwoApplication implements CommandLineRunner {

    private final DummyProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerTwoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (var i = 0; i < 10_0000; i++) {
            var dummyMessage = new DummyMessage("Now is " + LocalDate.now(), 1);
            producer.sendDummy(dummyMessage);
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
