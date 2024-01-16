package ma.project.repositories;

import ma.project.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository< Zone, Long> {
    List<Zone> findByVilleNom(String villeNom);
}