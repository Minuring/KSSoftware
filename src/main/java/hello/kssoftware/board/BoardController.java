package hello.kssoftware.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public List<Board> getAllBoards() {
        return boardService.getAllBoard();
    }

    @PostMapping("/board")
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }
}
