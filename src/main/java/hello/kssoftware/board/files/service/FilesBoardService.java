package hello.kssoftware.board.files.service;

import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.common.BoardService;
import hello.kssoftware.board.files.dto.FilesBoardDto;
import hello.kssoftware.board.files.entity.FilesBoard;
import hello.kssoftware.board.files.entity.UploadFile;
import hello.kssoftware.board.common.BoardRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FilesBoardService extends BoardService<FilesBoard, FilesBoardDto> {
    private final FileStore fileStore;

    public FilesBoardService(BoardRepository<FilesBoard> boardRepository, FileStore fileStore) {
        super(boardRepository);
        this.fileStore = fileStore;
    }

    public Long createBoard(BoardDto.Create createDto) throws IOException {
        FilesBoardDto.Create dto = (FilesBoardDto.Create) createDto;
        UploadFile attachFile = fileStore.storeFile(dto.getFile());
        FilesBoard board = FilesBoard.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .file(attachFile)
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(BoardDto.Update updateDto) throws IOException {
        FilesBoardDto.Update dto = (FilesBoardDto.Update) updateDto;
        FilesBoard board = boardRepository.findById(dto.getId());

        if (dto.getFile().isEmpty()) {
            board.update(dto.getTitle(), dto.getContent(), now(), board.getFile());
        } else {
            UploadFile attachFile = fileStore.storeFile(dto.getFile());
            board.update(dto.getTitle(), dto.getContent(), now(), attachFile);
        }
    }
}
