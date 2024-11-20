package hello.kssoftware.board.file.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileBoardUpdate {
    private Long id;
    private String title;
    private String content;
    private MultipartFile file;
}
