package hello.kssoftware.board;

import hello.kssoftware.board.dto.*;
import hello.kssoftware.login.Member;
import hello.kssoftware.FlashNotifier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final FlashNotifier flashNotifier;

    @GetMapping
    public String boards(@ModelAttribute BoardSearchDto boardSearchDto, Model model) {
        List<Board> boards = boardService.findAll(boardSearchDto);
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/{id}")
    public String board(@PathVariable(value = "id") long id, Model model,
                        @ModelAttribute CommentCreateDto commentCreateDto,
                        @ModelAttribute CommentUpdateDto commentUpdateDto) {
        Board findBoard = boardService.findById(id);
        List<Comment> comments = findBoard.getComments();
        model.addAttribute("board", findBoard);
        model.addAttribute("comments", comments);

        return "board/board";
    }

    @GetMapping("/post")
    public String postForm(@SessionAttribute(name = "loginUser", required = false) Member member,
                           @ModelAttribute BoardCreateDto boardCreateDto) {

        if (member == null) {
            flashNotifier.notify("required_login");
            return "redirect:/login/signIn";
        }

        return "board/post";
    }

    @PostMapping("/post")
    public String createBoard(@SessionAttribute(name = "loginUser") Member member,
                              @Valid BoardCreateDto createDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "board/post";
        }

        createDto.setWriter(member);
        Long boardId = boardService.createBoard(createDto);
        redirectAttributes.addAttribute("boardId", boardId);
        flashNotifier.notify("message.created.board");
        flashNotifier.notify("message.updated.board");

        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@SessionAttribute(name = "loginUser", required = false) Member member,
                           @PathVariable(value = "id") long id, Model model) {
        if (member == null) {
            flashNotifier.notify("required_login");
            return "redirect:/login/signIn";
        }

        Board findBoard = boardService.findById(id);
        if (!findBoard.getWriter().equals(member)) {
            flashNotifier.notify("not_author");
            return "redirect:/board/{id}";
        }

        model.addAttribute("board", findBoard);
        return "board/edit";
    }

    @PostMapping("/{id}/edit")
    public String editBoard(@SessionAttribute(name = "loginUser") Member member,
                            @PathVariable(value = "id") Long id, BoardUpdateDto updateDto) {
        Board board = boardService.findById(id);
        if (!board.getWriter().equals(member)) {
            flashNotifier.notify("not_author");
            return "redirect:/board/{id}";
        }

        boardService.updateBoard(id, updateDto);
        flashNotifier.notify("message.updated.board");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@SessionAttribute(name = "loginUser") Member member,
                              @PathVariable(value = "id") Long id) {
        Board board = boardService.findById(id);
        if (!board.getWriter().equals(member)) {
            flashNotifier.notify("not_author");
            return "redirect:/board/{id}";
        }

        boardService.deleteBoard(id);
        flashNotifier.notify("message.deleted.board");
        return "redirect:/board";
    }

    @PostMapping("/{id}/comment/new")
    public String createComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                                @PathVariable(value = "id") Long boardId,
                                CommentCreateDto commentCreateDto) {
        if (member == null) {
            flashNotifier.notify("required_login");
            return "redirect:/login/signIn";
        }

        commentCreateDto.setWriter(member);
        boardService.createComment(boardId, commentCreateDto);
        flashNotifier.notify("message.created.comment");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/edit")
    public String editComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                              @PathVariable("id") Long boardId,
                              @ModelAttribute CommentUpdateDto updateDto) {
        if (member == null) {
            flashNotifier.notify("required_login");
            return "redirect:/login/signIn";
        }

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(updateDto.getCommentId());
        if (comment.getWriter().equals(member)) {
            boardService.updateComment(boardId, updateDto);
            flashNotifier.notify("message.updated.comment");
        } else {
            flashNotifier.notify("not_author");
        }

        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/delete")
    public String editComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                              @PathVariable("id") Long boardId,
                              @RequestParam(name = "commentId") Long commentId) {
        if (member == null) {
            flashNotifier.notify("required_login");
            return "redirect:/login/signIn";
        }

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(commentId);
        if (comment.getWriter().equals(member)) {
            boardService.deleteComment(boardId, commentId);
            flashNotifier.notify("message.deleted.comment");
        } else {
            flashNotifier.notify("not_author");
        }

        return "redirect:/board/{id}";
    }
}
