package ru.catstack.vk_bot.commands;

import ru.catstack.vk_bot.model.Message;
import ru.catstack.vk_bot.resources.Core;
import ru.catstack.vk_bot.resources.DialoguesFields;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RandCheck extends BaseCommand {

    private String exception = Core.dialogues.get(DialoguesFields.ROLL_EXCEPTION.get());

    public RandCheck(Message thisMessage, String body) {
        super(thisMessage, body);
    }

    @Override
    public boolean checkCommand() throws IOException {
        if (body.startsWith("!roll ")) {
            String range = body.substring(6);
            Pattern pattern = Pattern.compile("\\d*[-]\\d*");

            try {
                int num = Integer.parseInt(range);

                if (num > 0) {
                    vkApi.sendMessage(new Random().nextInt(num) + 1 + "", message.getUid() + "");
                } else {
                    vkApi.sendMessage("0", message.getUid() + "");
                }
            } catch (NumberFormatException e) {
                try{
                    if (pattern.matcher(range).matches()) {
                        range = range.replaceAll("-", " ");

                        Scanner sc = new Scanner(range);

                        int startNumber = sc.nextInt();
                        int lastNumber = sc.nextInt();

                        if (lastNumber - startNumber > 0) {
                            vkApi.sendMessage(new Random().nextInt(lastNumber - startNumber + 1) + startNumber + "", message.getUid() + "");
                        } else {
                            throw (new InputMismatchException());
                        }
                    } else {
                        throw (new InputMismatchException());
                    }
                } catch (InputMismatchException ex) {
                    vkApi.sendMessage(exception, message.getUid() + "");
                }
            }
            return true;
        }
        return false;
    }
}
