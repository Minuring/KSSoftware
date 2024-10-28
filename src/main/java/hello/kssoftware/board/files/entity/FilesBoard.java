package hello.kssoftware.board.files.entity;

import hello.kssoftware.board.common.Board;
import hello.kssoftware.login.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Entity
@DiscriminatorValue("Files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilesBoard extends Board {

    @Embedded
    protected UploadFile file;

    @Builder
    public FilesBoard(Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, UploadFile file) {
        super(writer, title, content, createDate, updateDate);
        this.file = file;
    }

    public void update(String title, String content, LocalDateTime updateDate, UploadFile file) {
        super.update(title, content, updateDate);
        this.file = file;
    }
}

