import user.User;
import user.UserClient;
import org.junit.After;
import org.junit.Before;

public class UserTest {
    protected UserClient userClient = new UserClient();
    protected User user;
    protected String token;
    @Before
    public void setUp() {
        user = User.getRandom();
        token = userClient.postToCreateUser(user).body().path("accessToken");

    }

    @After
    public void tearDown() {
        userClient.deleteUser(user, token);
    }
}
