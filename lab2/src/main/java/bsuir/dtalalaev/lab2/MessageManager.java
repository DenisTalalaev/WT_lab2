package bsuir.dtalalaev.lab2;

public class MessageManager {

    public final static String EXCEPTION = "exception";
    public final static String MESSAGE = "message";

    private Language language;
    private String name;
    private String description;
    public static MessageManager getLoginOccupiedException(Language language){
        switch (language){
            case BY: return new MessageManager(language, "Лагін ўжо заняты","Лагін ўжо заняты, абярыце іншы");
            case EN: return new MessageManager(language, "Login occupied", "Lodin already occupied, choose another one");
            case RU: return new MessageManager(language, "Логин занят", "Логин уже занят, выберите другой");
            default: return getLoginOccupiedException(Language.EN);
        }
    }

    public MessageManager(Language language, String name, String description) {
        this.language = language;
        this.name = name;
        this.description = description;
    }

    public static MessageManager getConnectionException(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Памылка злучэння","Памылка злучэння, паспрабуйце яшчэ раз пазней");
            case EN: return new MessageManager(language, "Connection error", "Connection error, try again later");
            case RU: return new MessageManager(language, "Ошибка соединения", "Ошибка соединения, повторите попытку позже");
            default: return getLoginOccupiedException(Language.EN);
        }
    }

    public static MessageManager getPasswordsAreNotMatchException(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Некарэктны пароль","Некарэктны пароль, паролі не супадаюць");
            case EN: return new MessageManager(language, "Invalid password", "Incorrect password, passwords do not match");
            case RU: return new MessageManager(language, "Некорректный пароль", "Некорректный пароль, пароли не совпадают");
            default: return getPasswordsAreNotMatchException(Language.EN);
        }
    }

    public static MessageManager getRegistrationCompletedMessage(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Паспяховая рэгістрацыя", "рэгістрацыя прайшла паспяхова");
            case EN: return new MessageManager(language, "Successful registration", "Registration was successful");
            case RU: return new MessageManager(language, "Успешная регистрация", "Регистрация прошла успешно");
            default: return getRegistrationCompletedMessage(Language.EN);
        }
    }

    public static MessageManager getIncorrectLoginOrPasswordMessage(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Няправільны лагін або пароль", "няправільны лагін або пароль, праверце карэктнасць дадзеных");
            case EN: return new MessageManager(language, "Invalid username or password", "Invalid username or password, check the correctness of the data");
            case RU: return new MessageManager(language, "Неверный логин или пароль", "Неверный логин или пароль, проверьте корректность данных");
            default: return getIncorrectLoginOrPasswordMessage(Language.EN);
        }
    }

    public static Object getWelcomeMessage(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Вы ўвайшлі ў сістэму", " Сардэчна запрашаем!");
            case EN: return new MessageManager(language, "You are logged in", "Welcome!");
            case RU: return new MessageManager(language, "Вы вошли в систему", "Добро пожаловать!");
            default: return getWelcomeMessage(Language.EN);
        }
    }

    public static Object getGetUserListException(Language language) {
        switch (language){
            case BY: return new MessageManager(language, "Памылка", "Памылка атрымання спісу карыстальнікаў");
            case EN: return new MessageManager(language, "Error", "Error getting the list of users");
            case RU: return new MessageManager(language, "Ошибка", "Ошибка получения списка пользователей");
            default: return getWelcomeMessage(Language.EN);
        }
    }

    public Language getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
