package fr.ekod.cda.ja.tp7.dto.movie;

import fr.ekod.cda.ja.tp7.validation.ValidReleaseYear;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateMovieDTO(

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Release year is required")
        @ValidReleaseYear
        Integer releaseYear,

        @NotNull(message = "Duration is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        @Max(value = 1000, message = "Duration is unrealistically long")
        Integer durationMinutes,

        @NotBlank(message = "Genre is required")
        String genre,

        String synopsis,

        @NotNull(message = "Director id is required")
        Integer directorId
) {
}
