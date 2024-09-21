package hello.kssoftware.board.service;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.dto.*;
import hello.kssoftware.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long createBoard(BoardCreateDto dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(Long boardId, BoardUpdateDto dto) {
        Board board = boardRepository.findById(boardId);
        board.update(dto.getTitle(), dto.getContent(), now());
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }

    @Transactional(readOnly = true)
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public List<Board> findAll(BoardSearchDto boardSearchDto) {
        return boardRepository.findAll(boardSearchDto);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
