package recipes.domain.user;

import org.springframework.stereotype.Component;
import recipes.utils.StringValidator;

@Component
public class UserDtoValidator implements StringValidator {

    String emailPattern = "^[a-zA-Z]([.]?\\w+)*[@](\\w+[.]\\w+)+$";

    public boolean isValid(UserDto userDto) {
        return userDto != null &&
                isValid(userDto.getEmail()) &&
                userDto.getEmail().matches(emailPattern) &&
                isValid(userDto.getPassword()) &&
                userDto.getPassword().length() >= 8;
    }

}
