package hello.kssoftware.board.free.dto;

import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.board.common.BoardResponse;
import hello.kssoftware.board.free.entity.FreeBoard;
import hello.kssoftware.login.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FreeBoardResponse extends BoardResponse {

    public FreeBoardResponse(Long id, Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, Integer views, List<Comment> comments) {
        super(id, "free", writer, title, content, createDate, updateDate, views, comments);
    }

    public static FreeBoardResponse from(FreeBoard board) {
        return new FreeBoardResponse(
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