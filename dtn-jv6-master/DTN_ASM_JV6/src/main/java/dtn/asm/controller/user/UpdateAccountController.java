package dtn.asm.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import dtn.asm.dao.AddressDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.entity.Address;
import dtn.asm.model.UpdateAccountsForm;
import dtn.asm.service.SessionService;
import dtn.asm.service.impl.AccountServiceImpl;
import dtn.asm.service.impl.AddressServiceImp;

@Controller
public class UpdateAccountController {
	@Autowired
	AddressDAO addressDAO;

	@Autowired
	AddressServiceImp addressService;

	@Autowired
	SessionService session;

	@Autowired
	AccountServiceImpl accountsService;
	@Autowired
	ServletContext context;

//	Update account page
	@GetMapping("/update_account.html")
	public String getUpdateAccount(Model m, @ModelAttribute("updateAccount") UpdateAccountsForm form,
			@RequestParam("tb") Optional<String> tb) {
		Accounts acc = session.get("account");
		if (acc == null) {
			return "redirect:/index.html?error=NotLogin";
		}
		if (tb.isPresent()) {
			if (tb.get().contains("success")) {
				if (tb.get().contains("CreateAdr")) {
					m.addAttribute("alert", "alert-success");
					m.addAttribute("messageAdr", "Thêm địa chỉ thành công");
				} else if (tb.get().contains("Update")) {
					m.addAttribute("alert", "alert-success");
					m.addAttribute("messageAdr", "Cập nhật địa chỉ thành công");
				} else {
					m.addAttribute("alert", "alert-success");
					m.addAttribute("messageAdr", "Xóa địa chỉ thành công");
				}
			} else if (tb.get().contains("error")) {
				if (tb.get().contains("Create")) {
					m.addAttribute("alert", "alert-danger");
					m.addAttribute("messageAdr", "Thêm địa chỉ thất bại do nhập vào sai cách !!!");
				} else if (tb.get().contains("Update")) {
					m.addAttribute("alert", "alert-danger");
					m.addAttribute("messageAdr", "Cập nhật địa chỉ thất bại do không tìm thấy hoặc bạn không nhập gì");
				} else {
					m.addAttribute("alert", "alert-danger");
					m.addAttribute("messageAdr", "Không tìm thấy địa chỉ cần xóa");
				}
			}
		}
		form.setFullname(acc.getFullname());
		form.setEmail(acc.getEmail());
		form.setPhone(acc.getPhone());
		form.setPhoto(acc.getPhoto());
		return "user/home/update-account";
	}

	@PostMapping("/update_account.html")
	public String postUpdateAccount(Model m, @Valid @ModelAttribute("updateAccount") UpdateAccountsForm form,
			Errors error) {
		if (!error.hasErrors()) {
			Accounts acc = session.get("account");
			acc.setFullname(form.getFullname());
			acc.setEmail(form.getEmail());
			acc.setPhone(form.getPhone());
			accountsService.update(acc);
			m.addAttribute("alert", "alert-success");
			m.addAttribute("message", "Cập nhật thông tin thànhh công");
		}
		return "user/home/update-account";
	}

	@RequestMapping("/update_account.html/create")
	public String postCreateAddress(Model m, @RequestParam("newAddress") Optional<String> address) {
		Accounts acc = session.get("account");
		if (address.isPresent() && acc != null) {
			Address adr = new Address();
			if (!address.get().isBlank()) {
				adr.setAddress(address.get());
			} else {
				return "redirect:/update_account.html?tb=errorCreateAdrNull#messageAdr";
			}
			adr.setUserAr(acc);
			addressService.create(adr);
		} else {
			return "redirect:/update_account.html#messageAdr";
		}

		return "redirect:/update_account.html?tb=successCreateAdr#messageAdr";
	}

	@RequestMapping("/update_account.html/update/{id}")
	public String updateDeleteAccount(Model m, @PathVariable("id") Optional<String> id,
			@RequestParam("editAddress") Optional<String> address) {
		if (id.isPresent()) {
			try {
				int ma = Integer.parseInt(id.get());
				Accounts acc = session.get("account");
				Optional<Address> adr = addressDAO.findById(ma);
				if (adr.isPresent() && adr.get().getUserAr().getUsername().equals(acc.getUsername())) {
					if (address.isPresent()) {
						if (!address.get().isBlank()) {
							adr.get().setAddress(address.get());
						} else {
							return "redirect:/update_account.html?tb=errorUpdateAdrNull#messageAdr";
						}
						adr.get().setUserAr(acc);
						addressService.update(adr.get());
						return "redirect:/update_account.html?tb=successUpdate#messageAdr";
					}
				} else {
					return "redirect:/update_account.html?tb=errorUpdateAdr#messageAdr";
				}
			} catch (NumberFormatException e) {
				return "redirect:/update_account.html?tb=errorUpdateAdr#messageAdr";
			}
		}
		return "redirect:/update_account.html?tb=errorUpdateAdr#messageAdr";
	}

	@RequestMapping("/update_account.html/delete/{id}")
	public String getDeleteAccount(Model m, @PathVariable("id") Optional<String> id) {
		if (id.isPresent()) {
			try {
				int ma = Integer.parseInt(id.get());
				Accounts acc = session.get("account");
				Optional<Address> adr = addressDAO.findById(ma);
				List<Address> list = (List<Address>) m.getAttribute("listAddress");
				if (adr.isPresent() && adr.get().getUserAr().getUsername().equals(acc.getUsername())) {
					if (list.size() == 1) {
						return "redirect:/update_account.html?tb=errorDeleteAdr#messageAdr";
					} else {
						addressService.delete(ma);
						return "redirect:/update_account.html?tb=successDelete#messageAdr";
					}
				} else {
					return "redirect:/update_account.html?tb=errorDeleteAdr#messageAdr";
				}
			} catch (NumberFormatException e) {
				return "redirect:/update_account.html?tb=errorDeleteAdr#messageAdr";
			}
		}
		return "redirect:/update_account.html?tb=errorDeleteAdr#messageAdr";
	}
	@ModelAttribute("listAddress")
	public List<Address> getListAddress() {
		Accounts acc = session.get("account");
		return addressDAO.findByUsername(acc, null);
		
	}

	
}
