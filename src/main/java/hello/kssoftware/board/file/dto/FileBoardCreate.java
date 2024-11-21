package hello.kssoftware.board.file.dto;

import hello.kssoftware.board.file.validation.ValidFile;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileBoardCreate {
    @NotEmpty(message = "제목을 입력하세요.")
    private String title;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
    @ValidFile
    private MultipartFile file;
}
