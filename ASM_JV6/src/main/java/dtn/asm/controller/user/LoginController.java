package dtn.asm.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dtn.asm.dao.AccountDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.model.LoginForm;
import dtn.asm.service.SessionService;
import dtn.asm.service.impl.AccountServiceImpl;
import dtn.asm.service.impl.AddressServiceImp;

@Controller
public class LoginController {

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Autowired
	AccountDAO accountdao;

	@Autowired
	AccountServiceImpl accountsService;

	@Autowired
	AddressServiceImp addressService;

	@Autowired
	SessionService session;

//	Login Page
	@GetMapping("/login.html")
	public String getLogin(Model m) {
		LoginForm login = new LoginForm();
		m.addAttribute("loginForm", login);
		return "user/home/login";
	}

//	@PostMapping("/login.html")
	public String postLogin(Model m, @Valid @ModelAttribute("loginForm") LoginForm login, Errors errors) {
		if (!errors.hasErrors()) {
			String user = login.getUsername();
			String pass = login.getPass();

			Accounts acc = new Accounts();
			acc = accountsService.findById(user);

			if (acc instanceof Accounts) {
				if (pass.equals(acc.getPassword())) {
					if (acc.getActive()) {
						session.set("account", acc);
						m.addAttribute("message", "456");
						return "redirect:/index.html";
					} else {
						m.addAttribute("message", "789");
					}
				} else {
					m.addAttribute("message", "1011");
				}
			} else {
				m.addAttribute("message", "1011");
			}
		} else {
			m.addAttribute("message", "123");
		}
		return "user/home/login";
	}
}
