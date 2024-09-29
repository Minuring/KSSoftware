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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * {@link hello.kssoftware.login.interceptor.LoginCheckInterceptor}에 의해 로그인이 되어있고,
 * 수정, 삭제 요청의 경우 {@link hello.kssoftware.board.interceptor.AuthorMatchCheckInterceptor}에 의해
 * 작성자와 로그인유저 검증을 마친 정상 흐름을 가정하고 처리
 */
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final FlashNotifier flashNotifier;

    @GetMapping
    public String getBoardList(@ModelAttribute("boardSearch") BoardDto.Search search, Model model) {
        List<BoardDto.Response> boards = boardService.findAll(search)
                .stream()
                .map(BoardDto.Response::new)
                .toList();

        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/{id}")
    public String getBoardDetails(@PathVariable(value = "id") long id, Model model) {

        Board originBoard = boardService.findById(id);
        BoardDto.Response board = new BoardDto.Response(originBoard);

        List<Comment> originComments = board.getComments();
        List<CommentDto.Response> comments = originComments.stream().map(CommentDto.Response::new).toList();

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCreateDto", new CommentDto.Create());
        model.addAttribute("commentUpdateDto", new CommentDto.Update());

        return "board/board";
    }

    @GetMapping("/post")
    public String getBoardCreateForm(@ModelAttribute("boardCreateDto") BoardDto.Create dto) {

        return "board/post";
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member,
                              @Valid BoardDto.Create dto,
                              RedirectAttributes redirectAttributes) {

        dto.setWriter(member);
        Long id = boardService.createBoard(dto);
        flashNotifier.notify("message.created.board");

        redirectAttributes.addAttribute("id", id);
        return "redirect:/board/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getBoardUpdateForm(@PathVariable(value = "id") Long id, Model model) {

        Board originBoard = boardService.findById(id);
        BoardDto.Response board = new BoardDto.Response(originBoard);

        model.addAttribute("board", board);
        return "board/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(BoardDto.Update dto) {

        boardService.updateBoard(dto);
        flashNotifier.notify("message.updated.board");

        return "redirect:/board/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable(value = "id") Long id) {

        boardService.deleteBoard(id);

        flashNotifier.notify("message.deleted.board");
        return "redirect:/board";
    }

}
