package j6.asm.controller.user;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import j6.asm.entity.Accounts;
import j6.asm.entity.ReCapchaResponse;
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

	@GetMapping("/signin.html")
	public String signin(Accounts accounts, Model model) {
		model.addAttribute("account", accounts);
		return "/user/home/signin";
	}

	// @PostMapping("/signin.html")
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
				String sub = (String) oauth2User.getAttribute("sub");
				String picture = (String) oauth2User.getAttribute("picture");
				Optional<Accounts> optionalAccount = accountsService.checkDuplicateEmail(googleEmail);
				Accounts account = optionalAccount.orElse(null);

				System.out.println(googleEmail + "----------------------");
				System.out.println(usernameString + "----------------------");
				System.out.println(sub + "----------------------");
				System.out.println(oauth2User);
				// System.out.println(account.getAuthorities()+" cái gì đây");
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
				
//					accountsService1.create(newAccount);

					// // Tạo đối tượng AuthenTication từ UserDetails
					Authentication auth = new UsernamePasswordAuthenticationToken(newAccount, null,
							newAccount.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
					System.out.println("Dô đây ròi");
					System.out.println(auth.getAuthorities());
					// Lưu tài khoản mới vào cơ sở dữ liệu
					Accounts accounts = new Accounts();
					accounts.setUsername(generateRandomUsername()); // 
					accounts.setActive(true);
					accounts.setFullname(usernameString);
					accounts.setPassword(generateRandomPassword());
					accounts.setEmail(googleEmail);
					accounts.setPhoto(picture);
					accountsService1.create(accounts);
					System.out.println("Nè "+accounts);
      				session.set( "account", accounts); // Sử dụng newAccount thay vì account
					return "redirect:/index.html";
				}
			} else {
				// Xử lý khi không tìm thấy thông tin người dùng
				model.addAttribute("message", "Không tìm thấy thông tin người dùng");
			}
		}
		return "user/home/signin";
	}

	private static String generateRandomPassword() {
		Random random = new Random();
		int min = 100000; // Số nhỏ nhất có 6 chữ số
		int max = 999999; // Số lớn nhất có 6 chữ số
		int randomNumber = random.nextInt(max - min + 1) + min;
		return String.valueOf(randomNumber);
	}

	private static String generateRandomUsername() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String currentTime = LocalDateTime.now().format(formatter);

		Random random = new Random();
		int randomNumber = random.nextInt(10000); // Số ngẫu nhiên từ 0 đến 9999
		return "user" + currentTime + randomNumber;
	}
}