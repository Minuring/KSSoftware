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

    // 2024.09.29
    // 이너클래스 public static class Dto { ~~ } Service 계층 스펙의 DTO로 변경?
    // 컨트롤러에서 사용하는 DTO를 서비스에서 그대로 사용하는 것에 대한 생각
    // 파라미터로 풀면 4개, 5개, .. 늘어남
    // 생성, 수정, 삭제가 현재로선 BoardController만이 사용하므로
    // 당장은 DTO를 그대로 넘기기, 필요하면 유연성에 초점 (엔티티를 받기)
    // 서비스가 DTO변환을 책임, 대신 DTO는 서비스 스펙에 맞춤 즉 컨트롤러가 서비스DTO를 사용한다는 관점

    public Long createBoard(BoardDto.Create dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(BoardDto.Update dto) {
        Board board = boardRepository.findById(dto.getId());
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
    public List<Board> findAll(BoardDto.Search dto) {
        return boardRepository.findAll(dto);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
