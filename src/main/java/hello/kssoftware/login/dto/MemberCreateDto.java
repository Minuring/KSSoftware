package hello.kssoftware.login.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateDto {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String id;

    @NotEmpty(message = "닉네임을 입력하세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

    @NotNull(message = "학번을 입력하세요.")
    private Integer number;
}
