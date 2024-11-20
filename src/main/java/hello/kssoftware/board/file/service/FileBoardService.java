package hello.kssoftware.board.file.service;

import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.board.common.BoardSearch;
import hello.kssoftware.board.file.dto.FileBoardCreate;
import hello.kssoftware.board.file.dto.FileBoardUpdate;
import hello.kssoftware.board.file.entity.FileBoard;
import hello.kssoftware.board.file.entity.UploadFile;
import hello.kssoftware.login.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static hello.kssoftware.board.common.TimeUtils.now;

@Service
@RequiredArgsConstructor
@Transactional
public class FileBoardService {
    private final FileStore fileStore;
    private final BoardRepository boardRepository;

    public Long createBoard(Member member, FileBoardCreate dto) throws IOException {
        UploadFile attachFile = fileStore.storeFile(dto.getFile());
        FileBoard board = FileBoard.builder()
                .title(dto.getTitle())
                .writer(member)
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .file(attachFile)
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(FileBoardUpdate dto) throws IOException {
        FileBoard board = (FileBoard) boardRepository.findById(dto.getId());

        if (dto.getFile().isEmpty()) {
            board.update(dto.getTitle(), dto.getContent(), now(), board.getFile());
            return;
        }

        UploadFile attachFile = fileStore.storeFile(dto.getFile());
        board.update(dto.getTitle(), dto.getContent(), now(), attachFile);
    }

    public FileBoard findById(Long boardId) {
        return (FileBoard) boardRepository.findById(boardId);
    }

    public List<FileBoard> findAll(BoardSearch dto) {
        return boardRepository.findAll(dto)
                .stream()
                .filter(board -> board instanceof FileBoard)
                .map(board -> (FileBoard) board)
                .toList();
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }
}
