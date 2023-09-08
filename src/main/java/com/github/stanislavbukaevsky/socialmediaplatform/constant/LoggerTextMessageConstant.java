package com.github.stanislavbukaevsky.socialmediaplatform.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех логов в приложении
 */
public class LoggerTextMessageConstant {
    // Логи для методов в контроллерах
    public static final String REGISTRATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для регистрации нового пользователя в контроллере. Логин пользователя: {}. Электронная почта: {}";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для аутентификации зарегистрированного пользователя в контроллере. Логин пользователя: {}";
    public static final String GET_ACCESS_TOKEN_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для генерации нового access-токена для зарегистрированных пользователей в контроллере. Refresh-токен пользователя: {}";
    public static final String GET_REFRESH_TOKEN_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для генерации нового refresh-токена для зарегистрированных пользователей в контроллере. Refresh-токен пользователя: {}";
    public static final String GET_USER_INFO_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра информации об авторизированном пользователе в контроллере";
    public static final String ADD_POST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления нового поста в контроллере";
    public static final String GET_ALL_POST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для получения и просмотра всех постов по его заголовку опубликованных на платформе в контроллере. Заголовок поста: {}";
    public static final String GET_POST_IMAGE_ID_MESSAGE_LOGGER_CONTROLLER = "Вызван метод получения изображения у поста в контроллере. Идентификатор поста: {}";
    public static final String UPDATE_POST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения информации о посте, размещенном на платформе в контроллере. Идентификатор изменяемого поста: {}";
    public static final String UPDATE_POST_IMAGE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод изменения изображения у поста, размещенного на платформе в контроллере. Идентификатор поста: {}";
    public static final String DELETE_POST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод удаления поста с платформы по его идентификатору в контроллере. Идентификатор поста: {}";
    public static final String SENDING_APPLICATION_FRIENDS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод отправки заявки в друзья по идентификатору другого пользователя в контроллере. Идентификатор пользователя, кому отправленая заявка в друзья: {}";
    public static final String CANCEL_SUBSCRIPTION_REQUEST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для отмены подачи заявки в друзья, соответственно отказ быть подписчиком другого пользователя в контроллере. Идентификатор подписки: {}";
    public static final String ADD_FRIEND_REQUEST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления в друзья другого пользователя (подписчика) в контроллере. Идентификатор подписки: {}";
    public static final String REJECT_FRIEND_REQUEST_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для отклонения заявки в друзья от другого пользователя в контроллере. Идентификатор подписки: {}";
    public static final String DELETE_FROM_FRIENDS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для удаления пользователя из друзей и подписки в контроллере. Идентификатор подписки: {}";
    public static final String SEND_MESSAGE_USER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для отправления личных сообщений друзьям пользователя в контроллере. Идентификатор пользователя, получающий личное сообщение: {}";
    public static final String REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для получения личной переписки пользователей в контроллере. Идентификатор пользователя, с которым запрашивается переписка: {}";
    public static final String USER_ACTIVITY_FEED_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для поиска постов от пользователей, на которых он подписан в контроллере. Номер страницы: {}. Количество постов на странице: {}";
    public static final String SEND_MESSAGE_ANY_USERS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для отправки личных сообщений любым пользователям в контроллере. Идентификатор пользователя, кому будет отправлено личное сообщение: {}";
    public static final String CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения роли любым пользователям в контроллере. Идентификатор пользователя, у которого будет изменена роль: {}";
    public static final String CHANGING_BLOCKING_USERS_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения статуса блокировки любым пользователям в контроллере. Идентификатор пользователя, у которого будет изменен статус блокировки: {}";

    // Логи для пакета security
    public static final String GET_USERNAME_MESSAGE_LOGGER_SECURITY = "Вызван метод для получения логина пользователя. Логин пользователя: {}";
    public static final String GET_PASSWORD_MESSAGE_LOGGER_SECURITY = "Вызван метод для получения пароля пользователя. Пароль пользователя: {}";
    public static final String GET_AUTHORITIES_MESSAGE_LOGGER_SECURITY = "Вызван метод для получения роли пользователя. Роль пользователя: {}";
    public static final String LOAD_USER_BY_USERNAME_MESSAGE_LOGGER_SECURITY = "Вызван метод для поиска пользователя по его уникальному логину. Логин пользователя: {}";

