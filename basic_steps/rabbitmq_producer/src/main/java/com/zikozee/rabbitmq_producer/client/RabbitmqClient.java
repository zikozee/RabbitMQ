package com.zikozee.rabbitmq_producer.client;

import com.zikozee.rabbitmq_producer.entity.RabbitmqQueue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

/**
 * @author : zikoz
 * @created : 06 Jun, 2021
 */

@Service
public class RabbitmqClient {

    public List<RabbitmqQueue> getAllQueues(){
        var webClient = WebClient.create("http://localhost:15672/api/queues");

        var basicAuthHeader = createBasicAuthHeader("guest", "guest");
        return webClient.get().header(HttpHeaders.AUTHORIZATION, basicAuthHeader).retrieve().bodyToMono(
                new ParameterizedTypeReference<List<RabbitmqQueue>>() {
                }).block(Duration.ofSeconds(10));

    }

    public String createBasicAuthHeader(String username, String password){
        var authString = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }
}
