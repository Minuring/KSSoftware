package hello.kssoftware.login.validation.match;

import hello.kssoftware.login.MemberRepository;
import hello.kssoftware.login.dto.MemberLoginDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MatchValidator implements ConstraintValidator<Match, Object> {
    private final MemberRepository memberRepository;

    @Override
    public void initialize(Match constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        MemberLoginDto checkMember = (MemberLoginDto) o;
        String id = checkMember.getId();
        String password = checkMember.getPassword();
        if (id.isEmpty() || password.isEmpty()) {
            return true;
        }
        if (memberRepository.findUserId(id).isEmpty()) {
            return false;
        }
        return memberRepository.findUserId(id).get().getId().equals(id) &&
                memberRepository.findUserId(id).get().getPassword().equals(password);
    }
}
