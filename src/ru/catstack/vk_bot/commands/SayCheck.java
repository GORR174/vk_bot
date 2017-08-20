package ru.catstack.vk_bot.commands;

import ru.catstack.vk_bot.model.Message;

import java.io.IOException;

public class SayCheck extends BaseCommand {

    public SayCheck(Message message, String body) {
        super(message, body);
    }

    @Override
    public boolean checkCommand() throws IOException {
        if(body.contains("скажи, что ") || body.contains("скажи: ")){

            String sayMessage;

            if(body.contains("скажи, что ")){
                sayMessage = message.getBody().substring(body.indexOf("скажи, что ")+ "скажи, что ".length());
            } else {
                sayMessage = message.getBody().substring(body.indexOf("скажи: ")+"скажи: ".length());
            }

            vkApi.sendMessage(sayMessage, ""+message.getUid());
            return true;
        }
        return false;
    }
}
