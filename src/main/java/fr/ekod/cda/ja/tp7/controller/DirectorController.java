package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;
import fr.ekod.cda.ja.tp7.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorDTO>> findAll() {
        return ResponseEntity.ok(directorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(directorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> create(@Valid @RequestBody CreateDirectorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> update(@PathVariable Integer id, @Valid @RequestBody CreateDirectorDTO dto) {
        return ResponseEntity.ok(directorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
