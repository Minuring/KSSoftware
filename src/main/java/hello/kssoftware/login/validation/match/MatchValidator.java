package hello.kssoftware.login.validation.match;

import hello.kssoftware.login.Member;
import hello.kssoftware.login.MemberRepository;
import hello.kssoftware.login.dto.MemberLoginDto;
import hello.kssoftware.login.encrypt.PasswordEncoder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchValidator implements ConstraintValidator<Match, Object> {
    private final MemberRepository memberRepository;

    @Override
    public void initialize(Match constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        MemberLoginDto dto = (MemberLoginDto) o;
        String id = dto.getId();
        String rawPassword = dto.getPassword();
        if (id.isEmpty() || rawPassword.isEmpty()) {
            return true;
        }

        Member savedMember = memberRepository.findById(id).orElse(null);
        if (savedMember == null) return false;

        String salt = savedMember.getSalt();
        String savedPassword = savedMember.getPassword();
        return PasswordEncoder.matches(rawPassword, salt, savedPassword);
    }
}
