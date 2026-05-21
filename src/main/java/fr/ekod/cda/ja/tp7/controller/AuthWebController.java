package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.tp7.security.CustomUserDetailsService;
import fr.ekod.cda.ja.tp7.security.JwtService;
import fr.ekod.cda.ja.tp7.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthWebController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response, // 💡 Remplacement de HttpSession par HttpServletResponse
            Model model
    ) {
        try {
            // 1. Authentification
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. 💡 CORRECTION : Génération du JWT (comme pour le register)
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String jwtToken = jwtService.generateAccessToken(userDetails);

            // 3. 💡 CORRECTION : Dépôt du cookie ACCES_TOKEN
            addTokenCookie(response, jwtToken);

            return "redirect:/movies";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid email or password");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequestDTO("", "", "", ""));
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerProcess(
            RegisterRequestDTO registerRequest,
            HttpServletResponse response,
            Model model
    ){
        try {
            userService.register(registerRequest);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(registerRequest.email(), registerRequest.password());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var userDetails = userDetailsService.loadUserByUsername(registerRequest.email());
            String jwtToken = jwtService.generateAccessToken(userDetails);

            // 💡 CORRECTION : Utilisation de la méthode utilitaire pour avoir le nom ACCES_TOKEN
            addTokenCookie(response, jwtToken);

            return "redirect:/movies";
        } catch (Exception e){
            model.addAttribute("error", "Email already used");
            model.addAttribute("registerRequest", registerRequest);
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        // 💡 Pour déconnecter en mode JWT, on supprime le cookie en le remplaçant par un cookie expiré
        Cookie jwtCookie = new Cookie("ACCES_TOKEN", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Durée de vie à 0 = suppression immédiate par le navigateur
        response.addCookie(jwtCookie);

        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

    // ==========================================
    //   💡 MÉTHODE UTILITAIRE UNIQUE POUR LE COOKIE
    // ==========================================
    private void addTokenCookie(HttpServletResponse response, String token) {
        // Le nom doit correspondre EXACTEMENT à ce que cherche votre filtre : "ACCES_TOKEN"
        Cookie jwtCookie = new Cookie("ACCES_TOKEN", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24); // 24 heures
        jwtCookie.setSecure(false); // Mettre à true en production (HTTPS)
        response.addCookie(jwtCookie);
    }
}