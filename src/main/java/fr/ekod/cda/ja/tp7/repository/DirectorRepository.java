package fr.ekod.cda.ja.tp7.repository;

import fr.ekod.cda.ja.tp7.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
