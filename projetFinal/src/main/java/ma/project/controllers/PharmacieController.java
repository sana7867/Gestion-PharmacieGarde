package ma.project.controllers;

import ma.project.services.*;
import ma.project.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pharmacie")
public class PharmacieController {

	private  PharmacieService pharmacieService;
	private  ZoneService zoneService;
	private  UsersService usersService;
	private  VilleService villeService;
	private PharmacieGardeService pharmacieGardeService;

	@Autowired
	public PharmacieController(PharmacieGardeService pharmacieGardeService,VilleService villeService, PharmacieService pharmacieService, ZoneService zoneService, UsersService usersService) {
		this.pharmacieService = pharmacieService;
		this.zoneService = zoneService;
		this.villeService = villeService;
		this.usersService = usersService;
		this.pharmacieGardeService=pharmacieGardeService; }

	@GetMapping("/map")

	public String getAllPharmacies(Model model) {
		List<Pharmacie> pharmacies = pharmacieService.findAll();
		model.addAttribute("pharmacies",pharmacies);
		return "pharmacie/map";
	}


	@GetMapping("/listByZone")
	public String listPharmaciesByZone(@RequestParam(name = "zoneId", required = false) Long zoneId, Model model) {
		List<Pharmacie> pharmacies;
		Map<Long, List<Pharmacie>> pharmaciesByZone = new HashMap<>();

		if (zoneId != null) {
			pharmacies = pharmacieService.findByZone(zoneId);
			pharmaciesByZone.put(zoneId, pharmacies);
		} else {
			pharmacies = pharmacieService.findAll();
			for (Pharmacie pharmacie : pharmacies) {
				Long currentZoneId = pharmacie.getZone().getId();
				pharmaciesByZone.computeIfAbsent(currentZoneId, k -> new ArrayList<>()).add(pharmacie);
			}
		}

		List<Zone> zones = zoneService.findAll();
		model.addAttribute("zones", zones);
		model.addAttribute("pharmaciesByZone", pharmaciesByZone);
		model.addAttribute("selectedZoneId", zoneId);

		return "pharmacie/list3";
	}

	@GetMapping("/listByVilleAndZone")
	public String filterPharmaciesByVilleAndZone(
			@RequestParam(name = "villeNom", required = false) String villeNom,
			@RequestParam(name = "zoneId", required = false) Long zoneId,
			Model model) {

		List<Pharmacie> pharmacies;
		List<Ville> villes = villeService.findAll();
		List<Zone> zonesByVille = null;

		if (villeNom != null) {
			zonesByVille = zoneService.findByVille(villeNom);
			pharmacies = (zoneId != null) ? pharmacieService.findByZone(zoneId) : pharmacieService.findZoneVilleNom(villeNom);
		} else {
			pharmacies = pharmacieService.findAll();
		}

		List<Zone> zones = zoneService.findAll();
		model.addAttribute("zones", zonesByVille);
		model.addAttribute("pharmacies", pharmacies);
		model.addAttribute("villes", villes);
		model.addAttribute("selectedZoneId", zoneId);
		model.addAttribute("selectedVilleNom", villeNom);

		return "pharmacie/list3";
	}

	@GetMapping("/list")
	public String listPharmacies(Model model, @RequestParam(name = "villeNom", required = false) String villeNom) {
		List<Pharmacie> pharmacies = pharmacieService.findAll();
		model.addAttribute("pharmacies", pharmacies);

		List<Zone> zones = (villeNom != null) ? zoneService.findByVille(villeNom) : zoneService.findAll();
		model.addAttribute("zones", zones);

		model.addAttribute("villes", villeService.findAll());
		model.addAttribute("selectedZoneId", null); // Set this based on the actual selected value
		model.addAttribute("selectedVilleNom", villeNom);

		return "pharmacie/list";
	}
	@GetMapping("/list2")
	public String listPharmacies2(Model model){
		List<Pharmacie> pharmacies = pharmacieService.findAll();
		model.addAttribute("pharmacies", pharmacies);
		return "pharmacie/list2";
	}
	@GetMapping("/list3")
	public String listPharmacies3(Model model){
		List<Pharmacie> pharmacies = pharmacieService.findAll();
		model.addAttribute("pharmacies", pharmacies);
		return "pharmacie/list3";
	}
	@GetMapping("/etatPharmacie/{pharmacieId}")
	public String showPharmacyStatus(@PathVariable Long pharmacieId, Model model) {
		Pharmacie pharmacie = pharmacieService.findById(pharmacieId);
		List<PharmacieGarde> gardes = pharmacieGardeService.findByPharmacieId(pharmacieId);

		model.addAttribute("pharmacie", pharmacie);
		model.addAttribute("gardes", gardes);

		return "pharmacien/etatPharmacie";
	}
	@GetMapping("/form")
	public String showPharmacieForm(Model model) {
		model.addAttribute("pharmacie", new Pharmacie());
		model.addAttribute("zones", zoneService.findAll());
		model.addAttribute("users", usersService.findAll());
		return "pharmacie/form";
	}
	@GetMapping("/form2")
	public String showPharmacieForm2(Model model) {
		model.addAttribute("pharmacie", new Pharmacie());
		model.addAttribute("zones", zoneService.findAll());
		model.addAttribute("users", usersService.findAll());
		return "pharmacie/form2";
	}
		@PostMapping("/save")
public String saveVille(@Validated @ModelAttribute("pharmacie") Pharmacie pharmacie,
                        BindingResult bindingResult,
                        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
    if (bindingResult.hasErrors()) {
        return "pharmacie/form";
    }

    if (imageFile != null && !imageFile.isEmpty()) {
        String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

        // Utilisez la méthode saveFile avec le nom de fichier généré
        FileUploadUtil.saveFile(imageName, imageFile);

        pharmacie.setPhoto(imageName);
    }

    pharmacieService.create(pharmacie);
    return "redirect:/pharmacie/list";
}

