package hello.kssoftware.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ColumnDefault(value = "0")
    private Integer views = 0;

}
