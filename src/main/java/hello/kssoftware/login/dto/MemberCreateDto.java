package hello.kssoftware.login.dto;

import hello.kssoftware.login.validation.duplicated.NotDuplicate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateDto {

    @NotBlank
    @Size(min = 5)
    @NotDuplicate
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    @NotDuplicate
    @Size(min = 5)
    private String name;

    @NotNull
    @Min(value = 10)
    private Integer number;
}
