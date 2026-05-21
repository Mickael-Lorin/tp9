package fr.ekod.cda.ja.tp7.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidReleaseYearValidator.class)
public @interface ValidReleaseYear {

    String message() default "Release year must be between 1888 and current year + 2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
