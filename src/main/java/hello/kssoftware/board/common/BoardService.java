package hello.kssoftware.board.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
public abstract class BoardService<T extends Board, Dto extends BoardDto> {
    protected final BoardRepository<T> boardRepository;

    // 2024.09.29
    // 이너클래스 public static class Dto { ~~ } Service 계층 스펙의 DTO로 변경?
    // 컨트롤러에서 사용하는 DTO를 서비스에서 그대로 사용하는 것에 대한 생각
    // 파라미터로 풀면 4개, 5개, .. 늘어남
    // 생성, 수정, 삭제가 현재로선 BoardController만이 사용하므로
    // 당장은 DTO를 그대로 넘기기, 필요하면 유연성에 초점 (엔티티를 받기)
    // 서비스가 DTO변환을 책임, 대신 DTO는 서비스 스펙에 맞춤 즉 컨트롤러가 서비스DTO를 사용한다는 관점

    public abstract Long createBoard(Dto.Create dto) throws IOException;

    public abstract void updateBoard(Dto.Update dto) throws IOException;

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }

    @Transactional(readOnly = true)
    public T findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(BoardDto.Search dto) {
        return boardRepository.findAll(dto);
    }

    protected LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
