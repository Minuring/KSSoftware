package hello.kssoftware.board.free.dto;

import hello.kssoftware.board.dto.BoardDto;
import hello.kssoftware.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

public class FreeBoardDto extends BoardDto {

    @Getter @Setter
    public static class Create extends BoardDto.Create{}

    @Getter @Setter
    public static class Update extends BoardDto.Update{}

    @Getter @Setter
    public static class Response extends BoardDto.Response{
        public Response(Board board) {
            super(board);
        }
    }
}
