package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.auth.*;
import fr.ekod.cda.ja.tp7.security.CustomUserDetailsService;
import fr.ekod.cda.ja.tp7.security.JwtService;
import fr.ekod.cda.ja.tp7.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        UserDTO created = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        UserDetails user = userDetailsService.loadUserByUsername(dto.email());
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        TokenResponseDTO response = new TokenResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessExpirationSeconds()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@Valid @RequestBody RefreshRequestDTO dto) {
        String refreshToken = dto.refreshToken();

        try {
            String type = jwtService.extractTokenType(refreshToken);
            if (!"refresh".equals(type)) {
                throw new BadCredentialsException("Invalid token type");
            }

            String username = jwtService.extractUsername(refreshToken);
            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(refreshToken, user)) {
                throw new BadCredentialsException("Invalid refresh token");
            }

            String newAccessToken = jwtService.generateAccessToken(user);

            TokenResponseDTO response = new TokenResponseDTO(
                    newAccessToken,
                    refreshToken,
                    "Bearer",
                    jwtService.getAccessExpirationSeconds()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(userService.findByEmail(principal.getUsername()));
    }
}
