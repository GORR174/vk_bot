package ru.catstack.vk_bot.engine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    public static boolean isConsoleOn = true;

    public static void log(String log) {

        String currentTime = new SimpleDateFormat("HH:mm: ").format(Calendar.getInstance().getTime());

        logWithoutTime(log, currentTime);
    }

    public static void logWithoutTime(String log){
        logWithoutTime(log, "");
    }

    public static void logWithoutTime(String log, String prefix){
        if (isConsoleOn)
            System.out.println(prefix + log);
    }
}
