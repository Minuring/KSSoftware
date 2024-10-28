package hello.kssoftware.board.common;

import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.login.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public abstract class BoardDto {

    @Getter @Setter
    public static class Search {
        private String title;
        private String writerName;
        private String type;
    }

    @Getter @Setter
    @ToString
    public static class Create {
        protected Member writer;
        @NotEmpty(message = "제목을 입력하세요.")
        protected String title;
        @NotEmpty(message = "내용을 입력하세요.")
        protected String content;
    }


    @Getter @Setter
    public static class Update {
        protected Long id;
        protected String title;
        protected String content;
    }

    // delete는 ID만 필요하므로 DTO를 만들지 않았음

    @Getter @Setter
    public static class Response {
        protected Long id;
        protected Member writer;
        protected String title;
        protected String content;
        protected LocalDateTime createDate;
        protected LocalDateTime updateDate;
        protected Integer views = 0;
        protected List<Comment> comments;

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
