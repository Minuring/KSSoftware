package hello.kssoftware.board.free.service;

import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.board.common.BoardSearch;
import hello.kssoftware.board.free.dto.FreeBoardCreate;
import hello.kssoftware.board.free.dto.FreeBoardUpdate;
import hello.kssoftware.board.free.entity.FreeBoard;
import hello.kssoftware.login.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hello.kssoftware.board.common.TimeUtils.now;

@Service
@RequiredArgsConstructor
@Transactional
public class FreeBoardService {

    private final BoardRepository boardRepository;

    public Long createBoard(Member member, FreeBoardCreate dto) {
        FreeBoard board = FreeBoard.builder()
                .title(dto.getTitle())
                .writer(member)
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(FreeBoardUpdate dto) {
        FreeBoard board = (FreeBoard) boardRepository.findById(dto.getId());
        board.update(dto.getTitle(), dto.getContent(), now());
    }

    public FreeBoard findById(Long boardId) {
        return (FreeBoard) boardRepository.findById(boardId);
    }

    public List<FreeBoard> findAll(BoardSearch dto) {
        return boardRepository.findAll(dto)
                .stream()
                .filter(board -> board instanceof FreeBoard)
                .map(board -> (FreeBoard) board)
                .toList();
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }
}
