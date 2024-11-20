package hello.kssoftware.board.comment;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.dto.CommentCreate;
import hello.kssoftware.board.comment.dto.CommentUpdate;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * {@link hello.kssoftware.login.interceptor.LoginCheckInterceptor}에 의해 로그인이 되어있고,
 * 수정, 삭제 요청의 경우 {@link hello.kssoftware.board.interceptor.AuthorMatchCheckInterceptor}에 의해
 * 작성자와 로그인유저 검증을 마친 정상 흐름을 가정하고 처리
 */
@Controller
@RequestMapping({"/board/free", "/board/file"})
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final FlashNotifier flashNotifier;

    @PostMapping("/{id}/comment/new")
    public String createComment(@Login Member member,
                                @PathVariable(value = "id") Long boardId,
                                @ModelAttribute("commentCreateDto") CommentCreate dto,
                                HttpServletRequest request) {
        commentService.createComment(member, boardId, dto);

        flashNotifier.notify("message.created.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/file/" + boardId;

        return "redirect:" + fullPath;
    }

    @PostMapping("/{id}/comment/edit")
    public String updateComment(@PathVariable("id") Long boardId,
                                @ModelAttribute("commentUpdateDto") CommentUpdate dto,
                                HttpServletRequest request) {

        commentService.updateComment(boardId, dto);

        flashNotifier.notify("message.updated.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/file/" + boardId;

        return "redirect:" + fullPath;
    }

    @PostMapping("/{id}/comment/delete")
    public String deleteComment(@PathVariable("id") Long boardId,
                                @RequestParam(name = "commentId") Long commentId,
                                HttpServletRequest request) {
        commentService.deleteComment(boardId, commentId);

        flashNotifier.notify("message.deleted.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/file/" + boardId;

        return "redirect:" + fullPath;
    }
}
