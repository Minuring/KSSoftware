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

@RequiredArgsConstructor
@Component
public class AuthorMatchCheckInterceptor implements HandlerInterceptor {
    private final BoardRepository boardRepository;
    private final FlashNotifier flashNotifier;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        Member loginUser = (Member) request.getSession(false).getAttribute("loginUser");
        if (loginUser == null) return false;

        String requestURI = request.getRequestURI();
        Long boardId = getIdFromPathVariable(request);
        Board board = boardRepository.findById(boardId);

        boolean matches;
        if (requestURI.contains("comment")) {
            matches = doCompareWithCommentAuthor(request, board, loginUser);
        } else {
            matches = doCompareWithBoardAuthor(board, loginUser);
        }

        if (!matches) {
            flashNotifier.notify("not_author");
            response.sendRedirect("/board/" + boardId);
        }

        return matches;
    }

    private static boolean doCompareWithBoardAuthor(final Board board, final Member loginUser) {
        return board.getWriter().equals(loginUser);
    }

    private static boolean doCompareWithCommentAuthor(final HttpServletRequest request, final Board board, final Member loginUser) {
        Long commentId = Long.valueOf(request.getParameter("commentId"));
        Comment comment = board.getComment(commentId);
        return comment.getWriter().equals(loginUser);
    }

    private static Long getIdFromPathVariable(final HttpServletRequest request) {
        Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Map<String, String> map = (Map<String, String>) attribute;
        return Long.valueOf(map.get("id"));
    }
}
