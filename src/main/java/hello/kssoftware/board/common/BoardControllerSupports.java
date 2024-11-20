package hello.kssoftware.board.common;

import hello.kssoftware.board.comment.dto.CommentCreate;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.comment.dto.CommentUpdate;
import org.springframework.ui.Model;

import java.util.List;

public class BoardControllerSupports {

    public static void addCommentsToModel(Model model, List<CommentResponse> comments) {
        model.addAttribute("comments", comments);
        model.addAttribute("commentCreateDto", new CommentCreate());
        model.addAttribute("commentUpdateDto", new CommentUpdate());
    }
}
