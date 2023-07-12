package dtn.asm.controller.admin;

import java.util.List;

import javax.websocket.server.PathParam;

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
import org.springframework.web.multipart.MultipartFile;

import dtn.asm.entity.Brand;
import dtn.asm.service.BrandService;
import dtn.asm.service.FileManagerService;

@CrossOrigin("*")
@RestController
public class BrandRestController {

	@Autowired
	BrandService brand;
	
	@GetMapping("/rest/brands")
	public ResponseEntity<List<Brand>> getAll(Model m) {
		return ResponseEntity.ok(brand.findAll());
	}

	@GetMapping("/rest/brands/{id}")
	public ResponseEntity<Brand> getOne(@PathVariable("id") Integer id) {
		if (brand.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(brand.findById(id));
	}

	@GetMapping("/rest/brands/check/{id}")
	public Boolean checkProduct(@PathVariable("id") Integer id) {
		return brand.checkProduct(id);
	}

	@PostMapping("/rest/brands")
	public ResponseEntity<Brand> post(@RequestBody Brand data) {
//		if(brand.findById(data.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		if(data.getLogo() == null) {
			data.setLogo("default.jpg");
		}
		brand.create(data);
		return ResponseEntity.ok(brand.findById(data.getId()));
	}

	@PutMapping("/rest/brands/{id}")
	public ResponseEntity<Brand> put(@PathVariable("id") Integer id, @RequestBody Brand data) {
		if (brand.findById(data.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		brand.update(data);
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/rest/brands/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (brand.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		brand.delete(id);
		return ResponseEntity.ok().build();
	}

}
