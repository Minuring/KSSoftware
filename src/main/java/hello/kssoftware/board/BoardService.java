package hello.kssoftware.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board createBoard(Board board) {
        board.setCreateDate(now());
        board.setUpdateDate(now());

        //임시 (로그인 서비스로부터 작성자 추출해야함)
        if (board.getWriter() == null) {
            board.setWriter("testWriter");
        }

        return boardRepository.save(board);
    }

    public boolean updateBoard(Long boardId, Board updateParam) {
        log.info(updateParam.toString());
        Board findBoard = findById(boardId);

        findBoard.setTitle(updateParam.getTitle());
        findBoard.setContent(updateParam.getContent());
        findBoard.setUpdateDate(now());

        return boardRepository.update(boardId, findBoard);
    }

    public Board deleteBoard(Long boardId) {
        return boardRepository.delete(boardId);
    }

    @Transactional(readOnly = true)
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public List<Board> findAll(BoardSearch boardSearch) {
        return boardRepository.findAll(boardSearch);
    }

    private Date now() {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();

        return new java.sql.Date(currentMilliseconds);
    }
}
