package fr.ekod.cda.ja.tp7.dto.auth;

import fr.ekod.cda.ja.tp7.entity.Role;

public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
