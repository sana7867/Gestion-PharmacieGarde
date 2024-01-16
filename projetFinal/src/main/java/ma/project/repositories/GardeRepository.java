package ma.project.repositories;

import ma.project.entities.Garde;
import ma.project.entities.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GardeRepository extends JpaRepository<Garde, Long> {
    List<Garde> findGardeByPharmacies(Pharmacie pharmacie);

}

