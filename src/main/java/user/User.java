package user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private  String name;
    private  String password;
    private  String email;

    public static User getRandom() {
        String name = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@yandex.ru";
        return new User(name, password, email);
    }
}
