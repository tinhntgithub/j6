package j6.asm.controller.user;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import j6.asm.dao.AccountDAO;
import j6.asm.dao.AddressDAO;
import j6.asm.dao.AuthoritiesDAO;
import j6.asm.dao.RolesDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.entity.Authorities;
import j6.asm.entity.Roles;
import j6.asm.model.ChangePassForm;
import j6.asm.model.LoginForm;
import j6.asm.model.SignUpForm;
import j6.asm.model.UpdateAccountsForm;
import j6.asm.service.AddressService;
import j6.asm.service.SessionService;
import j6.asm.service.impl.AccountServiceImpl;
import j6.asm.service.impl.AddressServiceImp;

@Controller
public class AccountController {

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Autowired
	AccountDAO accountdao;

	@Autowired
	AuthoritiesDAO authdao;

	@Autowired
	RolesDAO rolesdao;

	@Autowired
	AccountServiceImpl accountsService;

	@Autowired
	SessionService session;

	// Register page
	@GetMapping("/register.html")
	public String register(Model m, @ModelAttribute("signUpForm") SignUpForm signUp) {
		signUp = new SignUpForm();
		return "user/home/register";
	}

	// @RequestMapping("/logout.html")
	public String logout() {
		session.remove("account");
		return "redirect:/index.html";
	}

