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

import dtn.asm.entity.Categories;
import dtn.asm.service.CategoriesService;

@CrossOrigin("*")
@RestController
public class CategoryRestController {

	@Autowired
	CategoriesService category;
	
	@GetMapping("/rest/categories")
	public ResponseEntity<List<Categories>> getAll(Model m){
		
		return ResponseEntity.ok(category.findAll());
	}
	
	@GetMapping("/rest/categories/{id}")
	public ResponseEntity<Categories> getOne(@PathVariable("id") Integer id){
		if(category.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(category.findById(id));
	}
	
	@GetMapping("/rest/categories/check/{id}")
	public Boolean checkProduct(@PathVariable("id") Integer id){
		return category.checkProduct(id);
	}
	
	@PostMapping("/rest/categories")
	public ResponseEntity<Categories> post(@RequestBody Categories cate){
//		if(category.findById(cate.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		category.create(cate);
		return ResponseEntity.ok(category.findById(cate.getId()));
	}
	
	@PutMapping("/rest/categories/{id}")
	public ResponseEntity<Categories> put(@PathVariable("id") Integer id,@RequestBody Categories cate){
		if(category.findById(cate.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		category.update(cate);
		return ResponseEntity.ok(cate);
	}
	@DeleteMapping("/rest/categories/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		if(category.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		category.delete(id);
		return ResponseEntity.ok().build();
	}

}
