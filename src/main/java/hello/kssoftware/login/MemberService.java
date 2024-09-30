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

    public boolean isUserIdExists(String userId) {
        return memberRepository.findUserId(userId).isPresent();
    }

    public boolean isUserNameExists(String userName) {
        List<Member> members = memberRepository.findByUserName(userName);
        for (Member member : members) {
            if (member.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
