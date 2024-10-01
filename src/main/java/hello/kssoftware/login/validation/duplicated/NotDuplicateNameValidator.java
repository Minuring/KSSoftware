package hello.kssoftware.login.validation.duplicated;

import hello.kssoftware.login.MemberRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class NotDuplicateNameValidator implements ConstraintValidator<NotDuplicateName, String> {

    private final MemberRepository memberRepository;
    @Override
    public void initialize(NotDuplicateName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {

        return memberRepository.findByName(name).isEmpty();
    }

}
