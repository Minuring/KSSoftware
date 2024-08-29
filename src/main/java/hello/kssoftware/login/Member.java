package hello.kssoftware.login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter @Setter
@ToString
public class Member {

    @Id
    @Column(name = "userid", nullable = false)
    private String userId;  //로그인 아이디

    @Column(name = "username", unique = true)
    private String userName;  //writer

    @Column(name = "pwd", nullable = false)
    private String pwd; //로그인 비번

    @Column(name = "usernum", nullable = false)
    private int userNum;    //학번

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Member member = (Member) object;
        return userNum == member.userNum && Objects.equals(userId, member.userId) && Objects.equals(userName, member.userName) && Objects.equals(pwd, member.pwd);
    }
}

