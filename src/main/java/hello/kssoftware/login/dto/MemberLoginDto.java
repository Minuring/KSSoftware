package hello.kssoftware.login.dto;

import hello.kssoftware.login.validation.match.Match;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Match
public class MemberLoginDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
