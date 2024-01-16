package ma.project.controllers;

import ma.project.services.FileUploadUtil;
import ma.project.services.VilleService;
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
@RequestMapping("/ville")
public class VilleController {

	@Autowired
	private VilleService villeService;

	@GetMapping("/list")
	public String listVilles(Model model) {
		List<Ville> villes = villeService.findAll();
		model.addAttribute("villes", villes);
		return "ville/list";
	}

	@GetMapping("/form")
	public String showVilleForm(Model model) {
		model.addAttribute("ville", new Ville());
		return "ville/form";
	}


	@PostMapping("/save")
public String saveVille(@Validated @ModelAttribute("ville") Ville ville,
                        BindingResult bindingResult,
                        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
    if (bindingResult.hasErrors()) {
        return "ville/form";
    }

    if (imageFile != null && !imageFile.isEmpty()) {
        String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

        // Utilisez la méthode saveFile avec le nom de fichier généré
        FileUploadUtil.saveFile(imageName, imageFile);

        ville.setImage(imageName);
    }

    villeService.create(ville);
    return "redirect:/ville/list";
}

	@GetMapping("/edit/{id}")
	public String editVille(@PathVariable Long id, Model model) {
		Ville ville = villeService.findById(id);
		model.addAttribute("ville", ville);
		return "ville/editForm";
	}


	@PostMapping("/update")
	public String updateVille(@Validated @ModelAttribute("ville") Ville updatedVille,
							  BindingResult bindingResult,
							  @RequestParam("imageFile") MultipartFile imageFile,
							  Model model) throws IOException {
		if (bindingResult.hasErrors()) {
			return "ville/editForm";
		}

		// Récupérer la ville existante de la base de données
		Ville existingVille = villeService.findById(updatedVille.getId());

		// Mettre à jour les propriétés de la ville existante
		existingVille.setNom(updatedVille.getNom());

		// Gestion de l'image
		if (imageFile != null && !imageFile.isEmpty()) {
			String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
			FileUploadUtil.saveFile(imageName, imageFile);
			existingVille.setImage(imageName);
		}

		// Synchroniser les zones existantes avec les zones mises à jour
		for (Zone existingZone : existingVille.getZones()) {
			// Si la zone existante n'est pas présente dans la nouvelle liste de zones, la dissocier
			if (!updatedVille.getZones().contains(existingZone)) {
				existingZone.setVille(null); // Dissocier la zone de la ville
			}
		}

		// Synchroniser les nouvelles zones avec la ville
		for (Zone newZone : updatedVille.getZones()) {
			if (!existingVille.getZones().contains(newZone)) {
				existingVille.addZone(newZone);
			}
		}
		villeService.update(existingVille);
		return "redirect:/ville/list";
	}


	@GetMapping("/delete/{id}")
	public String deleteVille(@PathVariable Long id) {
		Ville ville = villeService.findById(id);
		villeService.delete(ville);
		return "redirect:/ville/list";
	}
}
