package ru.catstack.vk_bot.commands;

import ru.catstack.vk_bot.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HelloCheck extends BaseCommand {
    public HelloCheck(Message message, String body) {
        super(message, body);
    }

    @Override
    public boolean checkCommand() throws IOException {
        ArrayList<String> helloList = new ArrayList<>();

        helloList.add("Здравствуй");
        helloList.add("Привет!");
        helloList.add("Давно не виделись!");

        ArrayList<String> dHelloList = new ArrayList<>();

        dHelloList.add("привет");
        dHelloList.add("здравствуй");
        dHelloList.add("здравствуйте");
        dHelloList.add("хай");
        dHelloList.add("йоу");

        for (String s : dHelloList) {
            if(body.contains(s)){
                vkApi.sendMessage(helloList.get(new Random().nextInt(helloList.size())), ""+message.getUid());
                return true;
            }
        }

        return false;
    }
}
