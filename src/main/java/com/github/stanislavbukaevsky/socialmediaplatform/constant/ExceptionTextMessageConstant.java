package com.github.stanislavbukaevsky.socialmediaplatform.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех исключений в приложении
 */
public class ExceptionTextMessageConstant {
    public static final String AUTHENTICATION_MESSAGE_EXCEPTION_SERVICE = "Вы ввели неправильный логин или пароль! Попробуйте ввести другую комбинацию логина и пароля.";
    public static final String GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_EXCEPTION_SERVICE = "Вы ввели невалидный refresh-токен! Введите актуальный токен для выполнения операции.";
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE = "Пользователь с комбинацией логина: ";
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE_2 = " и электронной почтой: ";
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE_3 = " уже существует! Попробуйте ввести другие логин и электронную почту.";
    public static final String FIND_USER_BY_USERNAME_MESSAGE_EXCEPTION_SERVICE = "Пользователя, с таким логином не существует в базе данных. Попробуйте ввести другой логин.";
    public static final String POST_NOT_FOUND_EXCEPTION = "Поста с таким идентификатором не существует!";
    public static final String RESPONSE_STATUS_EXCEPTION = "Пользователя с таким идентификатором не существует, либо у вас нет прав доступа к этому ресурсу.";
    public static final String IMAGE_NOT_FOUND_EXCEPTION = "Изображения с таким идентификатором не существует!";
    public static final String USER_ID_NOT_FOUND_EXCEPTION = "Пользователя с таким идентификатором не существует! Попробуйте ввести другой идентификатор";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Вы не правильно сделали запрос. Нельзя отправить заявку в друзья самому себе! Попробуйте другую операцию";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_2 = "Вы не правильно сделали запрос. Идентификатора, с такой подпиской нет! Попробуйте другой идентификатор";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_3 = "Вы не можете отправить личное сообщение этому пользователю, так как вы не являетесь друзьями или вас забанили! Попробуйте другую операцию";
    public static final String SUBSCRIPTION_NOT_FOUND_EXCEPTION = "Запроса подписки с таким идентификатором не существует! Попробуйте ввести другой идентификатор";
    public static final String ACTIVITY_FEED_NOT_FOUND_EXCEPTION = "У вас нет новых постов от подписчиков и друзей, так как у вас ни одного подписчика!";
    public static final String AUTH_EXCEPTION = "У вас нет прав на это действие! Либо эта операция для вас невозможна";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_4 = "Вы не правильно выбрали действие! Проверьте правильность введенных данных";
    public static final String RESPONSE_STATUS_EXCEPTION_2 = "Вы не можете отправить сообщение самому себе! Проверьте правильность введенных данных";
    public static final String BLOCKING_FORBIDDEN_EXCEPTION = "Вы не можете оставить пост, так как вас заблокировали на площадке";
    public static final String BLOCKING_FORBIDDEN_EXCEPTION_2 = "Вы не можете редактировать пост, так как вас заблокировали на площадке";
    public static final String BLOCKING_FORBIDDEN_EXCEPTION_3 = "Вы не можете редактировать изображение, так как вас заблокировали на площадке";
    public static final String BLOCKING_FORBIDDEN_EXCEPTION_4 = "Вы не можете удалить пост, так как вас заблокировали на площадке";
}