	@PostMapping("/save2")
	public String saveVillez(@Validated @ModelAttribute("pharmacie") Pharmacie pharmacie,
							BindingResult bindingResult,
							@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		if (bindingResult.hasErrors()) {
			return "pharmacie/form2";
		}

		if (imageFile != null && !imageFile.isEmpty()) {
			String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

			// Utilisez la méthode saveFile avec le nom de fichier généré
			FileUploadUtil.saveFile(imageName, imageFile);

			pharmacie.setPhoto(imageName);
		}

		pharmacieService.create(pharmacie);
		return "redirect:/pharmacie/list2";
	}


	@GetMapping("/edit/{id}")
	public String editPharmacie(@PathVariable Long id, Model model) {
		Pharmacie pharmacie = pharmacieService.findById(id);
		List<Zone> zones = zoneService.findAll();
		List<Users> users = usersService.findAll();
		model.addAttribute("pharmacie", pharmacie);
		model.addAttribute("zones", zones);
		model.addAttribute("users", users);
		return "pharmacie/editForm";
	}
	@GetMapping("/edit2/{id}")
	public String editPharmacie2(@PathVariable Long id, Model model) {
		Pharmacie pharmacie = pharmacieService.findById(id);
		List<Zone> zones = zoneService.findAll();
		List<Users> users = usersService.findAll();
		model.addAttribute("pharmacie", pharmacie);
		model.addAttribute("zones", zones);
		model.addAttribute("users", users);
		return "pharmacie/editForm2";
	}

	@PostMapping("/update")
	public String updatePharmacie(
			@RequestParam Long id,
			@Validated @ModelAttribute("pharmacie") Pharmacie updatedPharmacie,
			BindingResult bindingResult,
			@RequestParam("imageFile") MultipartFile imageFile) throws IOException {

		if (bindingResult.hasErrors()) {
			return "pharmacie/editForm"; // Correct the view name
		}

		Pharmacie existingPharmacie = pharmacieService.findById(id);
		if (existingPharmacie == null) {
			return "redirect:/pharmacie/list";
		}

		existingPharmacie.setNom(updatedPharmacie.getNom());
		existingPharmacie.setLatitude(updatedPharmacie.getLatitude());
		existingPharmacie.setLongitude(updatedPharmacie.getLongitude());

		// Vérifier si la valeur de la zone dans updatedPharmacie est null, auquel cas, laisser la zone inchangée
		if (updatedPharmacie.getZone() != null) {
			existingPharmacie.setZone(updatedPharmacie.getZone());
		}

		existingPharmacie.setUsers(updatedPharmacie.getUsers());

		if (imageFile != null && !imageFile.isEmpty()) {
			String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
			FileUploadUtil.saveFile(imageName, imageFile);
			existingPharmacie.setPhoto(imageName);
		}

		pharmacieService.update(existingPharmacie);

		return "redirect:/pharmacie/list";
	}

	@PostMapping("/update2")
	public String updatePharmacie2(
			@RequestParam Long id,
			@Validated @ModelAttribute("pharmacie") Pharmacie updatedPharmacie,
			BindingResult bindingResult,
			@RequestParam("imageFile") MultipartFile imageFile) throws IOException {

		if (bindingResult.hasErrors()) {
			return "pharmacie/editForm2"; // Correct the view name
		}

		Pharmacie existingPharmacie = pharmacieService.findById(id);
		if (existingPharmacie == null) {
			return "redirect:/pharmacie/list2";
		}

		existingPharmacie.setNom(updatedPharmacie.getNom());
		existingPharmacie.setLatitude(updatedPharmacie.getLatitude());
		existingPharmacie.setLongitude(updatedPharmacie.getLongitude());

		// Vérifier si la valeur de la zone dans updatedPharmacie est null, auquel cas, laisser la zone inchangée
		if (updatedPharmacie.getZone() != null) {
			existingPharmacie.setZone(updatedPharmacie.getZone());
		}

		existingPharmacie.setUsers(updatedPharmacie.getUsers());

		if (imageFile != null && !imageFile.isEmpty()) {
			String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
			FileUploadUtil.saveFile(imageName, imageFile);
			existingPharmacie.setPhoto(imageName);
		}

		pharmacieService.update(existingPharmacie);

		return "redirect:/pharmacie/list2";
	}


	@GetMapping("/delete/{id}")
	public String deletePharmacie(@PathVariable Long id) {
		Pharmacie pharmacie = pharmacieService.findById(id);
		pharmacieService.delete(pharmacie);
		return "redirect:/pharmacie/list";
	}
	@GetMapping("/delete2/{id}")
	public String deletePharmacie2(@PathVariable Long id) {
		Pharmacie pharmacie = pharmacieService.findById(id);
		pharmacieService.delete(pharmacie);
		return "redirect:/pharmacie/list2";
	}

}