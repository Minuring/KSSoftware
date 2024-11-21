package hello.kssoftware.board.file.controller;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.board.comment.CommentService;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.common.BoardSearch;
import hello.kssoftware.board.file.dto.FileBoardCreate;
import hello.kssoftware.board.file.dto.FileBoardResponse;
import hello.kssoftware.board.file.dto.FileBoardUpdate;
import hello.kssoftware.board.file.entity.FileBoard;
import hello.kssoftware.board.file.service.FileStore;
import hello.kssoftware.board.file.service.FileBoardService;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static hello.kssoftware.board.common.BoardControllerSupports.addCommentsToModel;

@Controller
@RequestMapping("/board/file")
@RequiredArgsConstructor
public class FileBoardController {

    private static final String URL_PATH = "board/file";

    private final FileBoardService boardService;
    private final FileStore fileStore;
    private final FlashNotifier flashNotifier;
    private final CommentService commentService;

    @GetMapping
    public String getBoardList(@ModelAttribute BoardSearch search, Model model) {
        List<FileBoardResponse> boards = boardService.findAll(search)
                .stream()
                .map(FileBoardResponse::from)
                .toList();

        model.addAttribute("boards", boards);

        return URL_PATH + "/boards";
    }

    @GetMapping("/{id}")
    public String getBoardDetails(@PathVariable(value = "id") long id, Model model) throws IOException {
        FileBoard board = boardService.findById(id);
        model.addAttribute("board", FileBoardResponse.from(board));

        List<Comment> comments = commentService.findAll(board);
        List<CommentResponse> responses = comments.stream().map(CommentResponse::from).toList();
        addCommentsToModel(model, responses);

        return URL_PATH + "/board";
    }

    @GetMapping("/post")
    public String getBoardCreateForm(Model model) {
        model.addAttribute("boardCreateDto", new FileBoardCreate());
        return URL_PATH + "/post";
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member, @Valid FileBoardCreate dto, RedirectAttributes redirectAttributes) throws IOException {
        Long id = boardService.createBoard(member, dto);
        flashNotifier.notify("message.created.board");

        redirectAttributes.addAttribute("id", id);

        return "redirect:/" + URL_PATH + "/{id}";
    }

    @GetMapping("/{id}/edit")
    public String getBoardUpdateForm(@PathVariable(value = "id") Long id, Model model) throws IOException {
        FileBoard board = boardService.findById(id);
        model.addAttribute("board", FileBoardResponse.from(board));

        return URL_PATH + "/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(FileBoardUpdate dto) throws IOException {
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

    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long boardId) throws MalformedURLException {
        FileBoard board = boardService.findById(boardId);

        String storeFileName = board.getFile().getStoreFileName();
        String uploadFileName = board.getFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
