package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieSummaryDTO>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieSummaryDTO>> findByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieService.findByGenre(genre));
    }

    @GetMapping("/director/{directorId}")
    public ResponseEntity<List<MovieSummaryDTO>> findByDirector(@PathVariable Integer directorId) {
        return ResponseEntity.ok(movieService.findByDirector(directorId));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@Valid @RequestBody CreateMovieDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Integer id, @Valid @RequestBody CreateMovieDTO dto) {
        return ResponseEntity.ok(movieService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDTO> patch(@PathVariable Integer id, @RequestBody CreateMovieDTO dto) {
        // No @Valid on PATCH: @NotBlank / @NotNull would block partial updates
        return ResponseEntity.ok(movieService.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
