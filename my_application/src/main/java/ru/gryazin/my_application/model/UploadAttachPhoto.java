package ru.gryazin.my_application.model;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
@Component
public class UploadAttachPhoto {

    public String uploadPhoto(VkApiClient vk, GroupActor actor, String urlPhoto){
        String attOut = null;
        try {
            var image = ImageIO.read(new URL(urlPhoto));
            File tmpFile = null;
            tmpFile = Files.createTempFile("vk", ".jpg").toFile();
            ImageIO.write(image, "jpg", tmpFile);

            var urlOut = vk   // получаем адрес сервера для заливки  картинки
                    .photos()
                    .getMessagesUploadServer(actor)
                    .execute().getUploadUrl();

            var uploadResponse = vk.upload().photo(urlOut.toString(), tmpFile).execute();
            var savedImage = vk.photos().saveMessagesPhoto(actor, uploadResponse.getPhoto())
                    .server(uploadResponse.getServer())
                    .hash(uploadResponse.getHash())
                    .execute().get(0);

            var attach = String.format("photo%d_%d", savedImage.getOwnerId(), savedImage.getId());
            attOut = String.join(",", attach);
        }catch (ApiException | ClientException | RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return attOut;
    }
}
