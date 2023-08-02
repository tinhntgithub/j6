package dtn.asm.controller.user;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import dtn.asm.dao.AccountDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.entity.ReCapchaResponse;
import dtn.asm.model.LoginForm;
import dtn.asm.service.AccountsService;
import dtn.asm.service.SessionService;
import dtn.asm.service.impl.AccountServiceImpl;
import dtn.asm.service.impl.AddressServiceImp;
import java.util.Optional;

@Controller
public class LoginController {

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	// @Autowired
	// AccountDAO accountdao;
	@Autowired
	AccountsService accountsService1;

	@Autowired
	AccountServiceImpl accountsService;

	@Autowired
	AddressServiceImp addressService;

	@Autowired
	SessionService session;

	@Value("${recaptcha.secret}")
	private String recapchaSecret;

	@Value("${recaptcha.url}")
	private String recapchaUrl;

	@Autowired
	private RestTemplate restTemplate;

	// Setup Recapcha
	private boolean verifyRecapcha(String recapcha) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret", recapchaSecret);
		map.add("response", recapcha);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ReCapchaResponse response = restTemplate.postForObject(recapchaUrl, request, ReCapchaResponse.class);
		response.isSuccess();
		return response.isSuccess();
	}

	// Login Page
	@GetMapping("/login.html")
	public String getLogin(Model m) {
		LoginForm login = new LoginForm();
		m.addAttribute("loginForm", login);
		return "user/home/login";
	}

	// @PostMapping("/login.html")
	// public String postLogin(@Valid @ModelAttribute("account") Accounts account,
	// BindingResult result, Model m,
	// HttpServletRequest request) {
	// // Xử lý lỗi nếu cần
	// if (result.hasFieldErrors("username") || result.hasFieldErrors("password")) {
	// // Xử lý lỗi nếu cần
	// }

	// String user = account.getUsername().trim();
	// String pass = account.getPassword().trim();
	// String gRecapcha = request.getParameter("g-recaptcha-response");
	// if (!verifyRecapcha(gRecapcha)) {
	// m.addAttribute("message", "3331");
	// } else {
	// Accounts acc = accountsService.findById(user);
	// if (acc != null) {
	// if (pass.equals(acc.getPassword())) {
	// if (acc.getActive()) {
	// session.set("account", acc);
	// m.addAttribute("message", "456");
	// return "redirect:/index.html";
	// } else {
	// m.addAttribute("message", "789");
	// }
	// } else {
	// m.addAttribute("message", "1011");
	// }
	// } else {
	// m.addAttribute("message", "1011");
	// }
	// }

	// return "user/home/login";
	// }

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

	@GetMapping("/compareEmail.html")
	public String compareEmail(Principal principal, Model model) {
		if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
			OAuth2User oauth2User = authenticationToken.getPrincipal();

			// Kiểm tra xem oauth2User có tồn tại không
			if (oauth2User != null) {
				String googleEmail = (String) oauth2User.getAttribute("email");
				Optional<Accounts> optionalAccount = accountsService.checkDuplicateEmail(googleEmail);
				Accounts account = optionalAccount.orElse(null);

				if (account != null) {
					if (account.getActive()) {
						session.set("account", account);
						return "redirect:/index.html";
					} else {
						model.addAttribute("message", "Tài khoản đã bị vô hiệu hóa");
					}
				} else {
					model.addAttribute("message", "Tài khoản không tồn tại hoặc đã bị vô hiệu hóa");
					return "redirect:/login.html";
				}
			} else {
				// Xử lý khi không tìm thấy thông tin người dùng
				model.addAttribute("message", "Không tìm thấy thông tin người dùng");
			}
		}
		return "user/home/login";
	}

}
