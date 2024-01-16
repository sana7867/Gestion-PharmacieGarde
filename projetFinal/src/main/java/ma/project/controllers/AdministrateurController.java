package ma.project.controllers;
import ma.project.services.*;
import ma.project.entities.Administrateur;
import ma.project.entities.Pharmacie;
import ma.project.entities.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministrateurController {

	@Autowired
	private AdminService administrateurService;
	@Autowired
	PharmacieService pharmacieService;
	@Autowired
	VilleService villeService;
	@Autowired
	GardeService gardeService;

	@Autowired
	PharmacienService pharmacienService;
	@Autowired
	ZoneService zoneService;

	@GetMapping("/pharmaciesParZoneData")
	@ResponseBody
	public List<Integer> getPharmaciesParZoneData() {
		List<Zone> zones = zoneService.findAll();
		List<Integer> nombrePharmaciesParZone = new ArrayList<>();

		for (Zone zone : zones) {
			List<Pharmacie> pharmacies = pharmacieService.findByZone(zone.getId());
			nombrePharmaciesParZone.add(pharmacies.size());
		}

		return nombrePharmaciesParZone;
	}

	@GetMapping("/statistique")
	public String afficherStatistiques(@ModelAttribute("admin") Administrateur admin, Model model) {
		int totalPharmacies = pharmacieService.findAll().size();
		int totalVilles = villeService.findAll().size();
		int totalGardes = gardeService.findAll().size();
		int totalPharmaciens = pharmacienService.findAll().size();
		List<Zone> zones = zoneService.findAll();
		List<String> nomsZones = new ArrayList<>();
		List<Integer> nombrePharmaciesParZone = new ArrayList<>();
		for (Zone zone : zones) {
			nomsZones.add(zone.getNom());
			List<Pharmacie> pharmacies = pharmacieService.findByZone(zone.getId());
			nombrePharmaciesParZone.add(pharmacies.size());
		}
		model.addAttribute("nomsZones", nomsZones);
		model.addAttribute("nombrePharmaciesParZone", nombrePharmaciesParZone);
		model.addAttribute("totalPharmacies", totalPharmacies);
		model.addAttribute("totalVilles", totalVilles);
		model.addAttribute("totalGardes", totalGardes);
		model.addAttribute("totalPharmaciens", totalPharmaciens);
		model.addAttribute("zones", zones);
		model.addAttribute("admin", admin.getNom());

		return "admin/statistique";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "admin/login";
	}

	@GetMapping("/home")
	public String showHomeForm(Model model) {
		return "admin/home";
	}
	@GetMapping("/home2")
	public String showHome2Form(Model model) {
		return "admin/home2";
	}

	@PostMapping("/connecter")
	public String login(@ModelAttribute("admin") Administrateur administrateur, Model model) {
		Administrateur existingAdmin = administrateurService.findByEmail(administrateur.getEmail());

		if (existingAdmin != null && existingAdmin.getPassword().equals(administrateur.getPassword())) {
			return "redirect:/admin/home2";
		} else if (existingAdmin == null) {
			Administrateur newAdmin = new Administrateur();
			newAdmin.setEmail("admin@admin");
			newAdmin.setPassword("admin@admin"); // Attention, ceci est très peu sécurisé en production

			administrateurService.create(newAdmin);
			return "redirect:/admin/home2";}
		else {
			model.addAttribute("error", "Identifiants invalides");
			return "admin/login";
		}
	}

	@GetMapping("/inscription")
	public String showRegistrationForm(Model model) {
		model.addAttribute("admin", new Administrateur());
		return "admin/inscription";
	}

	@PostMapping("/inscription")
	public String registerAdmin(@ModelAttribute("admin") Administrateur administrateur,
								@RequestParam("confirmPassword") String confirmPassword,
								Model model) {
		Administrateur existingAdmin = administrateurService.findByEmail(administrateur.getEmail());

		if (existingAdmin != null) {
			model.addAttribute("error", "Un admin avec cet e-mail existe déjà");
			return "admin/inscription";
		}

		if (!administrateur.getPassword().equals(confirmPassword)) {
			model.addAttribute("error", "Les mots de passe ne correspondent pas");
			return "admin/inscription";
		}

		administrateurService.create(administrateur);
		return "redirect:/admin/login";
	}


	@GetMapping("/list")
	public String listAdministrateurs(Model model) {
		List<Administrateur> administrateurs = administrateurService.findAll();
		model.addAttribute("administrateurs", administrateurs);
		return "admin/list";
	}

	@GetMapping("/form")
	public String showAdministrateurForm(Model model) {
		model.addAttribute("administrateur", new Administrateur());
		return "admin/form";
	}

	@PostMapping("/save")
	public String saveAdministrateur(@ModelAttribute("administrateur") Administrateur administrateur) {
		administrateurService.create(administrateur);
		return "redirect:/admin/login";
	}

	@GetMapping("/edit/{id}")
	public String editAdministrateur(@PathVariable Long id, Model model) {
		Administrateur administrateur = administrateurService.findById(id);
		model.addAttribute("administrateur", administrateur);
		return "admin/editForm";
	}

	@PostMapping("/update")
	public String updateAdministrateur(@ModelAttribute("administrateur") Administrateur administrateur) {
		administrateurService.update(administrateur);
		return "redirect:/admin/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteAdministrateur(@PathVariable Long id) {
		Administrateur administrateur = administrateurService.findById(id);
		administrateurService.delete(administrateur);
		return "redirect:/admin/list";
	}
}