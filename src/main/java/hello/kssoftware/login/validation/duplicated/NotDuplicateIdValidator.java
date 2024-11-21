package hello.kssoftware.login.validation.duplicated;

import hello.kssoftware.login.MemberRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
public class NotDuplicateIdValidator implements ConstraintValidator<NotDuplicateId, String> {

    private final MemberRepository memberRepository;
    @Override
    public void initialize(NotDuplicateId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext constraintValidatorContext) {

        return memberRepository.findById(userId).isEmpty();
    }

}
