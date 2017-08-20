package ru.catstack.vk_bot.resources;

public enum DialoguesFields {

    ROLL_EXCEPTION("roll_exception"),
    DONT_KNOWN("dont_known"),
    ADMIN_SAY("admin_say"),
    ALREADY_ADMIN("already_admin"),
    ADD_ADMIN("add_admin"),
    REMOVE_ADMIN("remove_admin"),
    REMOVE_DONT_ADMIN("remove_dont_admin"),
    TURN_ON("turn_on"),
    TURN_OFF("turn_off"),
    BOT_OFF("bot_off"),
    ;

    public String fieldName;

    DialoguesFields (String fieldName) {
        this.fieldName = fieldName;
    }

    public String get() {
        return fieldName;
    }
}
