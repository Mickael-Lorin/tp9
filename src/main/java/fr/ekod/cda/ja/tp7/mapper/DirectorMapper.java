package fr.ekod.cda.ja.tp7.mapper;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;
import fr.ekod.cda.ja.tp7.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorDTO toDto(Director director);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", ignore = true)
    Director toEntity(CreateDirectorDTO dto);
}
