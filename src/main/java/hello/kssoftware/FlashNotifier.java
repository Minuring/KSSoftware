package hello.kssoftware;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;

@Component
@RequestScope
public class FlashNotifier {

    private final MessageSource messageSource;
    private final HttpServletRequest request;
    private final FlashMap flashMap;
    private final ArrayList<String> messages = new ArrayList<>();
    private static final String DEFAULT_MESSAGE = "[Default Message] Message Code Not Found";
    private boolean isFirst = true;

    public FlashNotifier(MessageSource messageSource, HttpServletRequest request) {
        this.messageSource = messageSource;
        this.request = request;
        this.flashMap = RequestContextUtils.getOutputFlashMap(request);
    }

    public void notify(String messageCode, String... args) {
        if (isFirst) {
            flashMap.put("messages", messages);
            isFirst = false;
        }

        String message = messageSource.getMessage(messageCode, args, DEFAULT_MESSAGE, request.getLocale());
        messages.add(message);
    }

}
