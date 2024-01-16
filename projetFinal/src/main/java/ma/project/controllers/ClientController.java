package ma.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {



	@GetMapping("/home")
	public String showHomeForm(Model model) {
		return "client/home";
	}


}
