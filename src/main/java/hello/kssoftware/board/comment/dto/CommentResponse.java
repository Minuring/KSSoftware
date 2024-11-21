package hello.kssoftware.board.comment.dto;

import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.login.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Board board;
    private Member writer;
    private boolean anonymousYn;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private CommentResponse() {
    }

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.board = comment.getBoard();
        response.writer = comment.getWriter();
        response.anonymousYn = comment.isAnonymousYn();
        response.content = comment.getContent();
        response.createDate = comment.getCreateDate();
        response.updateDate = comment.getUpdateDate();
        return response;
    }
}