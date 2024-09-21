package hello.kssoftware.board.entity;

import hello.kssoftware.login.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member writer;
    private boolean anonymousYn;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    public void update(String content, boolean anonymousYn, LocalDateTime updateDate) {
        this.content = content;
        this.anonymousYn = anonymousYn;
        this.updateDate = updateDate;
    }

    @Builder
    private Comment(Board board, Member writer, boolean anonymousYn, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.board = board;
        this.writer = writer;
        this.anonymousYn = anonymousYn;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

}
