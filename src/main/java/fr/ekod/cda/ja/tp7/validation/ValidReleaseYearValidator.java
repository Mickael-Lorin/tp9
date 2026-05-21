package fr.ekod.cda.ja.tp7.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class ValidReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Integer> {

    private static final int CINEMA_BIRTH_YEAR = 1888;
    private static final int FUTURE_TOLERANCE_YEARS = 2;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull se charge de la nullite
        }
        int maxYear = Year.now().getValue() + FUTURE_TOLERANCE_YEARS;
        return value >= CINEMA_BIRTH_YEAR && value <= maxYear;
    }
}
