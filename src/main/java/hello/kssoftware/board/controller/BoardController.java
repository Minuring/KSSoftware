package hello.kssoftware.board.controller;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.service.BoardService;
import hello.kssoftware.board.entity.Comment;
import hello.kssoftware.board.dto.*;
import hello.kssoftware.login.Member;
import hello.kssoftware.FlashNotifier;
import hello.kssoftware.login.argumentresolver.Login;
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
    public String getBoardList(@ModelAttribute BoardSearchDto boardSearchDto, Model model) {
        List<Board> boards = boardService.findAll(boardSearchDto);
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/{id}")
    public String getBoardDetails(@PathVariable(value = "id") long id, Model model,
                                  @ModelAttribute CommentCreateDto commentCreateDto,
                                  @ModelAttribute CommentUpdateDto commentUpdateDto) {

        Board board = boardService.findById(id);
        List<Comment> comments = board.getComments();

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        return "board/board";
    }

    @GetMapping("/post")
    public String getBoardCreateForm(@Login Member member,
                                     @ModelAttribute BoardCreateDto boardCreateDto) {

        return "board/post";
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member,
                              @Valid BoardCreateDto createDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "board/post";
        }

        createDto.setWriter(member);
        Long id = boardService.createBoard(createDto);
        flashNotifier.notify("message.created.board");

        redirectAttributes.addAttribute("id", id);
        return "redirect:/board/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getBoardUpdateForm(@Login Member member,
                                     @PathVariable(value = "id") Long id, Model model) {

        Board board = boardService.findById(id);

        if (member.equals(board.getWriter())) {
            model.addAttribute("board", board);
            return "board/edit";

        } else {
            flashNotifier.notify("not_author");
            return "redirect:/board/{id}";

        }
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(@Login Member member,
                              @PathVariable(value = "id") Long id, BoardUpdateDto updateDto) {

        Board board = boardService.findById(id);
        if (member.equals(board.getWriter())) {
            boardService.updateBoard(id, updateDto);
            flashNotifier.notify("message.updated.board");

        } else {
            flashNotifier.notify("not_author");

        }
        
        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@Login Member member,
                              @PathVariable(value = "id") Long id) {

        Board board = boardService.findById(id);
        if (member.equals(board.getWriter())) {
            boardService.deleteBoard(id);
            flashNotifier.notify("message.deleted.board");
            return "redirect:/board";

        } else {
            flashNotifier.notify("not_author");
            return "redirect:/board/{id}";

        }

    }
}
