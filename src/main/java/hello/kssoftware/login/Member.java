package hello.kssoftware.login;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter @Setter
@ToString
@EqualsAndHashCode
public class Member {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer number;

    protected Member() {}

    public static Member createMember(String id, String name, String password, Integer number) {
        Member member = new Member();
        member.id = id;
        member.name = name;
        member.password = password;
        member.number = number;
        return member;
    }

}

