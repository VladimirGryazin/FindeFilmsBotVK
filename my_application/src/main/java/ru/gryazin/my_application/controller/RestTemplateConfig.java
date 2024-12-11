package ru.gryazin.my_application.controller;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.gryazin.my_application.model.CallbackApiLongPollHandler;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(6000))
                .setReadTimeout(Duration.ofMillis(6000))
                .defaultHeader("X-API-KEY", "")
                .build();
    }
    @Bean
    public CallbackApiLongPollHandler callbackApiLongPollHandler() {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        GroupActor actor = new GroupActor(227197425L, "");
        try {
            vk.groups().setLongPollSettings(actor, 227197425L)
                    .enabled(true)
                    .apiVersion("5.199")
                    .messageNew(true)
                    .messageEvent(true)
                    .execute();
            vk.groupsLongPoll().getLongPollServer(actor, 227197425L).execute();

        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vk, actor, 250);
        return handler;
    }
}
