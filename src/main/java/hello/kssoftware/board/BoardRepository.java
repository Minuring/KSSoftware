package hello.kssoftware.board;

import hello.kssoftware.board.dto.BoardSearchDto;

import java.util.List;

public interface BoardRepository {
    Long save(Board board);

    Board findById(Long id);

    void delete(Long id);

    List<Board> findAll(BoardSearchDto boardSearchDto);

}