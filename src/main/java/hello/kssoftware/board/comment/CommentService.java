package hello.kssoftware.board.comment;

import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.common.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;

    public void createComment(CommentDto.Create dto) {
        Long boardId = dto.getBoardId();
        Board board = boardRepository.findById(boardId);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(dto.getWriter())
                .anonymousYn(dto.isAnonymousYn())
                .createDate(now())
                .updateDate(now())
                .build();

        board.addComment(comment);
    }

    public void updateComment(CommentDto.Update dto) {
        Long commentId = dto.getCommentId();
        Long boardId = dto.getBoardId();
        Board board = boardRepository.findById(boardId);
        Comment comment = board.getComment(commentId);

        comment.update(dto.getContent(), now());
    }

    public void deleteComment(Long boardId, Long commentId) {
        Board board = boardRepository.findById(boardId);
        Comment comment = board.getComment(commentId);

        board.deleteComment(comment);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
