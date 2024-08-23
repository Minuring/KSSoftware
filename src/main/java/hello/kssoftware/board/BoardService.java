package hello.kssoftware.board;

import hello.kssoftware.board.dto.*;
import hello.kssoftware.board.repository.BoardRepository;
import hello.kssoftware.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

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

    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public Comment createComment(Long boardId, CommentCreateDto createDto) {
        Board board = boardRepository.findById(boardId);

        //임시 (로그인 서비스로부터 작성자 추출해야함)
        if (createDto.getWriter() == null) {
            createDto.setWriter("testWriter");
        }
        Comment comment = Comment.createComment(board, createDto, now());

        return commentRepository.save(comment);
    }

    public void updateComment(Long boardId, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(updateDto.getCommentId()).get();

        comment.update(updateDto.getContent(), now());
    }

    public void deleteComment(Long boardId, Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
