package ma.project.controllers;

import ma.project.services.FileUploadUtil;
import ma.project.services.VilleService;
import ma.project.services.ZoneService;
import ma.project.entities.Pharmacie;
import ma.project.entities.Ville;
import ma.project.entities.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/zone")
public class ZoneController {

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private VilleService villeService;

	@GetMapping("/list")
	public String listZones(Model model) {
		List<Zone> zones = zoneService.findAll();
		model.addAttribute("zones", zones);
		return "zone/list";
	}

	@GetMapping("/form")
	public String showZoneForm(Model model) {
		List<Ville> villes = villeService.findAll();
		model.addAttribute("zone", new Zone());
		model.addAttribute("villes", villes);
		return "zone/form";
	}
	@PostMapping("/save")
public String saveVille(@Validated @ModelAttribute("zone") Zone zone,
                        BindingResult bindingResult,
                        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
    if (bindingResult.hasErrors()) {
        return "zone/form";
    }

    if (imageFile != null && !imageFile.isEmpty()) {
        String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        
        // Utilisez la méthode saveFile avec le nom de fichier généré
        FileUploadUtil.saveFile(imageName, imageFile);
        
        zone.setImage(imageName);
    }

    zoneService.create(zone);
    return "redirect:/zone/list";
}


	@GetMapping("/edit/{id}")
	public String editZone(@PathVariable Long id, Model model) {
		Zone zone = zoneService.findById(id);
		List<Ville> villes = villeService.findAll();
		model.addAttribute("zone", zone);
		model.addAttribute("villes", villes);
		return "zone/editForm";
	}

	@PostMapping("/update")
	public String updateZone(@Validated @ModelAttribute("zone") Zone updatedZone,
							 BindingResult bindingResult,
							 @RequestParam("imageFile") MultipartFile imageFile,
							 Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			return "zone/editForm";
		}

		Zone existingZone = zoneService.findById(updatedZone.getId());

		existingZone.setNom(updatedZone.getNom());

		// Gestion de l'image
		if (imageFile != null && !imageFile.isEmpty()) {
			String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
			FileUploadUtil.saveFile(imageName, imageFile);
			existingZone.setImage(imageName);
		}

		// Vérifier si la valeur de ville dans updatedZone est null, auquel cas, la remplacer par la valeur actuelle de existingZone
		if (updatedZone.getVille() != null) {
			// Mettre à jour la relation avec la ville uniquement si une nouvelle ville est sélectionnée
			existingZone.setVille(updatedZone.getVille());
		}

// Synchroniser les pharmacies existantes avec les pharmacies mises à jour
		for (Pharmacie existingPharmacie : existingZone.getPharmacies()) {
			// Si la pharmacie existante n'est pas présente dans la nouvelle liste de pharmacies, la dissocier
			if (!updatedZone.getPharmacies().contains(existingPharmacie)) {
				existingPharmacie.setZone(null); // Dissocier la pharmacie de la zone
			}
		}

// Synchroniser les nouvelles pharmacies avec la zone
		for (Pharmacie newPharmacie : updatedZone.getPharmacies()) {
			if (!existingZone.getPharmacies().contains(newPharmacie)) {
				existingZone.addPharmacie(newPharmacie);
			}
		}

		zoneService.update(existingZone);
		return "redirect:/zone/list";
	}

	// Delete a zone
	@GetMapping("/delete/{id}")
	public String deleteZone(@PathVariable Long id) {
		Zone zone = zoneService.findById(id);
		zoneService.delete(zone);
		return "redirect:/zone/list";
	}
}
