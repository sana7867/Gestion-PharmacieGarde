package ma.project.repositories;

import ma.project.entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrateur, Long> {
   Administrateur findByEmail(String email);
   Administrateur findByNom(String nom);

}
