package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;
import fr.ekod.cda.ja.tp7.entity.Director;
import fr.ekod.cda.ja.tp7.exception.DirectorNotFoundException;
import fr.ekod.cda.ja.tp7.mapper.DirectorMapper;
import fr.ekod.cda.ja.tp7.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public List<DirectorDTO> findAll() {
        return directorRepository.findAll().stream()
                .map(directorMapper::toDto)
                .toList();
    }

    public DirectorDTO findById(Integer id) {
        return directorRepository.findById(id)
                .map(directorMapper::toDto)
                .orElseThrow(() -> new DirectorNotFoundException(id));
    }

    public Director getEntityById(Integer id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new DirectorNotFoundException(id));
    }

    @Transactional
    public DirectorDTO create(CreateDirectorDTO dto) {
        Director director = directorMapper.toEntity(dto);
        return directorMapper.toDto(directorRepository.save(director));
    }

    @Transactional
    public DirectorDTO update(Integer id, CreateDirectorDTO dto) {
        Director existing = getEntityById(id);
        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setNationality(dto.nationality());
        existing.setBirthDate(dto.birthDate());
        return directorMapper.toDto(directorRepository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        if (!directorRepository.existsById(id)) {
            throw new DirectorNotFoundException(id);
        }
        directorRepository.deleteById(id);
    }
}
