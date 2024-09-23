package hello.kssoftware.login;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional(readOnly = false)
    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findUserId(String userId){
        Member member = em.find(Member.class, userId);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUserName(String userName) {
        return em.createQuery("select m from Member m where m.name =:userName", Member.class)
                .setParameter("userName", userName)
                .getResultList();
    }

}
