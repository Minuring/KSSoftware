package hello.kssoftware.board.common;

import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.login.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardResponse {
    
    protected Long id;
    protected Member writer;
    protected String title;
    protected String content;
    protected LocalDateTime createDate;
    protected LocalDateTime updateDate;
    protected Integer views = 0;
    protected List<Comment> comments;

    protected BoardResponse(Long id, Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, Integer views, List<Comment> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.views = views;
        this.comments = comments;
    }

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getWriter(),
                board.getTitle(),
                board.getContent(),
                board.getCreateDate(),
                board.getUpdateDate(),
                board.getViews(),
                board.getComments()
        );
    }
}
