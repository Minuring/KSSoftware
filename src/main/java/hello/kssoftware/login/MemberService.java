package hello.kssoftware.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member) {
        memberRepository.save(member);
    }

    public void updatePassword(String id, String password) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setPassword(password);
    }
    public void updateName(String id, String name) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setName(name);
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
