package hello.kssoftware.board.free.dto;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.login.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.File;
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
        super.title = title;
        super.content = content;
        super.updateDate = updateDate;
    }
}
