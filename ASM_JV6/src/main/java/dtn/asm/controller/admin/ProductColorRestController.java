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

import dtn.asm.entity.Color;
import dtn.asm.entity.ProductColor;
import dtn.asm.entity.Products;
import dtn.asm.service.ProductColorService;
import dtn.asm.service.ProductsService;

@CrossOrigin("*")
@RestController
public class ProductColorRestController {

	@Autowired
	ProductColorService colorPr;
	@Autowired
	ProductsService products;

	@GetMapping("/rest/product_colors")
	public ResponseEntity<List<ProductColor>> getAll(Model m) {
		return ResponseEntity.ok(colorPr.findAll());
	}

	@GetMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> getOne(@PathVariable("id") Integer id) {
		if (colorPr.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(colorPr.findById(id));
	}

	@GetMapping("/rest/product_colors/check/{id}")
	public Boolean checkProduct(@PathVariable("id") Integer id) {
		return colorPr.checkOrder(id);
	}
	@GetMapping("/rest/product_colors/check2/{id}/{idd}")
	public Boolean checkProductColor(@PathVariable("id") Integer id,@PathVariable("idd") Integer idd) {
		return colorPr.checkExitsProduct(id, idd);
	}

	@PostMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> post(@PathVariable("id") Integer id, @RequestBody Color data) {
		Products pr = products.findById(id);
		ProductColor colorsPro = new ProductColor();
		colorsPro.setProducts(pr);
		colorsPro.setColor(data);
		colorPr.create(colorsPro);

		return ResponseEntity.ok(colorPr.findById(colorsPro.getId()));
	}

	@PutMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> put(@PathVariable("id") Integer id, @RequestBody Color data) {
		Products pr = products.findById(id);
		ProductColor colorsPro = new ProductColor();
		colorsPro.setProducts(pr);
		colorsPro.setColor(data);
		colorPr.create(colorsPro);

		return ResponseEntity.ok(colorPr.findById(colorsPro.getId()));
	}

	@DeleteMapping("/rest/product_colors/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (colorPr.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		colorPr.delete(id);
		return ResponseEntity.ok().build();
	}

}
