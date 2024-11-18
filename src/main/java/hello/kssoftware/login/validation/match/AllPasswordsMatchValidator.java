package hello.kssoftware.login.validation.match;

import hello.kssoftware.login.Member;
import hello.kssoftware.login.MemberRepository;
import hello.kssoftware.login.dto.PasswordChangeDto;
import hello.kssoftware.login.encrypt.PasswordEncoder;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AllPasswordsMatchValidator implements ConstraintValidator<AllPasswordsMatch, Object> {

    private final @NotNull HttpSession session;
    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        PasswordChangeDto dto = (PasswordChangeDto) o;
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();

        Member member = (Member) session.getAttribute("loginUser");
        Optional<Member> memberOptional = memberRepository.findById(member.getId());
        Member savedMember = memberOptional.orElse(null);

        boolean isOldPasswordCorrects = PasswordEncoder.matches(oldPassword, savedMember.getSalt(), savedMember.getPassword());
        return isOldPasswordCorrects &&
                newPassword.equals(confirmPassword);
    }
}
