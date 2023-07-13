package dtn.asm.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dtn.asm.dao.AccountDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.service.AccountsService;
import dtn.asm.service.SessionService;

@CrossOrigin("*")
@RestController
public class AccountsRestController {

	@Autowired
	AccountsService account;
	@Autowired
	SessionService session;
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	HttpServletRequest request;

	@GetMapping("/rest/accounts")
	public ResponseEntity<List<Accounts>> getAll(Model m) {
		return ResponseEntity.ok(account.findAll());
	}

	@GetMapping("/rest/accounts/{id}")
	public ResponseEntity<Accounts> getOne(@PathVariable("id") String id) {
		if (account.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(account.findById(id));
	}

	@GetMapping("/rest/accounts/check/{id}")
	public Boolean check(@PathVariable("id") String id) {
		return account.check(id);
	}

	@GetMapping("/rest/accounts/checkUser/{id}")
	public Boolean checkUser(@PathVariable("id") String id) {
		return account.checkUsername(id);
	}

	@GetMapping("/rest/accounts/checkEmail/{email}")
	public Boolean checkEmail(@PathVariable("email") String email) {
		return account.checkEmail(email);
	}

	@GetMapping("/rest/accounts/checkPhone/{phone}")
	public Boolean checkPhone(@PathVariable("phone") String phone) {
		return account.checkPhone(phone);
	}

	@PostMapping("/rest/accounts")
	public ResponseEntity<Accounts> post(@RequestBody Accounts data) {
//		if(account.findById(data.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		if (data.getPhoto() == null) {
			data.setPhoto("default.jpg");
		}
		account.create(data);
		return ResponseEntity.ok(account.findById(data.getUsername()));
	}

	@PutMapping("/rest/accounts/{id}")
	public ResponseEntity<Accounts> put(@PathVariable("id") String id, @RequestBody Accounts data) {
		if (account.findById(data.getUsername()) == null) {
			return ResponseEntity.notFound().build();
		}
		account.update(data);
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/rest/accounts/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		if (account.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		account.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/rest/fullname")
	public JsonNode fullname() {
		Accounts acc = accountDAO.findById(request.getUserPrincipal().getName()).get();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode obj = mapper.createObjectNode();
		obj.put("fullname", acc.getFullname());
		obj.put("photo", acc.getPhoto());
		return obj;
	}

}
