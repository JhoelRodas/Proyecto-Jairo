package bo.com.jvargas.veterinaria.api.admusuarios;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {


	@RequestMapping("/")
	String home(ModelMap modal) {
		modal.addAttribute("title","Veterinaria");
		return "forward:/index.html";
	}

	@RequestMapping({"/administracion/**",
			"/login",
			"/compras/**",
			"/ventas/**",
			"/inventario/**"})
	String partialHandler(/*@PathVariable("page") final String page*/) {
		return "forward:/index.html";
	}



}
