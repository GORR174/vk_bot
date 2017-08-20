package ru.catstack.vk_bot.resources;

import ru.catstack.vk_bot.model.LocalizeMessage;

import java.util.ArrayList;

public class LangDialogues {
    private ArrayList<LocalizeMessage> localizeMessages = new ArrayList<>();

    public ArrayList<LocalizeMessage> getLocalizeMessages() {
        return localizeMessages;
    }

    public String get(String fieldName) {
        for (LocalizeMessage localizeMessage : localizeMessages) {
            if (localizeMessage.getFieldName().equals(fieldName)) {
                return localizeMessage.getMessage();
            }
        }
        return "Error. Please, add '" + fieldName + "' field to " + Core.bot.settings.getDialoguesPath() + " file";
    }

    public void replace(String fieldName, String newMessage) {
        for (LocalizeMessage localizeMessage : localizeMessages) {
            if (localizeMessage.getFieldName().equals(fieldName)) {
                localizeMessage.setMessage(newMessage);
            }
        }
    }
}
