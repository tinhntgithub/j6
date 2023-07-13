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

import dtn.asm.service.ColorService;
import dtn.asm.entity.Color;

@CrossOrigin("*")
@RestController
public class ColorRestController {

	@Autowired
	ColorService color;

	@GetMapping("/rest/colors")
	public ResponseEntity<List<Color>> getAll(Model m) {

		return ResponseEntity.ok(color.findAll());
	}

	@GetMapping("/rest/colors/{id}")
	public ResponseEntity<Color> getOne(@PathVariable("id") Integer id) {
		if (color.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(color.findById(id));
	}

	 @GetMapping("/rest/colors/check/{id}")
	public Boolean checkProduct(@PathVariable("id") Integer id){
		return color.checkProduct(id);
	}

	@PostMapping("/rest/colors")
	public ResponseEntity<Color> post(@RequestBody Color data) {
//		if(color.findById(data.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		color.create(data);
		return ResponseEntity.ok(color.findById(data.getId()));
	}

	@PutMapping("/rest/colors/{id}")
	public ResponseEntity<Color> put(@PathVariable("id") Integer id, @RequestBody Color data) {
		if (color.findById(data.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		color.update(data);
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/rest/colors/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (color.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		color.delete(id);
		return ResponseEntity.ok().build();
	}

}
