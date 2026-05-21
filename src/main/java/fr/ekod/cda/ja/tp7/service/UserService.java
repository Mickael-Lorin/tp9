package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.tp7.dto.auth.UserDTO;
import fr.ekod.cda.ja.tp7.entity.Role;
import fr.ekod.cda.ja.tp7.entity.User;
import fr.ekod.cda.ja.tp7.exception.EmailAlreadyUsedException;
import fr.ekod.cda.ja.tp7.mapper.UserMapper;
import fr.ekod.cda.ja.tp7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException(dto.email());
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER); // role is forced server-side

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in DB"));
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
