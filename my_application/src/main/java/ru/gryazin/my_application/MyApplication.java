package ru.gryazin.my_application;

import java.util.Random;
import java.util.List;

import com.vk.api.sdk.actions.Messages;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.callback.MessageNew;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.messages.MessagesAllowMessagesFromGroupQuery;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gryazin.my_application.model.CallbackApiLongPollHandler;


@SpringBootApplication
public class MyApplication {

	public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
		SpringApplication.run(MyApplication.class, args);
	}
}


