package hello.kssoftware.board.file.dto;

import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.board.common.BoardResponse;
import hello.kssoftware.board.file.entity.FileBoard;
import hello.kssoftware.board.file.service.FileConverter;
import hello.kssoftware.login.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FileBoardResponse extends BoardResponse {

    private MultipartFile file;

    private FileBoardResponse(Long id, Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, Integer views, List<Comment> comments, MultipartFile file) {
        super(id, writer, title, content, createDate, updateDate, views, comments);
        this.file = file;
    }

    public static FileBoardResponse from(FileBoard board) {
        try {
            return new FileBoardResponse(
                    board.getId(),
                    board.getWriter(),
                    board.getTitle(),
                    board.getContent(),
                    board.getCreateDate(),
                    board.getUpdateDate(),
                    board.getViews(),
                    board.getComments(),
                    FileConverter.convertToMultipartFile(board.getFile())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}