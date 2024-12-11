package ru.gryazin.my_application.model.runer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gryazin.my_application.controller.RestTemplateConfig;
import ru.gryazin.my_application.model.CallbackApiLongPollHandler;

@Component
public class VKRunner {
    @Autowired
    private CallbackApiLongPollHandler callbackApiLongPollHandler;
    @PostConstruct
    public void vkRun(){
        callbackApiLongPollHandler.run();
    }
}
