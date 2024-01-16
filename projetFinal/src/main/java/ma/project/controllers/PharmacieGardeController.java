package ma.project.controllers;

import ma.project.services.GardeService;
import ma.project.services.PharmacieGardeService;
import ma.project.services.PharmacieService;
import ma.project.entities.Garde;
import ma.project.entities.Pharmacie;
import ma.project.entities.PharmacieGarde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pharmacieGarde")
public class PharmacieGardeController {

	@Autowired
	private PharmacieGardeService pharmacieGardeService;

	@Autowired
	private PharmacieService pharmacieService;

	@Autowired
	private GardeService gardeService;

	@GetMapping("/list")
	public String listPharmacieGardes(Model model) {
		List<PharmacieGarde> pharmacieGardes = pharmacieGardeService.findAll();
		model.addAttribute("pharmacieGardes", pharmacieGardes);
		return "pharmacieGarde/list";
	}


	@GetMapping("/form")
	public String showPharmacieGardeForm(Model model) {
		List<Pharmacie> pharmacies=pharmacieService.findAll();
        List<Garde> gardes= gardeService.findAll();
		model.addAttribute("pharmacies", pharmacies);
		model.addAttribute("gardes", gardes);
		model.addAttribute("pharmacieGarde", new PharmacieGarde());
		return "pharmacieGarde/form";
	}
	@GetMapping("/historique")
	public String showHistorique(Model model){
    List<PharmacieGarde> pharmacieGardes=pharmacieGardeService.findAll();
    model.addAttribute("pharmacieGardes", pharmacieGardes);
		return "pharmacieGarde/historique";

	}
	@GetMapping("/historique2")
	public String showHistorique2(Model model){
		List<PharmacieGarde> pharmacieGardes = pharmacieGardeService.findAll();
		model.addAttribute("pharmacieGardes", pharmacieGardes);
		return "pharmacieGarde/historique2";

	}

	@PostMapping("/save")
	public String savePharmacieGarde(
			@ModelAttribute("pharmacieGarde") @Validated PharmacieGarde pharmacieGarde,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			// Log validation errors for debugging
			System.out.println("Validation errors: " + bindingResult.getAllErrors());

			List<Pharmacie> pharmacies = pharmacieService.findAll();
			List<Garde> gardes = gardeService.findAll();
			model.addAttribute("pharmacies", pharmacies);
			model.addAttribute("gardes", gardes);
			return "pharmacieGarde/form";
		}

		if (pharmacieGarde.getId() != null) {
			pharmacieGardeService.update(pharmacieGarde);
		} else {
			pharmacieGardeService.create(pharmacieGarde);
		}

		return "redirect:/pharmacieGarde/list";
	}

	@GetMapping("/edit/{id}")
	public String editPharmacieGarde(@PathVariable Long id, Model model) {
		PharmacieGarde existingPharmacieGarde = pharmacieGardeService.findById(id);
		if (existingPharmacieGarde == null) {
			// Handle the case where the PharmacieGarde with the given id is not found
			return "redirect:/pharmacieGarde/list";
		}

		List<Pharmacie> pharmacies = pharmacieService.findAll();
		List<Garde> gardes = gardeService.findAll();

		model.addAttribute("pharmacieGarde", existingPharmacieGarde);
		model.addAttribute("pharmacies", pharmacies);
		model.addAttribute("gardes", gardes);
		return "pharmacieGarde/editForm";
	}

	@PostMapping("/update")
	public String updatePharmacie(
			@ModelAttribute("pharmacieGarde") @Validated PharmacieGarde updatedPharmacieGarde,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			// Log validation errors for debugging
			System.out.println("Validation errors: " + bindingResult.getAllErrors());

			List<Pharmacie> pharmacies = pharmacieService.findAll();
			List<Garde> gardes = gardeService.findAll();
			model.addAttribute("pharmacies", pharmacies);
			model.addAttribute("gardes", gardes);
			return "pharmacieGarde/editForm";  // Correction ici
		}

		PharmacieGarde existingPharmacieGarde = pharmacieGardeService.findById(updatedPharmacieGarde.getId());
		if (existingPharmacieGarde == null) {
			// Handle the case where the PharmacieGarde with the given id is not found
			return "redirect:/pharmacieGarde/list";
		}

		// Update properties of existingPharmacieGarde with the values from updatedPharmacieGarde
		existingPharmacieGarde.setDateDebut(updatedPharmacieGarde.getDateDebut());
		existingPharmacieGarde.setDateFin(updatedPharmacieGarde.getDateFin());
		existingPharmacieGarde.setGarde(updatedPharmacieGarde.getGarde());
		existingPharmacieGarde.setPharmacie(updatedPharmacieGarde.getPharmacie());

		pharmacieGardeService.update(existingPharmacieGarde);

		return "redirect:/pharmacieGarde/list";
	}


	@GetMapping("/delete/{id}")
	public String deletePharmacieGarde(@PathVariable Long id) {
		PharmacieGarde pharmacieGarde = pharmacieGardeService.findById(id);
		pharmacieGardeService.delete(pharmacieGarde);
		return "redirect:/pharmacieGarde/list";
	}


}
