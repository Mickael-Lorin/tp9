package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.entity.Director;
import fr.ekod.cda.ja.tp7.entity.Movie;
import fr.ekod.cda.ja.tp7.exception.MovieNotFoundException;
import fr.ekod.cda.ja.tp7.mapper.MovieMapper;
import fr.ekod.cda.ja.tp7.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final DirectorService directorService;

    public List<MovieSummaryDTO> findAll() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    public MovieDTO findById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public List<MovieSummaryDTO> findByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre).stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    public List<MovieSummaryDTO> findByDirector(Integer directorId) {
        // First check the director exists so we return a clean 404
        directorService.getEntityById(directorId);
        return movieRepository.findByDirectorId(directorId).stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    @Transactional
    public MovieDTO create(CreateMovieDTO dto) {
        Director director = directorService.getEntityById(dto.directorId());
        Movie movie = movieMapper.toEntity(dto);
        movie.setDirector(director);
        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Transactional
    public MovieDTO update(Integer id, CreateMovieDTO dto) {
        Movie existing = getEntityById(id);
        Director director = directorService.getEntityById(dto.directorId());
        existing.setTitle(dto.title());
        existing.setReleaseYear(dto.releaseYear());
        existing.setDurationMinutes(dto.durationMinutes());
        existing.setGenre(dto.genre());
        existing.setSynopsis(dto.synopsis());
        existing.setDirector(director);
        return movieMapper.toDto(movieRepository.save(existing));
    }

    @Transactional
    public MovieDTO patch(Integer id, CreateMovieDTO dto) {
        Movie existing = getEntityById(id);
        if (dto.title() != null) existing.setTitle(dto.title());
        if (dto.releaseYear() != null) existing.setReleaseYear(dto.releaseYear());
        if (dto.durationMinutes() != null) existing.setDurationMinutes(dto.durationMinutes());
        if (dto.genre() != null) existing.setGenre(dto.genre());
        if (dto.synopsis() != null) existing.setSynopsis(dto.synopsis());
        if (dto.directorId() != null) {
            existing.setDirector(directorService.getEntityById(dto.directorId()));
        }
        return movieMapper.toDto(movieRepository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException(id);
        }
        movieRepository.deleteById(id);
    }

    private Movie getEntityById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}
