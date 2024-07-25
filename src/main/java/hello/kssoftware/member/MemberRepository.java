package hello.kssoftware.member;

public interface MemberRepository {
    void save(Member member);
    void delete(Member member);
    boolean isUserIdExists(Member member);
    boolean signin(Member member);
}
