package security_config.demo_jwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RegisterRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String country;
}
