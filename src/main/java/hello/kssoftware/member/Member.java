package hello.kssoftware.member;

import lombok.Data;

@Data
public class Member {
    private String userId;  //로그인아이디
    private String pwd;     //로그인비번
    private String userName;    //닉네임
    private int userNum;    //학번
}
