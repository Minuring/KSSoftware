package hello.kssoftware.board;


import hello.kssoftware.board.dto.BoardSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
//@Primary
@Slf4j
public class JdbcBoardRepository implements BoardRepository {
    private final DataSource dataSource;

    public JdbcBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Long save(Board board) {
        String sql = "insert into board(title, writer, create_date, update_date, content) values(?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getWriter());
            pstmt.setString(3, String.valueOf(board.getCreateDate()));
            pstmt.setString(4, String.valueOf(board.getUpdateDate()));
            pstmt.setString(5, board.getContent());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                board.setId(rs.getLong(1));
            } else {
                log.error("ID 조회 실패");
                throw new SQLException("Id 조회 실패");
            }

            return board.getId();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }

    }

    @Override
    public Board findById(Long id) {
        String sql = "select * from board where id = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(id));

            rs = pstmt.executeQuery();
            Board board = new Board();

            if (rs.next()) {
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setCreateDate(LocalDateTime.parse(rs.getString("create_date")));
                board.setCreateDate(LocalDateTime.parse(rs.getString("update_date")));
                board.setContent(rs.getString("content"));
            }

            return board;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    @Override
    public void delete(Long id) {
        Board board = findById(id);

        String sql = "delete from board where id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, String.valueOf(id));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    private void close(Connection connection, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Board> findAll(BoardSearchDto boardSearchDto) {
        String sql = "select * from board";

        boolean isFirstCondition = true;
        if (StringUtils.hasText(boardSearchDto.getTitle())) {
            sql += " where title like '%" + boardSearchDto.getTitle() + "%'";
            isFirstCondition = false;
        }

        if (StringUtils.hasText(boardSearchDto.getWriter())) {
            sql += isFirstCondition ? " where" : " and";
            sql += " writer like '%" + boardSearchDto.getWriter() + "%'";
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setCreateDate(LocalDateTime.parse(rs.getString("create_date")));
                board.setCreateDate(LocalDateTime.parse(rs.getString("update_date")));
                board.setContent(rs.getString("content"));
                board.setViews(rs.getInt("views"));

                boards.add(board);
            }

            return boards;
        } catch (Exception e) {
            log.error("ID 조회 실패");
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
