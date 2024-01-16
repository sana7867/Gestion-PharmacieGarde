package ma.project.controllers;

import ma.project.services.GardeService;
import ma.project.entities.Garde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/garde")
public class GardeController {

    @Autowired
    private GardeService gardeService;

    @GetMapping("/list")
    public String listGardes(Model model) {
        List<Garde> gardes = gardeService.findAll();
        model.addAttribute("gardes", gardes);
        return "garde/list";
    }

    @GetMapping("/form")
    public String showGardeForm(Model model) {
        model.addAttribute("garde", new Garde());
        return "garde/form";
    }
	
	@PostMapping("/save")
	public String saveOrUpdateGarde(@ModelAttribute("garde") Garde garde) {
		if (garde.getId() == null) {
			// If ID is null, it's a new Garde, save it
			gardeService.create(garde);
		} else {
			// If ID is present, it's an existing Garde, update it
			gardeService.update(garde);
		}
		return "redirect:/garde/list";
	}
	

    @GetMapping("/edit/{id}")
    public String editGarde(@PathVariable Long id, Model model) {
        Garde garde = gardeService.findById(id);
        model.addAttribute("garde", garde);
        return "garde/form";
    }

   

    @GetMapping("/delete/{id}")
    public String deleteGarde(@PathVariable Long id) {
        Garde garde = gardeService.findById(id);
        gardeService.delete(garde);
        return "redirect:/garde/list";
    }
}
