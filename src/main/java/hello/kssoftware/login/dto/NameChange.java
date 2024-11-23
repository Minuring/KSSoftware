package hello.kssoftware.login.dto;

import hello.kssoftware.login.validation.duplicated.NotDuplicateName;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NameChange {
    @NotDuplicateName
    @Size(min = 5)
    private String name;
}
