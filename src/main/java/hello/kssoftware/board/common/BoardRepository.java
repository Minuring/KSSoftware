package hello.kssoftware.board.common;

import hello.kssoftware.login.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class BoardRepository {

    @PersistenceContext
    protected EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findByMember(Member member) {
        String jpql = "select b From Board b " +
                "left join fetch b.writer w " +
                "where b.writer.id = :id";

        return em.createQuery(jpql, Board.class)
                .setParameter("id", member.getId())
                .getResultList();
    }

    public void delete(Long id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    public List<Board> findAll(BoardSearch search) {
        String jpql = generateJpql(search);
        return em.createQuery(jpql, Board.class).getResultList();
    }

    private String generateJpql(BoardSearch search) {
        String jpql = "select b From Board b left join fetch b.writer w left join fetch b.comments c";

        if (StringUtils.hasText(search.getTitle())) {
            jpql += " where b.title like '%" + search.getTitle() + "%'";
        }

        if (StringUtils.hasText(search.getWriterName())) {
            jpql += jpql.contains("where") ? " and" : " where";
            jpql += " b.writer.name like '%" + search.getWriterName() + "%'";
        }

        return jpql;
    }
}
