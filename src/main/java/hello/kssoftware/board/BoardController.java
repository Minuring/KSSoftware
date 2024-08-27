package hello.kssoftware.board;

import hello.kssoftware.board.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
        model.addAttribute("boardSearchDto", new BoardSearchDto());
        return "board/boards";
    }

    @GetMapping("/{id}")
    public String board(@PathVariable(value = "id") long id, Model model,
                        @ModelAttribute CommentCreateDto commentCreateDto,
                        @ModelAttribute CommentUpdateDto commentUpdateDto) {
        Board findBoard = boardService.findById(id);
        List<Comment> comments = findBoard.getComments();
        model.addAttribute("board", findBoard);
        model.addAttribute("commentCreateDto", commentCreateDto);
        model.addAttribute("commentUpdateDto", commentUpdateDto);
        model.addAttribute("comments", comments);

        return "board/board";
    }

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("boardCreateDto", new BoardCreateDto());
        return "board/post";
    }

    @PostMapping("/post")
    public String createBoard(@Valid BoardCreateDto createDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "board/post";
        }

        Long boardId = boardService.createBoard(createDto);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable(value = "id") long id, Model model) {
        Board findBoard = boardService.findById(id);
        model.addAttribute("board", findBoard);
        return "board/edit";
    }

    @PostMapping("/{id}/edit")
    public String editBoard(@PathVariable(value = "id") Long id, BoardUpdateDto updateDto) {
        boardService.updateBoard(id, updateDto);
        return "redirect:/board/{id}";
    }

    @RequestMapping("/{id}/delete")
    public String deleteBoard(@PathVariable(value = "id") Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }

    @PostMapping("/{id}/comment/new")
    public String createComment(@PathVariable(value = "id") Long boardId,
                                CommentCreateDto commentCreateDto,
                                RedirectAttributes redirectAttributes) {
        boardService.createComment(boardId, commentCreateDto);
        redirectAttributes.addAttribute("redirectStatus", "commentCreated");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/edit")
    public String editComment(@PathVariable("id") Long boardId,
                              CommentUpdateDto updateDto,
                              RedirectAttributes redirectAttributes) {
        boardService.updateComment(boardId, updateDto);
        redirectAttributes.addAttribute("redirectStatus", "commentEdited");
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/comment/delete")
    public String editComment(@PathVariable("id") Long boardId,
                              @RequestParam(name = "commentId") Long commentId,
                              RedirectAttributes redirectAttributes) {
        boardService.deleteComment(boardId, commentId);
        redirectAttributes.addAttribute("redirectStatus", "commentDeleted");
        return "redirect:/board/{id}";
    }
}
