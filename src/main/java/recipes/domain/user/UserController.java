package recipes.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserDtoValidator userDtoValidator;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, UserDtoValidator userDtoValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userDtoValidator = userDtoValidator;
    }

    @PostMapping("/api/register")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        if (userDtoValidator.isValid(userDto)) {
            User user = userMapper.mapToUser(userDto);
            if(userService.findUserByEmail(user.getEmail()) == null) {
                user = userService.saveUser(user);
                return new ResponseEntity<>(Map.of("id", user.getId()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
