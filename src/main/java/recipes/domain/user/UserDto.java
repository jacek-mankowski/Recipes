package recipes.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String email;
    private String password;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
