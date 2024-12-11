package ru.gryazin.my_application.model;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gryazin.my_application.model.runer.VKRunner;

import java.util.*;

@Component
public class SendMessage {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MessageOut messageOut;

    public void sendMessage(VkApiClient vk, GroupActor actor, MessageNew message) {

        String messageText = message.getObject().getMessage().getText();
        ResponseEntity<Response> responseEntity = null;
        ResponseEntity<ResponseRandom> responseRandom = null;
        String out = "";

        if (messageText.equals("Случайный фильм")) {
            responseRandom = this.restTemplate
                    .getForEntity("https://api.kinopoisk.dev/v1.4/movie/random?notNullFields=poster.url&notNullFields=rating.kp&rating.kp=5-10"
                            , ResponseRandom.class);

            if (responseRandom.getBody() == null) {
                messageOut.sendMess(vk, actor, "Запрос ничего не вернул", message.getObject().getMessage().getFromId(), "", message.getObject().getMessage().getPeerId());
            }else {

                out = "\n" + responseRandom.getBody().name
                        + "\n\n" + responseRandom.getBody().description + "\n"
                        + "Рейтинг Кинопоиска: "
                        + responseRandom.getBody().rating.kp + "\n";
                String finalOut = out;
                messageOut.sendMess(vk, actor, finalOut, message.getObject().getMessage().getFromId(), responseRandom.getBody().poster.url, message.getObject().getMessage().getPeerId());
            }
        } else {
            responseEntity = this.restTemplate
                    .getForEntity("https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&query="
                                    + message.getObject().getMessage().getText()
                            , Response.class);

            for (Docs el : Objects.requireNonNull(responseEntity.getBody()).docs) {
                if(el.type.equals("movie") && el.rating.kp > 5) {
                    out = "\n" + el.name
                            + "\n\n" + el.description + "\n"
                            + "Рейтинг Кинопоиска: "
                            + el.rating.kp + "\n";
                    String finalOut = out;
                    messageOut.sendMess(vk, actor, finalOut, message.getObject().getMessage().getFromId(), el.poster.url, message.getObject().getMessage().getPeerId());
                }
            }
            if (out.isEmpty()) {
                messageOut.sendMess(vk, actor, "Запрос ничего не вернул", message.getObject().getMessage().getFromId(), "", message.getObject().getMessage().getPeerId());
            }


        }
    }
}
