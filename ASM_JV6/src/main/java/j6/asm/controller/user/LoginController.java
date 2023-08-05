package j6.asm.controller.user;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.nimbusds.oauth2.sdk.Role;

import j6.asm.entity.ReCapchaResponse;
import j6.asm.dao.AccountDAO;
import j6.asm.entity.*;
import j6.asm.entity.Authorities;
import j6.asm.model.LoginForm;
import j6.asm.service.AccountsService;
import j6.asm.service.RolesService;
import j6.asm.service.SessionService;
import j6.asm.service.impl.AccountServiceImpl;
import j6.asm.service.impl.AddressServiceImp;

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
	RolesService roleSer;

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
//	@GetMapping("/login.html")
//	public String getLogin(Model m) {
//		LoginForm login = new LoginForm();
//		m.addAttribute("loginForm", login);
//		return "user/home/login";
//	}

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

	// public String postLogin(Model m, @Valid @ModelAttribute("loginForm")
	// LoginForm login, Errors errors) {
	// if (!errors.hasErrors()) {
	// String user = login.getUsername();
	// String pass = login.getPass();

	// Accounts acc = new Accounts();
	// acc = accountsService.findById(user);

	// if (acc instanceof Accounts) {
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
	// } else {
	// m.addAttribute("message", "123");
	// }
	// return "user/home/login";

	// }

	@GetMapping("/signin.html")
	public String signin(Accounts accounts, Model model) {
		model.addAttribute("account", accounts);
		return "/user/home/signin";
	}

//	 @PostMapping("/signin.html")
	public String sigin_success(@Valid @ModelAttribute("account") Accounts account, BindingResult result, Model model,
			HttpServletRequest request) {
		if (result.hasFieldErrors("username") || result.hasFieldErrors("password")) {
			return "/user/home/signin";
		}

		String username = account.getUsername().trim();
		String password = account.getPassword().trim();
		Accounts acc = accountsService.findById(username);
		if (acc instanceof Accounts) {
			if (password.equals(acc.getPassword())) {
				if (acc.getActive()) {
					session.set("account", acc);
					System.out.println("ĐĂNG NHẬP THÀNH CÔNG");
					model.addAttribute("message", "OKe");
					return "redirect:/index.html";
				} else {
					System.out.println("Tài Khoản bị khóa mẹ ròi còn đâu");
					model.addAttribute("error", "Tài Khoản bị khóa mẹ ròi còn đâu");
				}
			} else {
				System.out.println("Tài khoản hoặc mật khẩu bị sai");
				model.addAttribute("error", "Tài khoản hoặc mật khẩu bị sai");
			}
		} else {
			System.out.println("Tài khoản hoặc mật khẩu bị sai");
			model.addAttribute("error", "Tài khoản hoặc mật khẩu bị sai");
		}

		return "/user/home/signin";
	}

	@GetMapping("/compareEmail.html")
	public String compareEmail(Principal principal, Model model) {
		if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
			OAuth2User oauth2User = authenticationToken.getPrincipal();
			String fullname = authenticationToken.getPrincipal().getAttribute("name");
			// Kiểm tra xem oauth2User có tồn tại không
			if (oauth2User != null) {
				String googleEmail = (String) oauth2User.getAttribute("email");
				String usernameString = (String) oauth2User.getAttribute("name");
				Optional<Accounts> optionalAccount = accountsService.checkDuplicateEmail(googleEmail);
				Accounts account = optionalAccount.orElse(null);

				System.out.println(googleEmail + "----------------------");
				System.out.println(usernameString + "----------------------");
//				System.out.println(account.getUsername() + "+++++++++++=");
//				System.out.println(account.getAuthorities()+" cái gì đây");
//			
				if (account != null) {
					account = accountsService.findById(account.getUsername());
					if (account.getActive()) {
						System.out.println("Oke");
						session.set("account", account);
						return "redirect:/index.html";
					} else {
						model.addAttribute("message", "Tài khoản đã bị vô hiệu hóa");
					}
				} else {
					UserDetails newAccount = User.withUsername(googleEmail).password("123").roles("CUST").build();
					// Lưu tài khoản mới vào cơ sở dữ liệu
//					accountsService1.create(newAccount);

					// // Tạo đối tượng AuthenTication từ UserDetails
					Authentication auth = new UsernamePasswordAuthenticationToken(newAccount, null,
							newAccount.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
					System.out.println("Dô đây ròi");
					System.out.println(auth.getAuthorities());
					Accounts accounts = new Accounts();
					accounts.setUsername(googleEmail);
					accounts.setActive(true);
					accounts.setFullname(usernameString);
					accounts.setPassword("123");
					accounts.setEmail(googleEmail);
					// Lấy vai trò "CUST" từ cơ sở dữ liệu
//					Roles custRole = roleSer.findById("CUST");
//					System.out.println(custRole + "iaaaaaaaaa");
//					// Gán vai trò "CUST" cho tài khoản mới thông qua đối tượng Authorities
					Authorities authority = new Authorities();

//					accounts.setAuthorities(Collections.singletonList(authority));
					accountsService1.create(accounts);
//					session.set("account", accounts); // Sử dụng newAccount thay vì account

					// model.addAttribute("message", "Tài khoản không tồn tại hoặc đã bị vô hiệu
					// hóa");
					return "redirect:/index.html";
				}
			} else {
				// Xử lý khi không tìm thấy thông tin người dùng
				model.addAttribute("message", "Không tìm thấy thông tin người dùng");
			}
		}
		return "user/home/login";
	}

}