	@RequestMapping("/sendOTPToMail/{email}/{otp}")
	@ResponseBody
	public String sendOTPToMail(@PathVariable("email") String email1, @PathVariable("otp") String otp1, Model model) {
		String email = email1;
		System.out.println(email);
		String otp = otp1;
		System.out.println(otp);

		// Xử lý gửi OTP qua email ở đây
		Properties pros = new Properties();
		pros.setProperty("mail.smtp.auth", "true");
		pros.setProperty("mail.smtp.starttls.enable", "true");
		pros.setProperty("mail.smtp.host", "smtp.gmail.com");
		pros.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		pros.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		pros.setProperty("mail.smtp.port", "587");

		Session session = Session.getInstance(pros, new Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				String username = "dtndatn2022@gmail.com";
				String password = "qdaggkewjaejnuxo";
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			Multipart multipart = new MimeMultipart();
			MimeBodyPart bodytext = new MimeBodyPart();
			bodytext.setContent(getHTMLT(otp, "Mã otp của bạn là !"), "text/html;charset=utf-8");
			multipart.addBodyPart(bodytext);

			MimeMessage mess = new MimeMessage(session);
			mess.setFrom(new InternetAddress("phuchtpc01818@fpt.edu.vn"));
			// mess.setRecipients(Message.RecipientType.TO, req.getParameter("email"));
			mess.setRecipients(Message.RecipientType.TO, email);
			mess.setSubject("Mã otp đăng ký", "utf-8");
			mess.setReplyTo(mess.getFrom());
			mess.setContent(multipart);
			Transport.send(mess);
			model.addAttribute("mess", "OTP đã được gửi đến email");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "Success";
	}

	@PostMapping("/register.html")
	public String regiterUpdate(Model m, @Valid @ModelAttribute("signUpForm") SignUpForm signUp, Errors errors) {

		Optional<Accounts> account = accountdao.findById(signUp.getUsername());
		String account_email = accountdao.findEmail(signUp.getEmail());
		String account_phone = accountdao.findPhone(signUp.getPhone());
		int loi = 0;
		if (errors.hasErrors()) {
			loi++;
		}
		if (account.isPresent()) {
			loi++;
			m.addAttribute("mess1", "Tên đăng nhập đã được sử dụng");
		}
		if (account_email != null) {
			m.addAttribute("mess2", "Email đã được sử dụng");
			loi++;
		}
		if (account_phone != null) {
			m.addAttribute("mess3", "Số điện thoại đã được sử dụng");
		}
		String sdt = signUp.getPhone();
		String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
		if (!sdt.matches(reg)) {
			m.addAttribute("mess5", "Số điện thoại không hợp lệ");
			loi++;
		}
		if (!signUp.getPassword().equals(signUp.getPassword2())) {
			m.addAttribute("mess4", "Vui lòng kiểm tra lại mật khẩu");
			loi++;
		}
		if (signUp.getCheck() == null) {
			System.out.println(signUp.getCheck());
			loi++;
		}
		if (loi == 0) {
			Accounts acc = new Accounts();
			acc.setUsername(signUp.getUsername());
			acc.setFullname(signUp.getFullname());
			acc.setPhone(signUp.getPhone());
			acc.setPassword(signUp.getPassword());
			acc.setPhoto("default.jpg");
			acc.setEmail(signUp.getEmail());
			accountdao.save(acc);

			Roles roles = rolesdao.getById("CUST");

			Authorities auth = new Authorities();
			auth.setRoleId(roles);
			auth.setUserAuthor(acc);
			authdao.save(auth);

			m.addAttribute("mess6", "Đăng ký thành công");
		}
		return "user/home/register";
	}

	// Change Pass page
	@GetMapping("/changepass.html")
	public String changepass(@ModelAttribute("changePassForm") ChangePassForm changepass) {
		return "user/home/changepass";
	}

	@PostMapping("/changepass.html")
	public String changepass_Post(Model m, @Valid @ModelAttribute("changePassForm") ChangePassForm changepass,
			Errors errors) {
		Accounts account = session.get("account");
		String password = changepass.getPassword();
		String newpassword = changepass.getNewpassword();
		String confirmpassword = changepass.getConfirmpassword();
		int loi = 0;
		if (!password.equals(account.getPassword())) {
			m.addAttribute("mess1", "Mật khẩu không đúng");
			loi++;
		}
		if (!newpassword.equals(confirmpassword)) {
			m.addAttribute("mess2", "Vui lòng kiểm tra lại mật khẩu mới");
			loi++;
		}
		if (loi == 0) {
			Accounts acc = accountdao.getById(account.getUsername());
			acc.setPassword(confirmpassword);
			accountdao.save(acc);
			m.addAttribute("mess", "Đổi mật khẩu thành công");
		}
		return "user/home/changepass";
	}

	// Forgot password page
	@GetMapping("/forgot-password.html")
	public String forgot_password() {
		return "user/home/forgot-password";
	}

	@PostMapping("/forgot-password.html")
	public String forgot_password_post(Model m) {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		Accounts acc = session.get("account");
		if (acc != null) {
			username = acc.getUsername();
		}
		try {
			Accounts account = accountdao.getById(username);
			if (account.getUsername().equals(username) && account.getEmail().equals(email)) {
				String pass = account.getPassword();
				// Gửi mật khẩu qua mail
				Properties pros = new Properties();
				pros.setProperty("mail.smtp.auth", "true");
				pros.setProperty("mail.smtp.starttls.enable", "true");
				pros.setProperty("mail.smtp.host", "smtp.gmail.com");
				pros.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
				pros.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
				pros.setProperty("mail.smtp.port", "587");
				// Kết nối
				Session session = Session.getInstance(pros, new Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						String username = "dtndatn2022@gmail.com";
						String password = "qdaggkewjaejnuxo";
						return new javax.mail.PasswordAuthentication(username, password);
					}
				});
				try {
					Multipart multipart = new MimeMultipart();
					MimeBodyPart bodytext = new MimeBodyPart();
					bodytext.setContent(getHTMLT(pass, "Mật khẩu của bạn là !"), "text/html; charset=utf-8");
					multipart.addBodyPart(bodytext);

					MimeMessage mess = new MimeMessage(session);
					mess.setFrom(new InternetAddress("phuchtpc01818@fpt.edu.vn"));
					mess.setRecipients(Message.RecipientType.TO, req.getParameter("email"));
					mess.setSubject("PASSWORD RETRIEVAL", "utf-8");
					mess.setReplyTo(mess.getFrom());
					mess.setContent(multipart);
					Transport.send(mess);
					m.addAttribute("mess", "Mật khẩu đã được gửi đến email của bạn");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (!account.getEmail().equals(email)) {
					m.addAttribute("errEmail", "Mail không đúng");
				}
				if (account.getUsername().equals(username)) {
					m.addAttribute("errUser", "Người dùng này không tồn tại");
				}

			}
		} catch (EntityNotFoundException e) {
			m.addAttribute("errUser", "Người dùng này không tồn tại");
		}
		return "user/home/forgot-password";
	}

