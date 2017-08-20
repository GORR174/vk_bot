package ru.catstack.vk_bot.utils;

public class NameParser {
    public static String parseName(String json){
        String s = "";
        String defaultString = json;
        defaultString = defaultString.substring(defaultString.indexOf("\"first_name\":\"")+14, defaultString.indexOf("\",\"last_n"));
        s += defaultString + " ";
        defaultString = json;
        defaultString = defaultString.substring(defaultString.indexOf("\"last_name\":\"")+13);
        defaultString = defaultString.substring(0, defaultString.indexOf("\""));
        s += defaultString + " - ";
        s += json.substring(json.indexOf("\"uid\":")+6, json.indexOf(",\"first_"));

        return s;
    }
}
