package hello.kssoftware.login.dto;

import hello.kssoftware.login.validation.match.AllPasswordsMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllPasswordsMatch
public class PasswordChangeDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

}
