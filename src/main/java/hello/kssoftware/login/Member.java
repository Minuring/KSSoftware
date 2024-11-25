package hello.kssoftware.login;

import hello.kssoftware.login.encrypt.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@EqualsAndHashCode
public class Member {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "CHAR(64)")
    private String password;

    @Column(columnDefinition = "CHAR(32)")
    private String salt;

    @Column(nullable = false)
    private Integer number;

    protected Member() {
    }

    public static Member create(String id, String name, String rawPassword, Integer studentNumber) {
        Member member = new Member();
        member.id = id;
        member.name = name;
        member.salt = PasswordEncoder.getSalt();
        member.password = PasswordEncoder.encode(rawPassword, member.salt);
        member.number = studentNumber;
        return member;
    }

    public void updatePassword(String newRawPassword) {
        this.salt = PasswordEncoder.getSalt();
        this.password = PasswordEncoder.encode(newRawPassword, salt);
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}

