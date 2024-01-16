package ma.project.repositories;

import ma.project.entities.Pharmacien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacienRepository extends JpaRepository<Pharmacien, Long> {
   Pharmacien findByEmail(String email);
}
