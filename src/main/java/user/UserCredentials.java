package user;

public class UserCredentials {
    private final String email;
    private final String password;
    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public UserCredentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
    public static UserCredentials from(User user) {
        return new UserCredentials(user);
    }
}
