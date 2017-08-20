package ru.catstack.vk_bot.bot;

import com.google.gson.Gson;
import ru.catstack.vk_bot.api.VKApi;
import ru.catstack.vk_bot.commands.*;
import ru.catstack.vk_bot.engine.Logger;
import ru.catstack.vk_bot.engine.Main;
import ru.catstack.vk_bot.model.Message;
import ru.catstack.vk_bot.model.Messages;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.DialoguesFields;
import ru.catstack.vk_bot.resources.LangFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Bot {

    public static Message thisMessage;
    public static String body;
    private VKApi vkApi;
    public Timer timer;
    public Settings settings = new Settings();
    private ArrayList<BaseCommand> commands = new ArrayList<>();

    public Bot() throws IOException {
        File configFile = new File("config.ini");

        if (configFile.exists()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(configFile));

            settings.setLangPath(properties.getProperty("lang"));
            settings.setDialoguesPath(properties.getProperty("dialogues"));
            settings.setEstablished(Boolean.parseBoolean(properties.getProperty("established")));
        } else {
            Logger.log("File 'config.ini' doesn't exists!");
            System.exit(1);
        }
    }

    public void onStart() {

        timer = new Timer();

        parseAdmins();

        Core.vkApi = new VKApi(Core.user.getApi_key());
        vkApi = Core.vkApi;

        main();
    }

    private void main() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String message = vkApi.getUnreadedMessages();
                    if ((message != null) && (message.contains("\"read_state\":0"))){
                        message = "{\"response\":["+message.substring(message.indexOf(",")+1);
                        Gson gson = new Gson();
                        Messages messages = gson.fromJson(message, Messages.class);
                        Collections.reverse(messages.getResponse());
                        for (Message lastMessage : messages.getResponse()) {
                            thisMessage = lastMessage;
                            if(lastMessage.getRead_state() == 0){
                                messageCheck();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    private void parseAdmins() {

        Logger.logWithoutTime(Main.delimeter);
        Logger.log(Core.lang.get(LangFields.ADMIN_CHECK.get()));

        File file = new File("data/admins.sv");

        if(file.exists()){
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                Logger.log(e.getLocalizedMessage());
            }

            Core.admins = new ArrayList<>();

            int count = 0;
            while (scanner.hasNext()) {
                Core.admins.add(scanner.nextInt());
                count++;
            }

            Logger.log(new Formatter().format(Core.lang.get(LangFields.ADMIN_COMPLETE.get()), count).toString());

            scanner.close();
        } else {
            Logger.log(Core.lang.get(LangFields.ADMIN_FAIL_FILE.get()));
        }
        Logger.logWithoutTime(Main.delimeter);
    }

    private void messageCheck() throws IOException {

        commands = new ArrayList<>();

        body = thisMessage.getBody().toLowerCase();

        commands.add(new SayCheck(thisMessage, body));
        commands.add(new HelloCheck(thisMessage, body));
        commands.add(new RandCheck(thisMessage, body));

        Logger.log(Core.lang.get(LangFields.CLIENT.get()) + thisMessage.getBody());

        if (body.startsWith("/")){
            parseAdmins();
        }

        if (settings.isEstablished()) {
            if (!adminCheck() && Core.isBotOn) {
                for (BaseCommand command : commands) {
                    if (command.checkCommand()) {
                        return;
                    }
                }
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.DONT_KNOWN.get()), "" + thisMessage.getUid());
            }
        } else {
            for (Integer admin : Core.admins) {
                if ((admin == thisMessage.getUid()) && (Setup.setupId == admin)){
                    Setup.setup(thisMessage);
                }

                if ((admin == thisMessage.getUid()) && (Setup.setupId == 0)){
                    Setup.setupId = admin;
                    vkApi.sendMessage("Для работы настройте бота... <br>" +
                            "Чтобы оставить прошлое сообщение, напишите '/skip' <br>" +
                            "----------------------------------------------------<br>" +
                            "Для начала напишите сообщение, которое бот будет писать, когда не знает, что ответить <br>" +
                            "(Прошлое сообщение — '" + Core.dialogues.get(DialoguesFields.DONT_KNOWN.get()) + "')", thisMessage.getUid()+"");
                }
            }
        }
    }

    private boolean adminCheck() throws IOException {
        return new AdminCheck(thisMessage, body).checkCommand();
    }
}
