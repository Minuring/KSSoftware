package hello.kssoftware.board;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Component
@Primary
public class JdbcBoardRepository implements BoardRepository {
    private final DataSource dataSource;
    public JdbcBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private String now() {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);

        return sqlDate.toString();
    }

    public Board save(Board board) {
        String sql = "insert into board(title, writer, createdate, updatedate, content) values(?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getWriter());
            pstmt.setString(3, now());
            pstmt.setString(4, now());
            pstmt.setString(5, board.getContent());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                board.setId(rs.getLong(1));
            } else {
                throw new SQLException("Id 조회 실패");
            }

            return board;

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

    public List<Board> findAll() {
        String sql = "select * from board";

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
                board.setCreatedDate(rs.getDate("createdate"));
                board.setUpdatedDate(rs.getDate("updatedate"));
                board.setContent(rs.getString("content"));
                board.setCount(rs.getInt("count"));

                boards.add(board);
            }

            return boards;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    @Override
    public List<Board> findByTitle(String title) {
        String sql = "select * from board where title like ?";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, title);

            rs = pstmt.executeQuery();
            List<Board> boards = new ArrayList<>();

            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setCreatedDate(rs.getDate("createdate"));
                board.setUpdatedDate(rs.getDate("updatedate"));
                board.setContent(rs.getString("content"));

                boards.add(board);
            }

            return boards;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    @Override
    public List<Board> findByWriter(String writer) {
        String sql = "select * from board where writer = ?";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, writer);

            rs = pstmt.executeQuery();
            List<Board> boards = new ArrayList<>();

            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setCreatedDate(rs.getDate("createdate"));
                board.setUpdatedDate(rs.getDate("updatedate"));
                board.setContent(rs.getString("content"));

                boards.add(board);
            }

            return boards;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
