package com.teamntp.firstproject.common;



import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.util.regex.Pattern;

@Documented
@Constraint(validatedBy = SelectionData.CustomizedValidator.class)
public @interface SelectionData {
    String message() default "데이터 오류";

    String regexp();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CustomizedValidator implements ConstraintValidator<SelectionData, String> {
        private String regexp;

        @Override
        public void initialize(SelectionData constraintAnnotation) {
            this.regexp = constraintAnnotation.regexp();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null
                    || (!value.equals("") && !Pattern.matches(this.regexp, value))) {
                return false;
            } else {
                return true;
            }
        }
    }
}
