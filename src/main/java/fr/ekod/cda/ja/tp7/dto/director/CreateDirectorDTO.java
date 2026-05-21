package fr.ekod.cda.ja.tp7.dto.director;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateDirectorDTO(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Nationality is required")
        String nationality,

        LocalDate birthDate
) {
}
