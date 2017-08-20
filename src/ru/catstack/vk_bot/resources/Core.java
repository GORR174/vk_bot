package ru.catstack.vk_bot.resources;

import ru.catstack.vk_bot.model.User;
import ru.catstack.vk_bot.api.VKApi;
import ru.catstack.vk_bot.bot.Bot;

import java.util.ArrayList;

public class Core {
    public static ArrayList<Integer> admins = new ArrayList<>();
    public static boolean isBotOn = true;
    public static User user;
    public static VKApi vkApi;
    public static Bot bot;
    public static Lang lang;
    public static Lang dialogues;
}
