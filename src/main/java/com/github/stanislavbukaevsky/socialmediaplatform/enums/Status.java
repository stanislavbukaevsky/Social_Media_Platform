package com.github.stanislavbukaevsky.socialmediaplatform.enums;

/**
 * Перечисление для разделения подачи заявки в друзья
 */
public enum Status {
    ACCEPTANCE("Заявка принята"), REJECTION("Заявка отклонена"), CONSIDERATION("Заявка на рассмотрении");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
