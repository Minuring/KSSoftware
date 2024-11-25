package hello.kssoftware.board.file.service;

import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.board.common.BoardSearch;
import hello.kssoftware.board.file.dto.FileBoardCreate;
import hello.kssoftware.board.file.dto.FileBoardResponse;
import hello.kssoftware.board.file.dto.FileBoardUpdate;
import hello.kssoftware.board.file.entity.FileBoard;
import hello.kssoftware.board.file.entity.UploadFile;
import hello.kssoftware.login.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
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

    public FileBoardResponse findById(Long boardId) {
        FileBoard board = (FileBoard) boardRepository.findById(boardId);
        return FileBoardResponse.from(board);
    }

    public List<FileBoardResponse> findAll(BoardSearch dto) {
        return boardRepository.findAll(dto)
                .stream()
                .filter(board -> board instanceof FileBoard)
                .map(board -> (FileBoard) board)
                .map(FileBoardResponse::from)
                .toList();
    }

    public UrlResource getUrlResource(Long boardId) throws MalformedURLException {
        FileBoard board = (FileBoard) boardRepository.findById(boardId);

        String storeFileName = board.getFile().getStoreFileName();
        return new UrlResource("file:" + fileStore.getFullPath(storeFileName));
    }

    public String getContentDisposition(Long boardId) {
        FileBoard board = (FileBoard) boardRepository.findById(boardId);

        String uploadFileName = board.getFile().getUploadFileName();
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        return "attachment; filename=\"" + encodedUploadFileName + "\"";
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }
}
