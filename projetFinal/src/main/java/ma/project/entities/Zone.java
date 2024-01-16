package ma.project.entities;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Zone implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String image;
	@ManyToOne
	private Ville ville;
	@OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pharmacie> pharmacies = new ArrayList<>();  // Initialisez la collection

	// autres champs et méthodes

	public void addPharmacie(Pharmacie pharmacie) {
		pharmacies.add(pharmacie);
		pharmacie.setZone(this);
	}

	public void removePharmacie(Pharmacie pharmacie) {
		pharmacies.remove(pharmacie);
		pharmacie.setZone(null);
	}

	// autres méthodes

	public List<Pharmacie> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacie> pharmacies) {
		this.pharmacies = pharmacies;
	}
	@Transient
	@NotNull
	private MultipartFile imageFile;

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}


	public Ville getVille() {
		return ville;
	}
	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Zone() {
		super();
	}
	 
	
}
