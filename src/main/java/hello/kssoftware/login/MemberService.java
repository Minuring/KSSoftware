package hello.kssoftware.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository jpaMemberRepository;

    public void join(Member member) {
        jpaMemberRepository.save(member);
    }

    public boolean isUserIdExists(String userId) {
        return jpaMemberRepository.findUserId(userId).isPresent();
    }

    public boolean isUserNameExists(String userName) {
        List<Member> members = jpaMemberRepository.findByUserName(userName);
        for (Member member : members) {
            if (member.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }


    public List<Member> findMembers() {
        return jpaMemberRepository.findAll();
    }
}
