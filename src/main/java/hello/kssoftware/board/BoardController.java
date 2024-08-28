package hello.kssoftware.board;

import hello.kssoftware.board.dto.*;
import hello.kssoftware.login.Member;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

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
            return "redirect:/login/signin";
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
        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@SessionAttribute(name = "loginUser", required = false) Member member,
                           @PathVariable(value = "id") long id, Model model) {
        if (member == null) {
            return "redirect:/login/signin";
        }

        Board findBoard = boardService.findById(id);
        if (!findBoard.getWriter().equals(member)) {
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
            return "redirect:/board/{id}";
        }

        boardService.updateBoard(id, updateDto);
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@SessionAttribute(name = "loginUser") Member member,
                              @PathVariable(value = "id") Long id) {
        Board board = boardService.findById(id);
        if (!board.getWriter().equals(member)) {
            return "redirect:/board/{id}";
        }
        boardService.deleteBoard(id);
        return "redirect:/board";
    }

    @PostMapping("/{id}/comment/new")
    public String createComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                                @PathVariable(value = "id") Long boardId,
                                CommentCreateDto commentCreateDto,
                                RedirectAttributes redirectAttributes) {
        if (member == null) {
            return "redirect:/login/signin";
        }

        commentCreateDto.setWriter(member);
        boardService.createComment(boardId, commentCreateDto);
        redirectAttributes.addAttribute("redirectStatus", "commentCreated");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/edit")
    public String editComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                              @PathVariable("id") Long boardId,
                              @ModelAttribute CommentUpdateDto updateDto,
                              RedirectAttributes redirectAttributes) {
        if (member == null) {
            return "redirect:/login/signin";
        }

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(updateDto.getCommentId());
        System.out.println("member = " + member + ", boardId = " + boardId + ", updateDto = " + updateDto);
        if (comment.getWriter().equals(member)) {
            boardService.updateComment(boardId, updateDto);
            redirectAttributes.addAttribute("redirectStatus", "commentEdited");
        }

        //작성자 아닐 때 예외처리
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/delete")
    public String editComment(@SessionAttribute(name = "loginUser", required = false) Member member,
                              @PathVariable("id") Long boardId,
                              @RequestParam(name = "commentId") Long commentId,
                              RedirectAttributes redirectAttributes) {
        if (member == null) {
            return "redirect:/login/signin";
        }

        Board board = boardService.findById(boardId);
        Comment comment = board.getComment(commentId);
        if (comment.getWriter().equals(member)) {
            boardService.deleteComment(boardId, commentId);
            redirectAttributes.addAttribute("redirectStatus", "commentDeleted");
        }

        //작성자 아닐 때 예외처리
        return "redirect:/board/{id}";
    }
}