    // Логи для методов в сервисах
    public static final String REGISTRATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для регистрации нового пользователя в сервисе. Зарегистрированный пользователь: {}";
    public static final String REGISTRATION_MESSAGE_LOGGER_SERVICE_2 = "Пользователь с логином {} и электронной почтой {} уже существует. Попробуйте ввести другие данные!";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для аутентификации зарегистрированного пользователя в сервисе. Логин пользователя: {}";
    public static final String AUTHENTICATION_MESSAGE_ERROR_LOGGER_SERVICE = "Введен неправильный логин или пароль! Проверьте правильность введенных данных.";
    public static final String GET_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для генерации нового access-токена для зарегистрированных пользователей в сервисе. Refresh-токен пользователя: {}";
    public static final String GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_ERROR_LOGGER_SERVICE = "Введен неправильный refresh-токен! Проверьте правильность введенного токена. Введенный refresh-токен: {}";
    public static final String GET_REFRESH_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для генерации нового refresh-токена для зарегистрированных пользователей в сервисе. Refresh-токен пользователя: {}";
    public static final String GET_GENERATING_JWT_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации ответа с личной информацией о пользователе в сервисе. Логин пользователя: {}";
    public static final String FIND_USER_BY_USERNAME_MESSAGE_LOGGER_SERVICE = "Вызван метод поиска пользователя по его логину в сервисе. Логин пользователя: {}";
    public static final String GET_USER_INFO_MESSAGE_LOGGER_SERVICE = "Вызван метод получения полной информации о пользователе по его логину в сервисе. Логин пользователя: {}";
    public static final String FIND_IMAGE_BY_POST_ID_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска изображения по идентификатору поста в сервисе. Идентификатор поста: {}";
    public static final String ADD_IMAGE_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления изображения к посту в сервисе. Идентификатор поста: {}";
    public static final String UPDATE_IMAGE_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод изменения изображения у поста, размещенного на платформе в сервисе. Идентификатор поста: {}";
    public static final String GET_POST_IMAGE_MESSAGE_LOGGER_SERVICE = "Вызван метод получения изображения у поста, размещенного на платформе по его идентификатору в сервисе. Идентификатор поста: {}";
    public static final String SAVE_IMAGE_POST_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод сохранения изображения у поста, размещенного на платформе в сервисе. Идентификатор поста: {}";
    public static final String GET_EXTENSIONS_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод генерирующий расширение у медиа файла в сервисе. Имя файла: {}";
    public static final String FIND_POST_BY_ID_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска поста по его идентификатору в сервисе. Идентификатор поста: {}";
    public static final String ADD_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления нового поста в сервисе";
    public static final String GET_ALL_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод для получения и просмотра всех постов по его заголовку опубликованных на платформе в сервисе. Заголовок поста: {}";
    public static final String GET_POST_IMAGE_ID_MESSAGE_LOGGER_SERVICE = "Вызван метод получения изображения у поста в сервисе. Идентификатор поста: {}";
    public static final String UPDATE_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод изменения информации о посте, размещенном на платформе в сервисе. Идентификатор изменяемого поста: {}";
    public static final String UPDATE_POST_IMAGE_MESSAGE_LOGGER_SERVICE = "Вызван метод изменения изображения у поста, размещенного на платформе в сервисе. Идентификатор поста: {}";
    public static final String DELETE_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод удаления поста с платформы в сервисе по его идентификатору. Идентификатор поста: {}";
    public static final String CHECK_USERS_BY_POST_MESSAGE_LOGGER_SERVICE = "Вызван метод проверки авторизированного пользователя, размещающего пост и пользователя по его роли в сервисе. Идентификатор поста: {}";
    public static final String SENDING_APPLICATION_FRIENDS_MESSAGE_LOGGER_SERVICE = "Вызван метод отправки заявки в друзья по идентификатору другого пользователя в сервисе. Идентификатор пользователя, кому отправленая заявка в друзья: {}";
    public static final String CANCEL_SUBSCRIPTION_REQUEST_MESSAGE_LOGGER_SERVICE = "Вызван метод для отмены подачи заявки в друзья, соответственно отказ быть подписчиком другого пользователя в сервисе. Идентификатор подписки: {}";
    public static final String ADD_FRIEND_REQUEST_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления в друзья другого пользователя (подписчика) в сервисе. Идентификатор подписки: {}";
    public static final String REJECT_FRIEND_REQUEST_MESSAGE_LOGGER_SERVICE = "Вызван метод для отклонения заявки в друзья от другого пользователя в сервисе. Идентификатор подписки: {}";
    public static final String DELETE_FROM_FRIENDS_MESSAGE_LOGGER_SERVICE = "Вызван метод для удаления пользователя из друзей и подписки в сервисе. Идентификатор подписки: {}";
    public static final String FIND_SUBSCRIPTION_BY_ID_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для поиска подписки по ее уникальному идентификатору в сервисе. Идентификатор подписки: {}";
    public static final String SEND_MESSAGE_USER_MESSAGE_LOGGER_SERVICE = "Вызван метод для отправления личных сообщений друзьям пользователя в сервисе. Идентификатор пользователя, получающий личное сообщение: {}";
    public static final String REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_SERVICE = "Вызван метод для получения личной переписки пользователей в сервисе. Идентификатор пользователя, с которым запрашивается переписка: {}";
    public static final String USER_ACTIVITY_FEED_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска постов от пользователей, на которых он подписан в сервисе. Номер страницы: {}. Количество постов на странице: {}";
    public static final String ADD_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления в базу данных нового refresh-токена в сервисе";
    public static final String FIND_REFRESH_TOKEN_BY_USERNAME_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска refresh-токена по имени пользователя в сервисе. Имя пользователя: {}";
    public static final String SEND_MESSAGE_ANY_USERS_MESSAGE_LOGGER_SERVICE = "Вызван метод для отправки личных сообщений любым пользователям, зарегистрированным на платформе в сервисе. Идентификатор пользователя, получающий личное сообщение: {}";
    public static final String SEND_MESSAGE_ANY_USERS_ERROR_LOGGER_SERVICE = "Произошла ошибка при вызове метода отправки сообщения любым пользователям, зарегистрированным на платформе в сервисе. Идентификатор пользователя, получающий личное сообщение: {}";
    public static final String CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения роли любым пользователям в сервисе. Идентификатор пользователя, у которого будет изменена роль: {}";
    public static final String CHANGING_THE_ROLE_USERS_ERROR_LOGGER_SERVICE = "Произошла ошибка при вызове метода для изменения роли любым пользователям в сервисе. Идентификатор пользователя, у которого будет изменена роль: {}";
    public static final String CHANGING_BLOCKING_USERS_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения статуса блокировки любым пользователям в сервисе. Идентификатор пользователя, у которого будет изменен статус блокировки: {}";
    public static final String CHANGING_BLOCKING_USERS_ERROR_LOGGER_SERVICE = "Произошла ошибка при вызове метода для изменения статуса блокировки любым пользователям в сервисе. Идентификатор пользователя, у которого будет изменен статус блокировки: {}";
}
