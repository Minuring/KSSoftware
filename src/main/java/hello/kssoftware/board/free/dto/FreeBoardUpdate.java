package hello.kssoftware.board.free.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardUpdate {
    private Long id;
    private String title;
    private String content;
}
