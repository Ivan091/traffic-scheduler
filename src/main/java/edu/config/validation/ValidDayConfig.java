package edu.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@SuppressWarnings("unused")
@Constraint(validatedBy = DayConfigValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDayConfig {

    String message() default "Map didn't contain some keys (must be [0..23]) or some values were less than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
