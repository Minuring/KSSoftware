package hello.kssoftware.login;

import hello.kssoftware.login.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository jpaMemberRepository;

    //회원가입
    public void join(Member member) {
        jpaMemberRepository.save(member);
    }

    public boolean isUserIdExists(String userId) {
        if (jpaMemberRepository.findUserId(userId).isEmpty()) {
            return false;
        }
        return jpaMemberRepository.findUserId(userId).get().getUserId().equals(userId);
    }

    public boolean isUserNameExists(String userName) {
        List<Member> members = jpaMemberRepository.findByUserName(userName);
        for (Member member : members) {
            if (member.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateLogin(String userId, String pwd) {
        if (jpaMemberRepository.findUserId(userId).isEmpty()) {
            return false;
        }
        return jpaMemberRepository.findUserId(userId).get().getUserId().equals(userId) &&
                jpaMemberRepository.findUserId(userId).get().getPwd().equals(pwd);
    }

    public List<Member> findMembers() {
        return jpaMemberRepository.findAll();
    }
}
