package ru.gryazin.my_application.model;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.events.callback.CallbackApi;
import com.vk.api.sdk.events.longpoll.GroupLongPollApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.callback.MessageNew;
import com.vk.api.sdk.objects.callback.MessageReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gryazin.my_application.model.runer.VKRunner;

import java.util.Random;

public class CallbackApiLongPollHandler extends GroupLongPollApi {
    private final VkApiClient clientvk;
    private final GroupActor actorvk;
    private SendMessage sendMessage;
    @Autowired
    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }
    public CallbackApiLongPollHandler(VkApiClient client, GroupActor actor, int waitTime) {
        super(client, actor, waitTime);
        this.clientvk = client;
        this.actorvk = actor;
    }

    @Override
    public void messageNew(Integer groupId, MessageNew message) {
        String ts = null;
        try {
            ts = clientvk.groupsLongPoll().getLongPollServer(actorvk, 227197425L).execute().getTs();
            clientvk.groupsLongPoll().getLongPollServer(actorvk, 227197425L).execute().setTs(ts);

            sendMessage.sendMessage(clientvk, actorvk, message);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }


}
