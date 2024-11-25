package hello.kssoftware.login.interceptor;

import hello.kssoftware.FlashNotifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final FlashNotifier flashNotifier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            flashNotifier.notify("required_login");
            sendRedirect(request, response);
            return false;
        }

        return true;
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String httpMethod = request.getMethod();

        if ("GET".equals(httpMethod)) {
            String requestURI = request.getRequestURI();
            response.sendRedirect("/login/signIn?redirectURI=" + requestURI);
            return;
        }
        response.sendRedirect("/login/signIn");
    }
}