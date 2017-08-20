package ru.catstack.vk_bot.api;

import ru.catstack.vk_bot.engine.Logger;
import ru.catstack.vk_bot.engine.Requester;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.LangFields;

import java.io.IOException;

public class VKApi {

    private String code_param;
    private Requester requester = new Requester();

    public VKApi(String access_token) {
        this.code_param = "access_token="+access_token;
    }

    public String sendMessage(String message, String uid) throws IOException {
        Logger.log(Core.lang.get(LangFields.BOT.get()) + message);
        return sendRequest("messages.send?message="+message+"&"+code_param+"&user_id="+uid);
    }

    public String sendMessageByDomain(String message, String domain) throws IOException {
        Logger.log("B: " + message);
        return sendRequest("messages.send?message="+message+"&"+code_param+"&domain="+domain);
    }

    public String getUnreadedMessages() throws IOException {
        return sendRequest("messages.get?"+code_param+"&out=0&count=200&filters=1");
    }

    public String getUserInfo(String id) throws IOException {
        return sendRequest("users.get?user_ids="+id);
    }

    private String sendRequest(String methodURL) throws IOException {
        return requester.sendRequest("https://api.vk.com/method/"+methodURL);
    }
}
