package ru.catstack.vk_bot.commands;

import ru.catstack.vk_bot.model.Message;
import ru.catstack.vk_bot.api.VKApi;
import ru.catstack.vk_bot.resources.Core;

import java.io.IOException;

public abstract class BaseCommand {

    protected VKApi vkApi = Core.vkApi;

    protected Message message;
    protected String body;

    public BaseCommand(Message message, String body) {
        this.message = message;
        this.body = body;
    }

    public boolean checkCommand() throws IOException {
        return false;
    }
}
