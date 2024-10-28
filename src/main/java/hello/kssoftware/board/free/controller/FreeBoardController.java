package hello.kssoftware.board.free.controller;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.common.BoardController;
import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.free.entity.FreeBoard;
import hello.kssoftware.board.free.dto.FreeBoardDto;
import hello.kssoftware.board.common.BoardService;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/board/free")
public class FreeBoardController extends BoardController<FreeBoard, FreeBoardDto> {
    public FreeBoardController(BoardService<FreeBoard, FreeBoardDto> boardService, FlashNotifier flashNotifier) {
        super(boardService, flashNotifier);
    }

    @PostConstruct
    public void init() {
        this.dtoClass = BoardDto.Response.class;
    }

    @GetMapping
    public String getBoardList(@ModelAttribute("boardSearch") FreeBoardDto.Search search, Model model) {
        return super.getBoardList(search, model);
    }

    @GetMapping("/post")
    public String getBoardCreateForm(@ModelAttribute("boardCreateDto") FreeBoardDto.Create dto) {
        return super.getBoardCreateForm(dto);
    }

    @Override
    protected String getPath() {
        return "board/free";
    }

    @Override
    protected BoardDto.Response createResponse(Board board) {
        return new FreeBoardDto.Response((FreeBoard)board);
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member, @Valid FreeBoardDto.Create dto, RedirectAttributes redirectAttributes) throws IOException {
        return super.createBoard(member, dto, redirectAttributes);
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(FreeBoardDto.Update dto) throws IOException {
        return super.updateBoard(dto);
    }
}
