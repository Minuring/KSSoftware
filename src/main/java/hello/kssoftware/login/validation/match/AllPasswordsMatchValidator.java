package hello.kssoftware.login.validation.match;

import hello.kssoftware.login.Member;
import hello.kssoftware.login.MemberRepository;
import hello.kssoftware.login.dto.PasswordChangeDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllPasswordsMatchValidator implements ConstraintValidator<AllPasswordsMatch, Object> {

    private final @NotNull HttpSession session;

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        PasswordChangeDto dto = (PasswordChangeDto) o;
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();

        Member member = (Member) session.getAttribute("loginUser");
        return oldPassword.equals(member.getPassword()) &&
                newPassword.equals(confirmPassword);
    }
}
