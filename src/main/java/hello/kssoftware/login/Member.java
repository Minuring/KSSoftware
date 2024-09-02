package hello.kssoftware.login;

import hello.kssoftware.login.dto.MemberCreateDto;
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
    @Column(nullable = false)
    private String id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(name, member.name) && Objects.equals(password, member.password) && Objects.equals(number, member.number);
    }

    public static Member createMember(MemberCreateDto memberCreateDto) {
        Member member = new Member();
        member.id = memberCreateDto.getId();
        member.name = memberCreateDto.getName();
        member.password = memberCreateDto.getPassword();
        member.number = memberCreateDto.getNumber();
        return member;
    }
}

