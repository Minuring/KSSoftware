package hello.kssoftware.board.free.dto;

import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.common.Board;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FreeBoardDto extends BoardDto {

    public static Class<? extends BoardDto> Create;

    @Getter @Setter
    public static class Search extends BoardDto.Search {
        protected String type = "FreeBoard";
    }

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
