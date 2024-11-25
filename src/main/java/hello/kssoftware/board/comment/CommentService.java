package hello.kssoftware.board.comment;

import hello.kssoftware.board.comment.dto.CommentCreate;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.comment.dto.CommentUpdate;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.login.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hello.kssoftware.board.common.TimeUtils.now;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;

    public void createComment(Member member, Long boardId, CommentCreate dto) {
        Board board = boardRepository.findById(boardId);
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(member)
                .anonymousYn(dto.isAnonymousYn())
                .createDate(now())
                .updateDate(now())
                .build();

        board.addComment(comment);
    }

    public void updateComment(Long boardId, CommentUpdate dto) {
        Board board = boardRepository.findById(boardId);
        Comment comment = board.getComment(dto.getCommentId());

        comment.update(dto.getContent(), now());
    }

    public void deleteComment(Long boardId, Long commentId) {
        Board board = boardRepository.findById(boardId);
        Comment comment = board.getComment(commentId);

        board.deleteComment(comment);
    }

    public List<CommentResponse> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId);
        return board.getComments()
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
