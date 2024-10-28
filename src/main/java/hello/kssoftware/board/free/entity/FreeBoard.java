package hello.kssoftware.board.free.entity;

import hello.kssoftware.board.common.Board;
import hello.kssoftware.login.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Entity
@DiscriminatorValue("Free")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard extends Board {

    @Builder
    public FreeBoard(Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        super(writer, title, content, createDate, updateDate);
    }

    public void update(String title, String content, LocalDateTime updateDate) {
        super.update(title, content, updateDate);
    }
}