	// @GetMapping("/otp.html")
	// public String otp() {
	// String otp = session.get("otpCode");
	// if(otp==null) {
	// return "redirect:/index.html";
	// }
	// return "user/home/otp";
	// }

	public String getHTMLT(String code, String message) {
		String html = "<!DOCTYPE html\r\n"
				+ "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + "\r\n" + "<head>\r\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
				+ "    <title>J6Team</title>\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n" + "</head>\r\n"
				+ "\r\n" + "<body style=\"margin: 0; padding: 0;\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"800\" style=\"border: 1px solid #cccccc;\">\r\n"
				+ "        <tr>\r\n" + "            <td align=\"center\"\r\n"
				+ "                style=\"padding: 10px 0 30px 0;background: url('https://lh3.googleusercontent.com/kwFhfKq_9afZ8_tGqSNozfp_DrYyzLikHy9xtC4kiiqylGWv7n_5UJA7yQ3W18xTmw=h500');background-size: cover;\">\r\n"
				+ "                <h3 style=\"color: #ffffff; font-family: Arial, sans-serif; font-size:50px;\">\r\n"
				+ "                    J6Team\r\n" + "                </h3>\r\n" + "            </td>\r\n"
				+ "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "                    <tr align=\"center\">\r\n"
				+ "                        <td style=\"color: #153643; font-family: Arial, sans-serif; font-size:18px;\">\r\n"
				+ "                            <b> " + message + "</b>\r\n" + "                        </td>\r\n"
				+ "                    </tr>\r\n" + "                    <tr>\r\n"
				+ "                        <td style=\"padding: 20px 0 30px 0;\">\r\n"
				+ "                        </td>\r\n" + "                    </tr>\r\n" + "                    <tr>\r\n"
				+ "                        <td>\r\n"
				+ "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td width=\"100%\" valign=\"top\">\r\n"
				+ "                                        <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "                                            <tr align=\"center\">\r\n"
				+ "                                                <td\r\n"
				+ "                                                    style=\"color: #153643; font-family: Arial, sans-serif; font-size: 30px;\">\r\n"
				+ "                                                    <h1>" + code + "</h1>\r\n"
				+ "                                                </td>\r\n"
				+ "                                            </tr>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </table>\r\n" + "                        </td>\r\n"
				+ "                    </tr>\r\n" + "                    <tr align=\"center\">\r\n"
				+ "                        <td style=\"padding: 40px 0 30px 0;\">\r\n"
				+ "                            <a href=\"http://localhost:8080/signin.html"
				+ "\" style=\"background-color: rgb(241, 75, 75);color:white;\r\n"
				+ "                                text-decoration: none;padding: 15px 50px 15px 50px;border-radius: 30px;\r\n"
				+ "                                box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;                                \r\n"
				+ "                                \" class=\"dtn\">\r\n"
				+ "                                Đăng nhập ngay\r\n" + "                            </a>\r\n"
				+ "                        </td>\r\n" + "                    </tr>\r\n" + "                </table>\r\n"
				+ "            </td>\r\n" + "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td bgcolor=\"crimson\" style=\"padding: 30px 30px 30px 30px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\r\n"
				+ "                            &reg; J6Team, @Gemlove<br />\r\n"
				+ "                            <a href=\"http://localhost:8080/register.html\" style=\"color: #ffffff;\">\r\n"
				+ "                                <font color=\"#ffffff\">Đăng ký</font>\r\n"
				+ "                            </a> để nhận nhiều ưu đãi hấp dẫn từ J6Team.\r\n"
				+ "                        </td>\r\n" + "                        <td align=\"right\">\r\n" + "\r\n"
				+ "                        </td>\r\n" + "                    </tr>\r\n" + "                </table>\r\n"
				+ "            </td>\r\n" + "        </tr>\r\n" + "    </table>\r\n" + "</body>\r\n" + "\r\n"
				+ "</html>";
		return html;
	}

}