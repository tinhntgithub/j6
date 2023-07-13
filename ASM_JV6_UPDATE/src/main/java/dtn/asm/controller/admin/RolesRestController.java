package dtn.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dtn.asm.entity.Roles;
import dtn.asm.service.RolesService;

@CrossOrigin("*")
@RestController
public class RolesRestController {

	@Autowired
	RolesService rolesService;
	
	@GetMapping("/rest/roles")
	public ResponseEntity<List<Roles>> getAll() {
		return ResponseEntity.ok(rolesService.findAll());
	}

	@GetMapping("/rest/roles/{id}")
	public ResponseEntity<Roles> getOne(@PathVariable("id") String id) {
		return ResponseEntity.ok(rolesService.findById(id));
	}
}
