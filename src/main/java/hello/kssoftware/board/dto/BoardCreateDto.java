package hello.kssoftware.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateDto {

    private String writer;

    private String title;

    private String content;
}
