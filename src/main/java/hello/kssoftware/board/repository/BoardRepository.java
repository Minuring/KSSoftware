package hello.kssoftware.board.repository;

import hello.kssoftware.board.Board;
import hello.kssoftware.board.dto.BoardSearchDto;

import java.util.List;

public interface BoardRepository {
    Long save(Board board);

    Board findById(Long id);

    void delete(Long id);

    List<Board> findAll(BoardSearchDto boardSearchDto);

}