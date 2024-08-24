package hello.kssoftware.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateDto {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String userId;  //로그인 아이디 zetgi7dk5359  닉네임(zetgi7****)

    @NotEmpty(message = "닉네임을 입력하세요.")
    private String userName;  //writer  //닉네임

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String pwd; //로그인 비번

    @NotEmpty(message = "학번을 입력하세요.")
    private int userNum;    //학번
}
