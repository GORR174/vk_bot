package ru.catstack.vk_bot.commands;

import ru.catstack.vk_bot.model.Message;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.DialoguesFields;
import ru.catstack.vk_bot.utils.NameParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class AdminCheck extends BaseCommand {

    private Scanner scanner;
    private boolean isExit = false;

    public AdminCheck(Message message, String body) {
        super(message, body);
        scanner = new Scanner(message.getBody());
    }

    private void check(boolean commandResult){
        if (!isExit && commandResult) {
            isExit = true;
        }
    }

    @Override
    public boolean checkCommand() throws IOException {
        for (Integer admin : Core.admins) {
            if (message.getUid() == admin){

                check(say());
                check(sbd());
                check(admins());
                check(add());
                check(remove());
                check(turn());
                check(off());
                check(help());

                if (isExit) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean say() throws IOException {
        if(body.startsWith("/say ") || body.startsWith("/s ")){
            scanner.next();
            String uid = scanner.next();
            String sMessage = scanner.nextLine();

            if(uid.equals("me"))
                vkApi.sendMessage(sMessage, ""+message.getUid());
            else
                vkApi.sendMessage(sMessage, uid);
            vkApi.sendMessage(Core.dialogues.get(DialoguesFields.ADMIN_SAY.get()), ""+message.getUid());

            return true;
        }
        return false;
    }

    private boolean sbd() throws IOException {
        if(body.startsWith("/sbd ")){
            scanner.next();
            String domain = scanner.next();
            String sMessage = scanner.nextLine();

            vkApi.sendMessageByDomain(sMessage, domain);
            vkApi.sendMessage(Core.dialogues.get(DialoguesFields.ADMIN_SAY.get()), ""+message.getUid());

            return true;
        }
        return false;
    }

    private boolean admins() throws IOException {
        if(body.equals("/admins")){

            String admins = "";

            for (Integer id : Core.admins)
                admins += NameParser.parseName(vkApi.getUserInfo(id+"")) + "<br>";

            vkApi.sendMessage(admins, message.getUid()+"");

            return true;
        }
        return false;
    }

    private boolean add() throws IOException {
        if(body.startsWith("/add ")){
            try {
                scanner.next();
                String domain = scanner.next();
                String json = vkApi.getUserInfo(domain);
                String uid = json.substring(json.indexOf("\"uid\":") + 6, json.indexOf(",\"first_"));
                for (Integer id : Core.admins) {
                    if (id == Integer.parseInt(uid)) {
                        vkApi.sendMessage(Core.dialogues.get(DialoguesFields.ALREADY_ADMIN.get()), message.getUid() + "");
                        return true;
                    }
                }
                Core.admins.add(Integer.parseInt(uid));
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.ADD_ADMIN.get()) + NameParser.parseName(vkApi.getUserInfo(uid + "")), message.getUid() + "");

                File file = new File("data/admins.sv");
                File dir = new File("data");

                if(!file.exists()) {
                    dir.mkdirs();
                    file.createNewFile();
                }


                FileWriter writer = new FileWriter(file);
                for (Integer id : Core.admins) {
                    writer.write(id + " ");
                }
                writer.close();

                return true;
            } catch (Exception e) {
                vkApi.sendMessage("Ошибка", message.getUid()+"");
                return true;
            }
        }
        return false;
    }

    private boolean remove() throws IOException {
        if(body.startsWith("/remove ")){
            try {
                scanner.next();
                String domain = scanner.next();
                String json = vkApi.getUserInfo(domain);
                String uid = json.substring(json.indexOf("\"uid\":") + 6, json.indexOf(",\"first_"));
                for (Integer id : Core.admins) {
                    if (id == Integer.parseInt(uid)) {
                        Core.admins.remove(id);

                        File file = new File("data/admins.sv");
                        File dir = new File("data");

                        if(!file.exists()) {
                            dir.mkdirs();
                            file.createNewFile();
                        }


                        FileWriter writer = new FileWriter(file);
                        for (Integer _id : Core.admins) {
                            writer.write(_id + " ");
                        }
                        writer.close();

                        Formatter formatter = new Formatter();

                        String removeAdminString = Core.dialogues.get(DialoguesFields.REMOVE_ADMIN.get());
                        String parseName = NameParser.parseName(vkApi.getUserInfo(uid + ""));

                        vkApi.sendMessage(formatter.format(removeAdminString, parseName).toString(), message.getUid() + "");
                        return true;
                    }
                }
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.REMOVE_DONT_ADMIN.get()), message.getUid() + "");
                return true;
            } catch (Exception e) {
                vkApi.sendMessage("Ошибка", message.getUid()+"");
                return true;
            }
        }
        return false;
    }

    private boolean turn() throws IOException {
        if(body.startsWith("/turn")){
            Core.isBotOn = !Core.isBotOn;

            if(Core.isBotOn)
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.TURN_ON.get()), ""+message.getUid());
            else
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.TURN_OFF.get()), ""+message.getUid());

            return true;
        }
        return false;
    }

    private boolean off() throws IOException {
        if(body.startsWith("/off")){
            for (Integer uid : Core.admins)
                vkApi.sendMessage(Core.dialogues.get(DialoguesFields.BOT_OFF.get()), ""+uid);
            Core.bot.timer.cancel();

            return true;
        }
        return false;
    }

    private boolean help() throws IOException {
        if(body.startsWith("/help")){
            String helpMessage = "-----------------------------------------------" +
                    "<br>Помощь:<br>" +
                    "/help - вызов окна помощи<br>" +
                    "/admins - список всех администраторов бота<br>" +
                    "/add [uid|domain] - добавляет пользователя в администраторы по его UID<br>" +
                    "/remove [uid|domain] - удаляет пользователя из администраторов по его UID<br>" +
                    "/say [uid|me] [message] - отправка сообщения пользователю по его UID ( 183695387 )<br>" +
                    "/sbd [domain] [message] - отправка сообщения пользователю по его короткому адресу ( alt_er_love8 )<br>" +
                    "/turn - включает/выключает бота<br>" +
                    "/off - закрывает бота<br>" +
                    "-----------------------------------------------";
            vkApi.sendMessage(helpMessage, message.getUid()+"");

            return true;
        }
        return false;
    }
}
