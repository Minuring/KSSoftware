package hello.kssoftware.board.dto;

import hello.kssoftware.board.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentCreateDto {

    private String writer;
    private boolean anonymousYn;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
}
