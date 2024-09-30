package hello.kssoftware.board.files;

import hello.kssoftware.board.dto.BoardDto;
import hello.kssoftware.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

public class FilesBoardDto extends BoardDto {

    @Getter @Setter
    public static class Create extends BoardDto.Create{
        protected File file;
    }

    @Getter @Setter
    public static class Update extends BoardDto.Update{
        protected File file;
    }

    @Getter @Setter
    public static class Response extends BoardDto.Response{
        protected File file;
        public Response(Board board) {
            super(board);
        }
    }
}
