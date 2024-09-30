package hello.kssoftware.board.files;

import hello.kssoftware.board.entity.Board;
import hello.kssoftware.login.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;

@Getter
@Entity
@DiscriminatorValue("Files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilesBoard extends Board {
    protected File file;

    @Builder
    public FilesBoard(Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, File file) {
        super(writer, title, content, createDate, updateDate);
        this.file = file;
    }

    public void update(String title, String content, LocalDateTime updateDate, File file) {
        super.title = title;
        super.content = content;
        super.updateDate = updateDate;
        this.file = file;
    }

}

