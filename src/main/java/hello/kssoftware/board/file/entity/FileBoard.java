package hello.kssoftware.board.file.entity;

import hello.kssoftware.board.common.Board;
import hello.kssoftware.login.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@DiscriminatorValue("file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileBoard extends Board {

    @Embedded
    protected UploadFile file;

    @Builder
    public FileBoard(Member writer, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, UploadFile file) {
        super(writer, title, content, createDate, updateDate);
        this.file = file;
    }

    public void update(String title, String content, LocalDateTime updateDate, UploadFile file) {
        super.update(title, content, updateDate);
        this.file = file;
    }
}

