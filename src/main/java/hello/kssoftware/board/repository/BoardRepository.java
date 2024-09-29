package hello.kssoftware.board.repository;

import hello.kssoftware.board.dto.BoardDto;
import hello.kssoftware.board.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository{

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public void delete(Long id) {
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
    }

    public List<Board> findAll(BoardDto.Search search) {

        String jpql = "select b From Board b left join fetch b.writer w left join fetch b.comments c ";
        boolean isFirstCondition = true;

        //제목으로 검색
        if (StringUtils.hasText(search.getTitle())) {
            jpql += " where b.title like '%" + search.getTitle() + "%'";
            isFirstCondition = false;
        }
        //작성자로 검색
        if (StringUtils.hasText(search.getWriterName())) {
            jpql += isFirstCondition ? " where" : " and";
            jpql += " b.writer.name like '%" + search.getWriterName() + "%'";
        }
        TypedQuery<Board> query = em.createQuery(jpql, Board.class).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}
