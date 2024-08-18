package hello.kssoftware.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class JpaBoardRepository implements BoardRepository {

    private final EntityManager em;

    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    @Override
    public boolean update(Long id, Board updateParam) {

        Board findBoard = findById(id);
        findBoard.setTitle(updateParam.getTitle());
        findBoard.setContent(updateParam.getContent());
        findBoard.setUpdateDate(updateParam.getUpdateDate());

        return true;
    }

    @Override
    public Board delete(Long id) {
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
        return findBoard;
    }

    @Override
    public List<Board> findAll(BoardSearch boardSearch) {

        String jpql = "select b From Board b";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (StringUtils.hasText(boardSearch.getTitle())) {
            jpql += " where b.title like '%" + boardSearch.getTitle() + "%'";
            isFirstCondition = false;
        }
        //회원 이름 검색
        if (StringUtils.hasText(boardSearch.getWriter())) {
            jpql += isFirstCondition ? " where" : " and";
            jpql += " b.writer like '%" + boardSearch.getWriter() + "%'";
        }
        TypedQuery<Board> query = em.createQuery(jpql, Board.class).setMaxResults(1000); //최대 1000건
//        if (StringUtils.hasText(boardSearch.getTitle())) {
//            query = query.setParameter("title", boardSearch.getTitle());
//        }
//        if (StringUtils.hasText(boardSearch.getWriter())) {
//            query = query.setParameter("writer", boardSearch.getWriter());
//        }
        return query.getResultList();
    }
}
