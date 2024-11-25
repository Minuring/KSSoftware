package hello.kssoftware.login;

import hello.kssoftware.login.dto.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(SignUp signUp) {
        Member member = Member.create(
                signUp.getId(),
                signUp.getName(),
                signUp.getPassword(),
                signUp.getStudentNumber()
        );
        memberRepository.save(member);
    }

    public void updatePassword(String id, String password) {
        memberRepository.findById(id)
                .ifPresent(member -> member.updatePassword(password));
    }

    public void updateName(String id, String name) {
        memberRepository.findById(id)
                .ifPresent(member -> member.updateName(name));
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
