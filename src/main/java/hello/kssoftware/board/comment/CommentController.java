package hello.kssoftware.board.comment;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * {@link hello.kssoftware.login.interceptor.LoginCheckInterceptor}에 의해 로그인이 되어있고,
 * 수정, 삭제 요청의 경우 {@link hello.kssoftware.board.interceptor.AuthorMatchCheckInterceptor}에 의해
 * 작성자와 로그인유저 검증을 마친 정상 흐름을 가정하고 처리
 */
@Controller
@RequestMapping({"/board/free", "/board/files"})
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final FlashNotifier flashNotifier;

    @PostMapping("/{id}/comment/new")
    public String createComment(@Login Member member,
                                @PathVariable(value = "id") Long boardId,
                                @ModelAttribute("commentCreateDto") CommentDto.Create dto,
                                HttpServletRequest request) {

        dto.setBoardId(boardId);
        dto.setWriter(member);
        commentService.createComment(dto);

        flashNotifier.notify("message.created.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/files/" + boardId;

        return "redirect:" + fullPath;
    }

    @PostMapping("/{id}/comment/edit")
    public String updateComment(@PathVariable("id") Long boardId,
                                @ModelAttribute("commentUpdateDto") CommentDto.Update dto,
                                HttpServletRequest request) {

        dto.setBoardId(boardId);
        commentService.updateComment(dto);

        flashNotifier.notify("message.updated.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/files/" + boardId;

        return "redirect:" + fullPath;
    }

    @PostMapping("/{id}/comment/delete")
    public String deleteComment(@PathVariable("id") Long boardId,
                                @RequestParam(name = "commentId") Long commentId,
                                HttpServletRequest request) {

        commentService.deleteComment(boardId, commentId);
        flashNotifier.notify("message.deleted.comment");

        String fullPath = request.getRequestURI().contains("/board/free") ?
                "/board/free/" + boardId : "/board/files/" + boardId;

        return "redirect:" + fullPath;
    }
}
