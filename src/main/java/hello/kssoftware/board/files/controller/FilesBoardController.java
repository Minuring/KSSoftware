package hello.kssoftware.board.files.controller;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.common.BoardController;
import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.files.service.FileStore;
import hello.kssoftware.board.files.entity.FilesBoard;
import hello.kssoftware.board.files.dto.FilesBoardDto;
import hello.kssoftware.board.files.service.FilesBoardService;
import hello.kssoftware.board.common.BoardService;
import hello.kssoftware.login.Member;
import hello.kssoftware.login.argumentresolver.Login;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/board/files")
public class FilesBoardController extends BoardController<FilesBoard, FilesBoardDto> {

    private final FilesBoardService filesBoardService;
    private final FileStore fileStore;

    public FilesBoardController(BoardService<FilesBoard, FilesBoardDto> boardService, FlashNotifier flashNotifier, FilesBoardService filesBoardService, FileStore fileStore) {
        super(boardService, flashNotifier);
        this.filesBoardService = filesBoardService;
        this.fileStore = fileStore;
    }

    @GetMapping("/post")
    public String getBoardCreateForm(@ModelAttribute("boardCreateDto") FilesBoardDto.Create dto) {
        return super.getBoardCreateForm(dto);
    }

    @PostConstruct
    public void init() {
        this.dtoClass = FilesBoardDto.Response.class;
    }

    @Override
    protected String getPath() {
        return "board/files";
    }

    @Override
    protected BoardDto.Response createResponse(Board board) throws IOException {
        return new FilesBoardDto.Response((FilesBoard)board);
    }

    @GetMapping
    public String getBoardList(@ModelAttribute("boardSearch") FilesBoardDto.Search search, Model model) {
        return super.getBoardList(search, model);
    }

    @PostMapping("/post")
    public String createBoard(@Login Member member, @Valid FilesBoardDto.Create dto,
                              RedirectAttributes redirectAttributes) throws IOException {
        return super.createBoard(member, dto, redirectAttributes);
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(FilesBoardDto.Update dto) throws IOException {
        return super.updateBoard(dto);
    }

    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long boardId) throws MalformedURLException {
        FilesBoard board = filesBoardService.findById(boardId);
        String storeFileName = board.getFile().getStoreFileName();
        String uploadFileName = board.getFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        //한글 깨지는거 방지위한 인코딩
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        //다운로드를 위한
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)    //다운로드를 위함
                .body(resource);
    }
}
