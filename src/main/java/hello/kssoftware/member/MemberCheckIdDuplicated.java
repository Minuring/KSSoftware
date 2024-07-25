package hello.kssoftware.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/checkid")
public class MemberCheckIdDuplicated {
    private final DataSource dataSource;

    public MemberCheckIdDuplicated(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping
    public ResponseEntity<String> checkId(@RequestBody Member newmember){
        JdbcMemberRepository jdbcMemberRepository = new JdbcMemberRepository(dataSource);

        if (jdbcMemberRepository.isUserIdExists(newmember)){
            return new ResponseEntity<>("사용불가능한 아이디(중복)", HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>("사용가능한 아이디", HttpStatus.OK);
        }
    }
}