package ru.catstack.vk_bot.model;

public class LocalizeMessage {
    private String fieldName;
    private String message;

//    public LocalizeMessage(String fieldName, String message) {
//        this.fieldName = fieldName;
//        this.message = message;
//    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
