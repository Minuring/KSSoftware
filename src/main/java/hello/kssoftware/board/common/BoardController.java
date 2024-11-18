package hello.kssoftware.board.common;

import hello.kssoftware.board.comment.CommentDto;
import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.login.Member;
import hello.kssoftware.FlashNotifier;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

/**
 * {@link hello.kssoftware.login.interceptor.LoginCheckInterceptor}에 의해 로그인이 되어있고,
 * 수정, 삭제 요청의 경우 {@link hello.kssoftware.board.interceptor.AuthorMatchCheckInterceptor}에 의해
 * 작성자와 로그인유저 검증을 마친 정상 흐름을 가정하고 처리
 */

@RequiredArgsConstructor
@Slf4j
public abstract class BoardController<T extends Board, Dto extends BoardDto> {

    private final BoardService<T, Dto> boardService;
    private final FlashNotifier flashNotifier;

    @Setter
    protected Class<? extends BoardDto.Response> dtoClass;

    protected abstract String getPath();

    protected abstract Dto.Response createResponse(Board board) throws IOException;

    public String getBoardList(@ModelAttribute("boardSearch") Dto.Search search, Model model) {
        List<Dto.Response> boards = boardService.findAll(search)
                .stream()
                .map(Dto.Response::new)
                .toList();

        model.addAttribute("boards", boards);

        return getPath() + "/boards";
    }

    @GetMapping("/{id}")
    public String getBoardDetails(@PathVariable(value = "id") long id, Model model) throws IOException {
        T originBoard = boardService.findById(id);
        Dto.Response board = createResponse(originBoard);

        List<Comment> originComments = board.getComments();
        List<CommentDto.Response> comments = originComments.stream().map(CommentDto.Response::new).toList();

        model.addAttribute("board", dtoClass.cast(board));
        model.addAttribute("comments", comments);
        model.addAttribute("commentCreateDto", new CommentDto.Create());
        model.addAttribute("commentUpdateDto", new CommentDto.Update());

        return getPath()+"/board";
    }

    public String getBoardCreateForm(Dto.Create dto) {
        return getPath() + "/post";
    }

    public String createBoard(Member member, BoardDto.Create dto, RedirectAttributes redirectAttributes) throws IOException {
        dto.setWriter(member);
        Long id = boardService.createBoard(dto);
        flashNotifier.notify("message.created.board");

        redirectAttributes.addAttribute("id", id);

        return "redirect:/" + getPath() + "/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getBoardUpdateForm(@PathVariable(value = "id") Long id, Model model) throws IOException {
        T originBoard = boardService.findById(id);
        Dto.Response board = createResponse(originBoard);

        model.addAttribute("board", dtoClass.cast(board));

        return  getPath() + "/edit";
    }

    public String updateBoard(BoardDto.Update dto) throws IOException {
        boardService.updateBoard(dto);
        flashNotifier.notify("message.updated.board");

        return "redirect:/" + getPath() + "/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable(value = "id") Long id) {
        boardService.deleteBoard(id);
        flashNotifier.notify("message.deleted.board");

        return "redirect:/" + getPath();
    }
}
