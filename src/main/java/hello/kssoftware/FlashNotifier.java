package hello.kssoftware;

import jakarta.annotation.Nullable;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;

/**
 * {@link RequestContextUtils}를 통해 현재 request의 {@code flashMap}에 접근하여
 * {@link FlashMap}의 {@code "messages"} 속성으로 메시지들을 전달하는 클래스
 *
 * @see  #notify(String, Object...) notify(String, String...)
 */
@Component
@RequestScope
public class FlashNotifier {

    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FlashMap flashMap;

    private final ArrayList<String> messages = new ArrayList<>();
    private static final String DEFAULT_MESSAGE = "[Default Message] Message Code Not Found";
    private boolean isFirst = true;

    public FlashNotifier(MessageSource messageSource, HttpServletRequest request, HttpServletResponse response) {
        this.messageSource = messageSource;
        this.request = request;
        this.response = response;
        this.flashMap = RequestContextUtils.getOutputFlashMap(request);
    }

    /**
     * {@code flashMap}의 Key {@code "messages"}에 파라미터로 전달 된 메시지코드를
     * {@link MessageSource}를 통해 파싱하여 리스트로 저장합니다.
     *
     * @param messageCode {@link MessageSource}를 통해 파싱될 메시지 코드
     * @param args <b>(Nullable)</b> messageCode의 args
     */
    public void notify(String messageCode, @Nullable Object... args) {
        if (isFirst) {
            flashMap.put("messages", messages);
            isFirst = false;
        }

        String message = messageSource.getMessage(messageCode, args, DEFAULT_MESSAGE, request.getLocale());
        messages.add(message);
    }

    /**
     * {@code this.flashMap}과 {@code flashMapManager.saveOutputFlashMap}의 flashMap은 같은 Request 컨텍스트이므로
     * {@code saveOutputFlashMap}를 호출할 때 {@link RedirectAttributes}의 {@code FlashAttribute}와 같은 다른 속성이 손실되지 않음
     */
    @PreDestroy
    private void destroy() {
        FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
        if (flashMapManager != null) {
            flashMapManager.saveOutputFlashMap(this.flashMap, request, response);
        }
    }
}
