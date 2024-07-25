package hello.kssoftware.member;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class JdbcMemberRepository implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean isUserIdExists(Member member) {
        String sql = "select count(*) from userInfo where userId = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, member.getUserId());
        return count > 0;
    }

    @Override
    public void save(Member member) {
        String sql = "insert into userInfo values (\'" + member.getUserId() + "\',\'" + member.getPwd() + "\',\'" + member.getUserName() + "\'," + member.getUserNum() + ")";
        jdbcTemplate.update(sql);
    }

    @Override
    public void delete(Member member) {
        String sql = "delete from userInfo where userId = ?";
        jdbcTemplate.update(sql, member.getUserId());
    }

    @Override
    public boolean signin(Member member) {
        String sql = "select pwd from userInfo where userId = ?";
        try {
            String pwd = jdbcTemplate.queryForObject(sql, String.class, member.getUserId());
            return member.getPwd().equals(pwd);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
