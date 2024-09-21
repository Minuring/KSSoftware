package hello.kssoftware.board.entity;

import hello.kssoftware.login.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member writer;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ColumnDefault(value = "0")
    private Integer views = 0;

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    public Board(Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public void update(String title, String content, LocalDateTime updateDate) {
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBoard(this);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
        comment.setBoard(null);
    }

    public Comment getComment(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElse(null);
    }
}
