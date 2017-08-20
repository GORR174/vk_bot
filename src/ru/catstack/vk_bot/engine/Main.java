package ru.catstack.vk_bot.engine;

import com.google.gson.Gson;
import ru.catstack.vk_bot.bot.Bot;
import ru.catstack.vk_bot.model.User;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.Lang;
import ru.catstack.vk_bot.resources.LangFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Main {

    public static String version = "v0.5 alpha";
    public static String delimeter = "=-------------------------------------------------=";

    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();

        Core.bot = new Bot();

        Core.lang = gson.fromJson(new InputStreamReader(new FileInputStream(Core.bot.settings.getLangPath()), "UTF-8"), Lang.class);
        Core.lang.setLangPath(Core.bot.settings.getLangPath());
        Core.dialogues = gson.fromJson(new InputStreamReader(new FileInputStream(Core.bot.settings.getDialoguesPath()), "UTF-8"), Lang.class);
        Core.lang.setLangPath(Core.bot.settings.getDialoguesPath());

        Logger.logWithoutTime("VK_Bot (" + version + ") by CatStack - vk.com/catstack\n" + delimeter);

        if (testAuthorization()) {
            Core.bot.onStart();
        }

    }

    private static boolean testAuthorization() throws IOException {

        Logger.log(Core.lang.get(LangFields.AUTH_CHECK.get()));

        File file = new File("account.ini");
        if(file.exists()){
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));

            String login = properties.getProperty("login");
            String password = properties.getProperty("password");

            String request = "https://site.com/api/bot/get?login=" + login + "&password=" + password;
            Requester requester = new Requester();

            Gson gson = new Gson();
            User user = gson.fromJson(requester.sendRequest(request), User.class);

            if (user.getApi_key() != null && user.getFunctional() != null){
                Core.user = user;
                Logger.log(Core.lang.get(LangFields.AUTH_COMPLETE.get()));
                return true;
            }

            Logger.log(Core.lang.get(LangFields.AUTH_FAIL_PSWD.get()));
            return false;

        }

        Logger.log(Core.lang.get(LangFields.AUTH_FAIL_FILE.get()));
        return false;
    }

}
