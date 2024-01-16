package ma.project.repositories;


import ma.project.entities.Pharmacie;
import ma.project.entities.PharmacieGarde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PharmacieGardeRepository extends JpaRepository<PharmacieGarde, Long> {
    List<PharmacieGarde> findByPharmacieId(Long pharmacieId);


}
