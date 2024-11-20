package hello.kssoftware.board.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
