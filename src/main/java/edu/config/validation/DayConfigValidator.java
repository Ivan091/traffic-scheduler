package edu.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.stream.IntStream;


public class DayConfigValidator implements ConstraintValidator<ValidDayConfig, Map<Integer, Double>> {

    @Override
    public boolean isValid(Map<Integer, Double> value, ConstraintValidatorContext context) {
        return IntStream.range(0, 24).allMatch(value::containsKey) &&
                value.values().stream().allMatch(x -> x > 0);
    }
}
