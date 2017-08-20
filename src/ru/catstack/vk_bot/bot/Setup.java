package ru.catstack.vk_bot.bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.catstack.vk_bot.model.Message;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.DialoguesFields;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Setup {

    public static int setupId = 0;
    public static int count = 0;

    private static ArrayList<IdString> idStrings = new ArrayList<>();

    static {
        idStrings.add(new IdString("dont_known", "")); //must be first

        idStrings.add(new IdString("roll_exception", "Введите сообщение, которое выводит бот при ошибке в команде !roll <br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.ROLL_EXCEPTION.get())));
        idStrings.add(new IdString("admin_say", "Введите сообщение, которое выводит бот при отправке сообщение администратором <br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.ADMIN_SAY.get())));
        idStrings.add(new IdString("add_admin", "Введите сообщение, которое выводит бот при добавлении пользователя в администраторы <br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.ADD_ADMIN.get())));
        idStrings.add(new IdString("already_admin", "Введите сообщение, которое выводит бот при добавлении пользователя в администраторы, если пользователь уже является администратором <br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.ALREADY_ADMIN.get())));
        idStrings.add(new IdString("remove_admin", "Введите сообщение, которое выводит бот при удалении пользователя из администраторов ('%s' - информация об пользователе)<br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.REMOVE_ADMIN.get())));
        idStrings.add(new IdString("remove_dont_admin", "Введите сообщение, которое выводит бот при удалении пользователя из администраторов, если он им не является<br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.REMOVE_DONT_ADMIN.get())));
        idStrings.add(new IdString("turn_on", "Введите сообщение, которое выводит бот при включении<br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.TURN_ON.get())));
        idStrings.add(new IdString("turn_off", "Введите сообщение, которое выводит бот при выключении<br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.TURN_OFF.get())));
        idStrings.add(new IdString("bot_off", "Введите сообщение, которое выводит бот при полном выключении<br>" +
                "Прошлое сообщение — " + Core.dialogues.get(DialoguesFields.BOT_OFF.get())));

    }

    public static void setup (Message message) throws IOException {

        if ((message.getBody().equals("/skip all")) && (count == 0)) {
            Core.bot.settings.setEstablished(true);
            Core.bot.settings.saveSettings();
            Gson gson = new Gson();

            String jsonString = gson.toJson(Core.dialogues);

            new FileWriter(new File(Core.bot.settings.getDialoguesPath())).write(jsonString);
        }

        if (!message.getBody().equals("/skip")) {
            Core.dialogues.replace(idStrings.get(count).field, message.getBody());
        }
        count++;

        if (count == 10) {
            Core.bot.settings.setEstablished(true);
            save();
            Core.vkApi.sendMessage("Настройка завершена!", message.getUid() + "");
        } else {
            Core.vkApi.sendMessage(idStrings.get(count).text, message.getUid() + "");
        }
    }

    private static void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        FileWriter writer = new FileWriter(Core.bot.settings.getDialoguesPath());
        gson.toJson(Core.dialogues, writer);
        writer.close();

        // TODO: 13.08.2017 end setup (save ini file)
    }

    private static class IdString {
        String field = "";
        String text = "";

        IdString(String field, String text) {
            this.field = field;
            this.text = text;
        }
    }
}
