package hello.kssoftware.board.dto;

import hello.kssoftware.login.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentCreateDto {

    private Long boardId;
    private Member writer;
    private boolean anonymousYn;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
}
