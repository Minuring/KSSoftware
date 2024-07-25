package hello.kssoftware.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);

    List<Board> findByTitle(String title);

    List<Board> findByWriter(String writer);

    List<Board> findAll();

}