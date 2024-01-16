package ma.project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Garde implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "garde", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PharmacieGarde> pharmacies = new ArrayList<>();

	public List<PharmacieGarde> getPharmacies() {
		return pharmacies;
	}

	// Méthode pour ajouter une pharmacie à la liste
	public void addPharmacie(PharmacieGarde pharmacieGarde) {
		pharmacies.add(pharmacieGarde);
		pharmacieGarde.setGarde(this);
	}

	// Méthode pour supprimer une pharmacie de la liste
	public void removePharmacie(PharmacieGarde pharmacieGarde) {
		pharmacies.remove(pharmacieGarde);
		pharmacieGarde.setGarde(null);
	}

	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Garde() {
		
	}
}
