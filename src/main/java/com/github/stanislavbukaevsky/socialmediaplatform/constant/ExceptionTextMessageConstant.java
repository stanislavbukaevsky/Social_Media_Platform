package com.github.stanislavbukaevsky.socialmediaplatform.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех исключений в приложении
 */
public class ExceptionTextMessageConstant {
    public static final String AUTHORIZATION_MESSAGE_EXCEPTION_CONTROLLER = "Вы ввели неправильный логин или пароль! Попробуйте ввести другую комбинацию.";
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE = "Пользователь с комбинацией логина и электронной почты ";
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE_2 = " уже существует! Попробуйте ввести другие данные.";
    public static final String FIND_USER_BY_USERNAME_MESSAGE_EXCEPTION_SERVICE = "Пользователя, с таким логином не существует в базе данных. Попробуйте ввести другой логин.";
    public static final String POST_NOT_FOUND_EXCEPTION = "Поста с таким идентификатором не существует!";
    public static final String RESPONSE_STATUS_EXCEPTION = "Пользователя с таким идентификатором не существует, либо у вас нет прав доступа к этому ресурсу.";
    public static final String IMAGE_NOT_FOUND_EXCEPTION = "Изображения с таким идентификатором не существует!";
    public static final String USER_ID_NOT_FOUND_EXCEPTION = "Пользователя с таким идентификатором не существует! Попробуйте ввести другой идентификатор";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Вы не правильно сделали запрос. Нельзя отправить заявку в друзья самому себе! Попробуйте другую операцию";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_2 = "Вы не правильно сделали запрос. Идентификатора, с такой подпиской нет! Попробуйте другой идентификатор";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_3 = "Вы не можете отправить личное сообщение этому пользователю, так как вы не являетесь друзьями! Попробуйте другую операцию";
    public static final String SUBSCRIPTION_NOT_FOUND_EXCEPTION = "Запроса подписки с таким идентификатором не существует! Попробуйте ввести другой идентификатор";
    public static final String ACTIVITY_FEED_NOT_FOUND_EXCEPTION = "У вас нет новых постов от подписчиков и друзей, так как у вас ни одного подписчика!";
}
