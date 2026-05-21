package fr.ekod.cda.ja.tp7.mapper;

import fr.ekod.cda.ja.tp7.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.tp7.dto.auth.UserDTO;
import fr.ekod.cda.ja.tp7.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequestDTO dto);
}
