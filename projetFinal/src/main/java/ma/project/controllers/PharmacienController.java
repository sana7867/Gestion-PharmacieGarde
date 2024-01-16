package ma.project.controllers;

import jakarta.servlet.http.HttpServletRequest;
import ma.project.services.*;
import ma.project.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pharmacien")
public class PharmacienController {

	@Autowired
	private PharmacienService pharmacienService;

	@Autowired
	private PharmacieService pharmacieService;

	@Autowired
	private ZoneService zoneService;
	@Autowired
	private GardeService gardeService;
	@Autowired
	private PharmacieGardeService pharmacieGardeService;
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "pharmacien/login";
	}

	@GetMapping("/home")
	public String showHomeForm(@ModelAttribute("pharmacien") Pharmacien pharmacien, Model model) {
		List<Zone> zones = zoneService.findAll();
		List<Pharmacie> pharmacies = pharmacieService.findByUsersId(pharmacien.getId());
		model.addAttribute("pharmacie", new Pharmacie());
		model.addAttribute("zones", zones);
		model.addAttribute("pharmacies", pharmacies);
		return "pharmacien/home";
	}
@GetMapping("/montionerGarde")
public String showGarde(@ModelAttribute("pharmacie") Pharmacie pharmacie, Model model){
		List<Garde>gardes=gardeService.findByPharamcies(pharmacie);
model.addAttribute("gardes",gardes);
		return"/pharmacien/montionerGarde";
}
	@GetMapping("/home2")
	public String showHome2Form(Model model) {
		return "pharmacien/home2";
	}

	@PostMapping("/connecter")
	public String login(@ModelAttribute("pharmacien") Pharmacien pharmacien, Model model, HttpServletRequest request) {
		Pharmacien existingPharmacien = pharmacienService.findByEmail(pharmacien.getEmail());

		if (existingPharmacien != null && existingPharmacien.getPassword().equals(pharmacien.getPassword())) {
			List<Pharmacie> pharmacies = pharmacieService.findByUsersId(existingPharmacien.getId());
			model.addAttribute("pharmacies", pharmacies);
			model.addAttribute("pharmacien", existingPharmacien);

			// Store the logged-in pharmacist in the session
			request.getSession().setAttribute("pharmacien", existingPharmacien);

			return "redirect:/pharmacien/home";
		} else {
			model.addAttribute("error", "Identifiants invalides");
			return "pharmacien/login";
		}
	}



	@GetMapping("/inscription")
	public String showRegistrationForm(Model model) {
		model.addAttribute("pharmacien", new Pharmacien());
		return "pharmacien/inscription";
	}

	@PostMapping("/inscription")
	public String registerPharmacien(@ModelAttribute("pharmacien") Pharmacien pharmacien,
									 @RequestParam("confirmPassword") String confirmPassword,
									 Model model) {
		Pharmacien existingPharmacien = pharmacienService.findByEmail(pharmacien.getEmail());

		if (existingPharmacien != null) {
			model.addAttribute("error", "Un pharmacien avec cet e-mail existe déjà");
			return "pharmacien/inscription";
		}

		if (!pharmacien.getPassword().equals(confirmPassword)) {
			model.addAttribute("error", "Les mots de passe ne correspondent pas");
			return "pharmacien/inscription";
		}

		pharmacienService.create(pharmacien);
		return "redirect:/pharmacien/login";
	}

	@GetMapping("/list")
	public String listPharmaciens(Model model) {
		List<Pharmacien> pharmaciens = pharmacienService.findAll();
		model.addAttribute("pharmaciens", pharmaciens);
		return "pharmacien/list";
	}

	@GetMapping("/form")
	public String showPharmacienForm(Model model) {
		model.addAttribute("pharmacien", new Pharmacien());
		return "pharmacien/form";
	}

	@PostMapping("/save")
	public String savePhar(@ModelAttribute("pharmacien") Pharmacien pharmacien) {
		pharmacienService.create(pharmacien);
		return "redirect:/pharmacien/login";
	}

	@GetMapping("/edit/{id}")
	public String editPhar(@PathVariable Long id, Model model) {
		Pharmacien pharmacien = pharmacienService.findById(id);
		model.addAttribute("pharmacien", pharmacien);
		return "pharmacien/editForm";
	}

	@PostMapping("/update")
	public String updatePhar(@ModelAttribute("pharmacien") Pharmacien updatedPharmacien) {
		Long pharmacienId = updatedPharmacien.getId();

		if (pharmacienId == null) {
			// Gérer le cas où l'ID est nul, peut-être rediriger vers une page d'erreur
			return "redirect:/error";
		}

		Pharmacien existingPharmacien = pharmacienService.findById(pharmacienId);

		// Mettre à jour les propriétés du pharmacien existant
		existingPharmacien.setNom(updatedPharmacien.getNom());
		existingPharmacien.setPrenom(updatedPharmacien.getPrenom());
		existingPharmacien.setEmail(updatedPharmacien.getEmail());
		existingPharmacien.setPassword(updatedPharmacien.getPassword());

		pharmacienService.update(existingPharmacien);
		return "redirect:/pharmacien/list";
	}



	@GetMapping("/delete/{id}")
	public String deletePharmacien(@PathVariable Long id) {
		Pharmacien pharmacien = pharmacienService.findById(id);
		pharmacienService.delete(pharmacien);
		return "redirect:/pharmacien/list";
	}
}
