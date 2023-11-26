package bsuir.dtalalaev.lab2.entities;

public class User {
    private int userId;
    private String userName;
    private String userLogin;
    private boolean isAdmin;
    private boolean isBlocked;

    // Геттеры и сеттеры

    // Конструктор
    public User(int userId, String userName, String userLogin, boolean isAdmin, boolean isBlocked) {
        this.userId = userId;
        this.userName = userName;
        this.userLogin = userLogin;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
