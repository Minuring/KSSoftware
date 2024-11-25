package hello.kssoftware.board.free.controller;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.CommentService;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.common.BoardSearch;
import hello.kssoftware.board.free.dto.FreeBoardCreate;
import hello.kssoftware.board.free.dto.FreeBoardResponse;
import hello.kssoftware.board.free.dto.FreeBoardUpdate;
import hello.kssoftware.board.free.service.FreeBoardService;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static hello.kssoftware.board.common.BoardControllerSupports.addCommentsToModel;

@Controller
@RequestMapping("/board/free")
@RequiredArgsConstructor
public class FreeBoardController {

    private static final String URL_PATH = "board/free";

    private final FreeBoardService boardService;
    private final CommentService commentService;
    private final FlashNotifier flashNotifier;

    @GetMapping
    public String getBoardList(@ModelAttribute BoardSearch search, Model model) {
        model.addAttribute("boards", boardService.findAll(search));
        return URL_PATH + "/boardList";
    }

    @GetMapping("/{id}")
    public String getBoardDetails(@PathVariable(value = "id") long id, Model model) {
        FreeBoardResponse board = boardService.findById(id);
        List<CommentResponse> comments = commentService.findAll(board.getId());

        model.addAttribute("board", board);
        addCommentsToModel(model, comments);
        return URL_PATH + "/board";
    }

    @GetMapping("/post")
    public String getBoardCreateForm(Model model) {
        model.addAttribute("boardCreateDto", new FreeBoardCreate());
        return URL_PATH + "/post";
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member, FreeBoardCreate dto, RedirectAttributes redirectAttributes) {
        Long id = boardService.createBoard(member, dto);

        flashNotifier.notify("message.created.board");
        redirectAttributes.addAttribute("id", id);
        return "redirect:/" + URL_PATH + "/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getBoardUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return URL_PATH + "/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(FreeBoardUpdate dto) {
        boardService.updateBoard(dto);

        flashNotifier.notify("message.updated.board");
        return "redirect:/" + URL_PATH + "/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable(value = "id") Long id) {
        boardService.deleteBoard(id);

        flashNotifier.notify("message.deleted.board");
        return "redirect:/" + URL_PATH;
    }
}
