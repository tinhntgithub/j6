package dtn.asm.controller.admin;

import java.util.List;

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

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Authorities;
import dtn.asm.entity.Roles;
import dtn.asm.service.AccountsService;
import dtn.asm.service.AuthoritiesService;
import dtn.asm.service.RolesService;

@CrossOrigin("*")
@RestController
public class AuthRestController {

	@Autowired
	AuthoritiesService authService;
	@Autowired
	RolesService roles;
	@Autowired
	AccountsService accountService;

	@GetMapping("/rest/auths")
	public ResponseEntity<List<Authorities>> getAll(Model m) {

		return ResponseEntity.ok(authService.findAll());
	}

	@GetMapping("/rest/auths/{id}")
	public ResponseEntity<Authorities> getOne(@PathVariable("id") Integer id) {
		if (authService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(authService.findById(id));
	}

	@GetMapping("/rest/auths/check/{id}")
	public Boolean checkAuth(@PathVariable("id") Integer id) {
		return authService.checkAuth(id);
	}
	@GetMapping("/rest/auths/check2/{id}/{acc}")
	public Boolean checkAuth2(@PathVariable("id") String id,@PathVariable("acc") String username) {
		return authService.checkAuth2(id,username);
	}

	@PutMapping("/rest/auths/{id}")
	public ResponseEntity<Authorities> put(@PathVariable("id") String id,@RequestBody Accounts data) {
		Roles role = roles.findById(id);
		Authorities auth = new Authorities();

		auth.setRoleId(role);
		auth.setUserAuthor(data);

		authService.update(auth);
		return ResponseEntity.ok(auth);
	}

	@DeleteMapping("/rest/auths/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (authService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		authService.delete(id);
		return ResponseEntity.ok().build();
	}

}
