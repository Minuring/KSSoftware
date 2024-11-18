package hello.kssoftware.board.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.List;

@Slf4j
@Repository
public class BoardRepository<T extends Board>{

    @PersistenceContext
    protected EntityManager em;

    public Long save(T board) {
        em.persist(board);
        return board.getId();
    }

    public T findById(Long id) {
        return (T) em.find(Board.class, id);
    }

    public void delete(Long id) {
        T findBoard = (T) em.find(Board.class, id);
        em.remove(findBoard);
    }

    public List<T> findAll(BoardDto.Search search) {
        String jpql = "select b From Board b left join fetch b.writer w left join fetch b.comments c ";
        jpql += "where type(b) = " + search.getType();

        //제목으로 검색
        if (StringUtils.hasText(search.getTitle())) {
            jpql += " and b.title like '%" + search.getTitle() + "%'";
        }
        //작성자로 검색
        if (StringUtils.hasText(search.getWriterName())) {
            jpql += " and b.writer.name like '%" + search.getWriterName() + "%'";
        }
        TypedQuery<Board> query = em.createQuery(jpql, Board.class).setMaxResults(1000);//최대 1000건
        return (List<T>) query.getResultList();
    }
}
