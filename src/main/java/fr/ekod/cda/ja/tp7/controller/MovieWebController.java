package fr.ekod.cda.ja.tp7.controller;


import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.entity.Movie;
import fr.ekod.cda.ja.tp7.service.DirectorService;
import fr.ekod.cda.ja.tp7.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieWebController {
    private final MovieService movieService;
    private final DirectorService directorService;

    public MovieWebController(MovieService movieService, DirectorService directorService) {
        this.movieService = movieService;
        this.directorService = directorService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/movies")
    public String movies(Model model) {
        model.addAttribute(
                "movies",
                movieService.findAll()
        );
        return "movies/list";
    }
    @GetMapping("/movies/{id}")
    public String movie(Model model,@PathVariable Integer id) {
        model.addAttribute(
                "movie",
                movieService.findById(id)
        );
        return "movies/details";
    }

    @GetMapping("/movies/new")
    public String addMovieForm(Model model) {
        // 💡 On appelle le constructeur en lui donnant des valeurs par défaut pour le premier affichage
        model.addAttribute("movieForm", new CreateMovieDTO(
                "",
                2026,
                120,
                "",
                "",
                null
        ));

        model.addAttribute("directors", directorService.findAll()); // Pour remplir le <select>
        return "movies/form";
    }
    @PostMapping("/movies/new")
    public String addMovie(
            @Valid @ModelAttribute("movieForm") CreateMovieDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("directors", directorService.findAll());
            return "movies/form";
        }

        try {
            movieService.create(dto);
            return "redirect:/movies";
        } catch (Exception e) {
            // 💡 Changement du message ici pour coller au contexte d'un film
            model.addAttribute("error", "Ce film existe déjà ou les données sont invalides");
            model.addAttribute("directors", directorService.findAll());
            return "movies/form";
        }
    }
}
