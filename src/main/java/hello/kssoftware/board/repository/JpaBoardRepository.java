package hello.kssoftware.board.repository;

import hello.kssoftware.board.Board;
import hello.kssoftware.board.dto.BoardSearchDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class JpaBoardRepository implements BoardRepository {

    private final EntityManager em;

    @Override
    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    @Override
    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    @Override
    public void delete(Long id) {
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
    }

    @Override
    public List<Board> findAll(BoardSearchDto boardSearchDto) {

        String jpql = "select b From Board b";
        boolean isFirstCondition = true;

        //제목으로 검색
        if (StringUtils.hasText(boardSearchDto.getTitle())) {
            jpql += " where b.title like '%" + boardSearchDto.getTitle() + "%'";
            isFirstCondition = false;
        }
        //작성자로 검색
        if (StringUtils.hasText(boardSearchDto.getWriterName())) {
            jpql += isFirstCondition ? " where" : " and";
            jpql += " b.writer.name like '%" + boardSearchDto.getWriterName() + "%'";
        }
        TypedQuery<Board> query = em.createQuery(jpql, Board.class).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}
