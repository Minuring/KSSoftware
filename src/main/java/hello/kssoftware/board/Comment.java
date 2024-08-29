package hello.kssoftware.board;

import hello.kssoftware.board.dto.CommentCreateDto;
import hello.kssoftware.board.dto.CommentUpdateDto;
import hello.kssoftware.login.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Member writer;
    private boolean anonymousYn;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static Comment createComment(Board board,CommentCreateDto dto, LocalDateTime createDate) {
         return new Comment(board,
                dto.getWriter(),
                dto.isAnonymousYn(),
                dto.getContent(),
                createDate);
    }

    public void update(String content, LocalDateTime updateDate) {
        this.content = content;
        this.updateDate = updateDate;
    }


    protected Comment(Board board, Member writer, boolean anonymousYn, String content, LocalDateTime createDate) {
        this.board = board;
        this.writer = writer;
        this.anonymousYn = anonymousYn;
        this.content = content;
        this.createDate = createDate;
    }
}
