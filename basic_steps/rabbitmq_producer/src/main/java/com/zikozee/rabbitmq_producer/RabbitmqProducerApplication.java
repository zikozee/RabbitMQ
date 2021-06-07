package com.zikozee.rabbitmq_producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zikozee.rabbitmq_producer.entity.Employee;
import com.zikozee.rabbitmq_producer.entity.Furniture;
import com.zikozee.rabbitmq_producer.entity.Picture;
import com.zikozee.rabbitmq_producer.customException.MyPictureProducer;
import com.zikozee.rabbitmq_producer.exchange.direct.PictureProducer;
import com.zikozee.rabbitmq_producer.exchange.fanout.HumanResourceProducer;
import com.zikozee.rabbitmq_producer.exchange.header.FurnitureProducer;
import com.zikozee.rabbitmq_producer.exchange.topic.PictureProducer2;
import com.zikozee.rabbitmq_producer.retry.direct.RetryPictureProducer;
import com.zikozee.rabbitmq_producer.retry.fanout.RetryEmployeeProducer;
import com.zikozee.rabbitmq_producer.retry.spring_retry.direct.SpringPictureProducer;
import com.zikozee.rabbitmq_producer.retry.spring_retry.fanout.SpringEmployeeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class RabbitmqProducerApplication implements CommandLineRunner {

    @Autowired
    private HumanResourceProducer fanout;

    // valid sources
    private final List<String> SOURCES = List.of("mobile", "web");
    // valid types
    private final List<String> TYPES = List.of("jpg", "png", "svg");

    @Autowired
    private PictureProducer direct;

    @Autowired
    private PictureProducer2 topic;

    @Autowired
    private MyPictureProducer customError;

    @Autowired
    private RetryPictureProducer retryDirect;

    @Autowired
    private RetryEmployeeProducer retryFanout;

    @Autowired
    private SpringPictureProducer springDirectRetry;

    @Autowired
    private SpringEmployeeProducer springFanoutRetry;


    private final List<String> COLORS = List.of("white", "red", "green");

    private final List<String> MATERIALS = List.of("wood", "plastic", "steel");

    @Autowired
    private FurnitureProducer header;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //switch out the methods to use which u want --> remember to enable service
        springFanoutRetryHandling();
    }

    private void springFanoutRetryHandling() throws JsonProcessingException {
        for (var i = 0; i <1; i++) {
            var employee = Employee.builder()
                    .name("")
                    .employeeId("emp" + i)
                    .birthDate(LocalDate.of(1990, 12, i + 1))
                    .build();
            springFanoutRetry.sendMessage(employee);
        }
    }

    private void springDirectRetryHandling() throws JsonProcessingException {
        for (var i = 0; i <1; i++) {
            var picture = Picture.builder()
                    .name("Test Spring  " + LocalDate.now())
                    .size(ThreadLocalRandom.current().nextLong(9001, 10001))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            springDirectRetry.sendMessage(picture);
        }
    }

    private void retryFanoutHandling() throws JsonProcessingException {
        for (var i = 0; i <10; i++) {
            var employee = Employee.builder()
                    .name("emp-" + i)
                    .employeeId(null)
                    .birthDate(LocalDate.now())
                    .build();
            retryFanout.sendMessage(employee);
        }
    }

    private void retryDirectHandling() throws JsonProcessingException {
        for (var i = 0; i <10; i++) {
            var picture = Picture.builder()
                    .name("Retry Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(9000, 10000))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            retryDirect.sendMessage(picture);
        }
    }

    private void customErrorHandling() throws JsonProcessingException {
        for (var i = 0; i <1; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(9000, 10000))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();

//            direct.sendMessage(picture);
            customError.sendMessage(picture);
        }
    }

    private void fanoutExchangeSample() throws JsonProcessingException {
        for (var i = 0; i <5; i++) {
            var employee = new Employee("emp" + i, "employee" + i, LocalDate.now());
            fanout.sendMessage(employee);
        }
    }

    private void directAndTopicExchangeSample() throws JsonProcessingException {
        for (var i = 0; i <10; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(1, 10000))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();

//            direct.sendMessage(picture);
            topic.sendMessage(picture);
        }
    }

    private void headerExchangeSample() throws JsonProcessingException {
        for (var i = 0; i <10; i++) {
            var furniture = Furniture.builder()
                    .name("Furniture " + i)
                    .color(COLORS.get(i % COLORS.size()))
                    .material(MATERIALS.get(i % MATERIALS.size()))
                    .price(i)
                    .build();

//            direct.sendMessage(picture);
            header.sendMessage(furniture);
        }
    }
}
