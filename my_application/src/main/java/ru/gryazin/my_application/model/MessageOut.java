package ru.gryazin.my_application.model;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.queries.photos.PhotosGetMessagesUploadServerQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import static java.util.Arrays.asList;

@Component
public class MessageOut {
    @Autowired
    private UploadAttachPhoto uploadAttachPhoto;

    public void sendMess(VkApiClient vk, GroupActor actor, String message, Long id, String url, Long peerId) {
        Random random = new Random();
        String photo = uploadAttachPhoto.uploadPhoto(vk, actor, url);
        KeyboardButtonActionText butn1 = new KeyboardButtonActionText().setLabel("Случайный фильм").setType(KeyboardButtonActionTextType.TEXT);
        KeyboardButtonActionText butn2 = new KeyboardButtonActionText().setLabel("Поиск актера").setType(KeyboardButtonActionTextType.TEXT);
        KeyboardButtonActionText butn3 = new KeyboardButtonActionText().setLabel("Случайный фильм").setType(KeyboardButtonActionTextType.TEXT);
        List<List<KeyboardButton>> button = asList(asList(
                new KeyboardButton().setAction
                        //(new KeyboardButtonActionCallback().setLabel("Сохранить").setType(KeyboardButtonActionCallbackType.CALLBACK))));
                                (butn1)));
        Keyboard keyboard = new Keyboard().setOneTime(false).setButtons(button).setInline(false);
        try {
            String ts = vk.groupsLongPoll().getLongPollServer(actor, 227197425L).execute().getTs();
            vk.groupsLongPoll().getLongPollServer(actor, 227197425L).execute().setTs(ts);
            var msg = vk.messages()
                    .sendUserIds(actor)
                    //.userId(id)
                    .peerId(peerId)
                    .message(message)
                    .randomId(random.nextInt(10000));
            if (photo != null)
                msg.attachment(photo);
            msg.keyboard(keyboard);
            msg.execute();


        } catch (ApiException | ClientException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}