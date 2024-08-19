package hello.kssoftware.board;

import hello.kssoftware.board.dto.BoardCreateDto;
import hello.kssoftware.board.dto.BoardSearchDto;
import hello.kssoftware.board.dto.BoardUpdateDto;
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
    public String board(@PathVariable(value = "id") long id, Model model) {
        Board findBoard = boardService.findById(id);
        model.addAttribute("board", findBoard);
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
}
