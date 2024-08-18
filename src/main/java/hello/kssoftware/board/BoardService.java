package hello.kssoftware.board;

import hello.kssoftware.board.Board;
import hello.kssoftware.board.BoardRepository;
import hello.kssoftware.board.dto.BoardCreateDto;
import hello.kssoftware.board.dto.BoardSearchDto;
import hello.kssoftware.board.dto.BoardUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Long createBoard(BoardCreateDto createDto) {
        Board board = new Board();
        board.setTitle(createDto.getTitle());
        board.setWriter(createDto.getWriter());
        board.setContent(createDto.getContent());
        board.setCreateDate(now());
        board.setUpdateDate(now());

        //임시 (로그인 서비스로부터 작성자 추출해야함)
        if (createDto.getWriter() == null) {
            board.setWriter("testWriter");
        }

        return boardRepository.save(board);
    }

    public void updateBoard(Long boardId, BoardUpdateDto updateDto) {
        Board findBoard = findById(boardId);

        findBoard.setTitle(updateDto.getTitle());
        findBoard.setContent(updateDto.getContent());
        findBoard.setUpdateDate(now());
    }

    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
    }

    @Transactional(readOnly = true)
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public List<Board> findAll(BoardSearchDto boardSearchDto) {
        return boardRepository.findAll(boardSearchDto);
    }

    private Date now() {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();

        return new java.sql.Date(currentMilliseconds);
    }
}
