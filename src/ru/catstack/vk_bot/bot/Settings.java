package ru.catstack.vk_bot.bot;

import ru.catstack.vk_bot.utils.IniBuilder;

public class Settings {

    private String langPath = "lang/default.json";
    private String dialoguesPath = "data/dialogues.json";
    private boolean isEstablished = false;

    public String getLangPath() {
        return langPath;
    }

    public void setLangPath(String langPath) {
        if (langPath != null) {
            this.langPath = langPath;
        }
    }

    public String getDialoguesPath() {
        return dialoguesPath;
    }

    public void setDialoguesPath(String dialoguesPath) {
        if (dialoguesPath != null) {
            this.dialoguesPath = dialoguesPath;
        }
    }

    public boolean isEstablished() {
        return isEstablished;
    }

    public void setEstablished(boolean established) {
        isEstablished = established;
    }

    public void saveSettings() {
        IniBuilder iniBuilder = new IniBuilder();
        iniBuilder.addField("lang", langPath);
        iniBuilder.addField("dialogues", dialoguesPath);
        iniBuilder.addField("established", isEstablished+"");
    }
}
