package hello.kssoftware.login.validation.duplicated;

import hello.kssoftware.login.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class NotDuplicateValidator implements ConstraintValidator<NotDuplicate, String> {

    private final MemberService memberService;
    @Override
    public void initialize(NotDuplicate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (memberService.isUserIdExists(s)) {
            return false;
        }
        return !memberService.isUserNameExists(s);
    }

}
