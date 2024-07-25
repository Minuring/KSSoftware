package hello.kssoftware.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
class JdbcMemberRepositoryTest {
    private final DataSource dataSource;

    JdbcMemberRepositoryTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Test
    @DisplayName("jdbctest")
    void testJdbc(Member member) {
        JdbcMemberRepository jdbcMemberRepository = new JdbcMemberRepository(dataSource);
        System.out.println(jdbcMemberRepository.isUserIdExists(member));
    }

    @Test
    @DisplayName("savetest")
    void savejdbc() {
        JdbcMemberRepository jdbcMemberRepository = new JdbcMemberRepository(dataSource);
        Member member = new Member();
        member.setUserName("minu");
        member.setPwd("123adf");
        member.setUserId("zetgi1dk5359");
        member.setUserNum(2322323);
        if (jdbcMemberRepository.isUserIdExists(member)){
            System.out.println(jdbcMemberRepository.isUserIdExists(member));
            System.out.println("아이디 중복 존재");
        } else{
            System.out.println(jdbcMemberRepository.isUserIdExists(member));
            System.out.println("입력성공");
        }
    }

    @Test
    @DisplayName("deleteTest")
    void deleteJdbc() {
        JdbcMemberRepository jdbcMemberRepository = new JdbcMemberRepository(dataSource);
        Member member = new Member();
        member.setUserId("zetgi7dk5359");
        member.setPwd("dokyun");
        member.setUserName("dokyun");
        member.setUserNum(2020875010);
        if (jdbcMemberRepository.isUserIdExists(member)){
            jdbcMemberRepository.delete(member);
        }
    }

    @Test
    @DisplayName("LoginTest")
    void signinTest(){
        JdbcMemberRepository jdbcMemberRepository = new JdbcMemberRepository(dataSource);
        Member member = new Member();
        member.setUserId("zetgi8dk5359");
        member.setPwd("daklfjs");
        member.setUserName("daklfjs");
        member.setUserNum(2020875010);
        System.out.println(jdbcMemberRepository.signin(member));
    }
}