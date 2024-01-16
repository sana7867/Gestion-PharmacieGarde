package ma.project.repositories;

import ma.project.entities.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie, Long> {
    List<Pharmacie> findByZoneId(Long zoneId);
    List<Pharmacie> findByZoneVilleNom(String nomVille);
    List<Pharmacie> findByZoneIdAndZoneVilleNom(Long zoneId, String nomVille);
   List< Pharmacie>  findByUsersId(Long usersId);

}