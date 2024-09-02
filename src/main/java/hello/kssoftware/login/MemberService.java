package hello.kssoftware.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository jpaMemberRepository;

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

    public boolean validateLogin(String userId, String pwd) {
        if (jpaMemberRepository.findUserId(userId).isEmpty()) {
            return false;
        }
        return jpaMemberRepository.findUserId(userId).get().getId().equals(userId) &&
                jpaMemberRepository.findUserId(userId).get().getPassword().equals(pwd);
    }

    public List<Member> findMembers() {
        return jpaMemberRepository.findAll();
    }
}
