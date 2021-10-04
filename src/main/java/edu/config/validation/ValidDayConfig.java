package edu.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = DayConfigValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDayConfig {

    String message() default "Invalid day config";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
