package ru.catstack.vk_bot.utils;

import ru.catstack.vk_bot.engine.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IniBuilder {

    private String iniString = "";

    public void addField(String fieldName, String value) {
        iniString += fieldName + " = " + value + "\n";
    }

    public void saveIniFile(File file) throws IOException {
        if (file.exists()) {
            FileWriter writer = new FileWriter(file);
            writer.write(iniString);
            writer.close();
        } else {
            Logger.log("File '" + file.getPath() + "' doesn't exists");
        }
    }
}
