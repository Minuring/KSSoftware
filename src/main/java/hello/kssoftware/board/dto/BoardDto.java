package hello.kssoftware.board.dto;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.entity.Comment;
import hello.kssoftware.login.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public abstract class BoardDto {

    @Getter @Setter
    public static class Search {
        private String title;
        private String writerName;
    }

    @Getter @Setter
    public static class Create {
        private Member writer;
        @NotEmpty(message = "제목을 입력하세요.")
        private String title;
        @NotEmpty(message = "내용을 입력하세요.")
        private String content;
    }

    @Getter @Setter
    public static class Update {
        private Long id;
        private String title;
        private String content;
    }

    // delete는 ID만 필요하므로 DTO를 만들지 않았음

    @Getter @Setter
    public static class Response {
        private Long id;
        private Member writer;
        private String title;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private Integer views = 0;
        private List<Comment> comments;

        public Response(Board board) {
            this.id = board.getId();
            this.writer = board.getWriter();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.createDate = board.getCreateDate();
            this.updateDate = board.getUpdateDate();
            this.views = board.getViews();
            this.comments = board.getComments();
        }
    }
}
