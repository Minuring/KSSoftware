package hello.kssoftware.board.interceptor;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.Comment;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.login.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthorMatchCheckInterceptor implements HandlerInterceptor {
    private final BoardRepository boardRepository;
    private final FlashNotifier flashNotifier;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        Member loginUser = Optional.ofNullable(request.getSession(false))
                .map(session -> (Member) session.getAttribute("loginUser"))
                .orElse(null);
        Long boardId = getIdFromPathVariable(request);
        Board board = boardRepository.findById(boardId);

        if (loginUser == null) {
            flashNotifier.notify("required_login");
            response.sendRedirect("/login/signIn");
            return false;
        }

        boolean matches;
        if (request.getRequestURI().contains("comment")) {
            matches = doCompareWithCommentAuthor(request, board, loginUser);
        } else {
            matches = board.isWrittenBy(loginUser);
        }

        if (matches) {
            return true;
        }

        flashNotifier.notify("not_author");
        response.sendRedirect("/board/" + board.getType() + "/" + boardId);
        return false;
    }

    private static boolean doCompareWithCommentAuthor(final HttpServletRequest request, final Board board, final Member loginUser) {
        Long commentId = Long.valueOf(request.getParameter("commentId"));
        Comment comment = board.getComment(commentId);
        return comment.isWrittenBy(loginUser);
    }

    private static Long getIdFromPathVariable(final HttpServletRequest request) {
        Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Map<String, String> map = (Map<String, String>) attribute;
        return Long.valueOf(map.get("id"));
    }
}
