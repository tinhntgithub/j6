package dtn.asm.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dtn.asm.service.SessionService;

@Controller
public class AdminController {
	SessionService session;

	@RequestMapping({ "/admin", "/admin/index.html" })
	public String adminPage(Model m) {
		return "redirect:/assets/admin/index.html";
	}

}
