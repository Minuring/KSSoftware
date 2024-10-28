package hello.kssoftware;

import hello.kssoftware.board.interceptor.AuthorMatchCheckInterceptor;
import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.login.argumentresolver.LoginMemberArgumentResolver;
import hello.kssoftware.login.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FlashNotifier flashNotifier;
    private final BoardRepository boardRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(flashNotifier))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login/signIn","/login/signUp", "/css/**",
                        "/board","/*.ico", "/error","/*.png","/img/**", "/js/**");

        registry.addInterceptor(new AuthorMatchCheckInterceptor(boardRepository, flashNotifier))
                .order(2)
                .addPathPatterns("/board/**/edit", "/board/**/delete");
    }
}
