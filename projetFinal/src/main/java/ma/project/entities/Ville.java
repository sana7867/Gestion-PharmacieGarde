package ma.project.entities;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Ville implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String image;
	@OneToMany(mappedBy = "ville", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Zone> zones;

	public List<Zone> getZones() {
		if (zones == null) {
			zones = new ArrayList<>();
		}
		return zones;
	}
	// other fields and methods

	public void addZone(Zone zone) {
		zones.add(zone);
		zone.setVille(this);
	}

	public void removeZone(Zone zone) {
		zones.remove(zone);
		zone.setVille(null);
	}



	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	@Transient
	@NotNull
	private MultipartFile imageFile;
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
	public Ville() {
		super();
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
}
