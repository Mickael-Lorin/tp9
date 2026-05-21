package fr.ekod.cda.ja.tp7.dto.movie;

import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;

public record MovieDTO(
        Integer id,
        String title,
        Integer releaseYear,
        Integer durationMinutes,
        String genre,
        String synopsis,
        DirectorDTO director
) {
}
