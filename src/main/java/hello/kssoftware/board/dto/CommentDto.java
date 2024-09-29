package hello.kssoftware.board.dto;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.entity.Comment;
import hello.kssoftware.login.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class CommentDto {

    @Getter @Setter
    public static class Create {
        private Long boardId;
        private Member writer;
        private boolean anonymousYn;
        @NotEmpty(message = "내용을 입력하세요.")
        private String content;
    }

    @Getter @Setter
    public static class Update {
        private Long boardId;
        private Long commentId;
        @NotEmpty(message = "내용을 입력하세요.")
        private String content;
    }

    @Getter @Setter
    public static class Response {
        private Board board;
        private Member writer;
        private boolean anonymousYn;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public Response(Comment comment) {
            this.board = comment.getBoard();
            this.writer = comment.getWriter();
            this.anonymousYn = comment.isAnonymousYn();
            this.content = comment.getContent();
            this.createDate = comment.getCreateDate();
            this.updateDate = comment.getUpdateDate();
        }
    }

}
