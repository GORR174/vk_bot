package ru.catstack.vk_bot.resources;

public enum LangFields {
    AUTH_CHECK("auth_check"),
    AUTH_COMPLETE("auth_complete"),
    AUTH_FAIL_PSWD("auth_fail_pswd"),
    AUTH_FAIL_FILE("auth_fail_file"),
    ADMIN_CHECK("admin_check"),
    ADMIN_COMPLETE("admin_complete"),
    ADMIN_FAIL_FILE("admin_fail_file"),
    CLIENT("client"),
    BOT("bot"),
    ;

    public String fieldName;

    LangFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String get() {
        return fieldName;
    }
}
