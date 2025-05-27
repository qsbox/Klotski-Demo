package User;

public class User {

    private String UserName;
    private String UserPassword;

    public User(String UserName, String UserPassword){
        this.UserName = UserName;
        this.UserPassword = UserPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

}
