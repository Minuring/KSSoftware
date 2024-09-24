package hello.kssoftware.board.controller;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.entity.Comment;
import hello.kssoftware.board.dto.CommentCreateDto;
import hello.kssoftware.board.dto.CommentUpdateDto;
import hello.kssoftware.board.service.BoardService;
import hello.kssoftware.board.service.CommentService;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final FlashNotifier flashNotifier;
    private final BoardService boardService;

    @PostMapping("/{id}/comment/new")
    public String createComment(@Login Member member,
                                @PathVariable(value = "id") Long boardId,
                                @ModelAttribute CommentCreateDto createDto) {

        createDto.setBoardId(boardId);
        createDto.setWriter(member);

        commentService.createComment(createDto);

        flashNotifier.notify("message.created.comment");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/edit")
    public String updateComment(@Login Member member,
                                @PathVariable("id") Long boardId,
                                @ModelAttribute CommentUpdateDto updateDto) {

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(updateDto.getCommentId());
        updateDto.setBoardId(boardId);

        if (member.equals(comment.getWriter())) {
            commentService.updateComment(updateDto);
            flashNotifier.notify("message.updated.comment");

        } else {
            flashNotifier.notify("not_author");

        }
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/delete")
    public String deleteComment(@Login Member member,
                                @PathVariable("id") Long boardId,
                                @RequestParam(name = "commentId") Long commentId) {

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(commentId);

        if (comment.getWriter().equals(member)) {
            commentService.deleteComment(boardId, commentId);
            flashNotifier.notify("message.deleted.comment");

        } else {
            flashNotifier.notify("not_author");

        }
        return "redirect:/board/{id}";
    }
}
