package hello.kssoftware.board.files.dto;

import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.files.service.FileConverter;
import hello.kssoftware.board.files.entity.FilesBoard;
import hello.kssoftware.board.files.validation.ValidFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Getter @Setter
public class FilesBoardDto extends BoardDto {

    public static Class<? extends BoardDto> Create;

    @Getter @Setter
    public static class Search extends BoardDto.Search {
        protected String type = "FilesBoard";
    }

    @Getter @Setter
    public static class Create extends BoardDto.Create{
        @ValidFile
        protected MultipartFile file;
    }

    @Getter @Setter
    public static class Update extends BoardDto.Update{
        protected MultipartFile file;
    }

    @Getter @Setter
    public static class Response extends BoardDto.Response{
        protected MultipartFile file;

        public Response(FilesBoard board) throws IOException {
            super(board);
            this.file = FileConverter.convertToMultipartFile(board.getFile());
        }
    }
}
