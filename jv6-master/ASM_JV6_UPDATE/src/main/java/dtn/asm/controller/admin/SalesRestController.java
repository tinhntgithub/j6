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

import dtn.asm.entity.Sale;
import dtn.asm.service.SaleService;

@CrossOrigin("*")
@RestController
public class SalesRestController {

	@Autowired
	SaleService sale;

	@GetMapping("/rest/sales")
	public ResponseEntity<List<Sale>> getAll(Model m) {

		return ResponseEntity.ok(sale.findAll());
	}

	@GetMapping("/rest/sales/{id}")
	public ResponseEntity<Sale> getOne(@PathVariable("id") Integer id) {
		if (sale.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(sale.findById(id));
	}

	@GetMapping("/rest/sales/check/{id}")
	public Boolean checkOrder(@PathVariable("id") Integer id) {
		return sale.checkOrder(id);
	}

	@PostMapping("/rest/sales")
	public ResponseEntity<Sale> post(@RequestBody Sale data) {
//		if(sale.findById(data.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		sale.create(data);
		return ResponseEntity.ok(sale.findById(data.getId()));
	}

	@PutMapping("/rest/sales/{id}")
	public ResponseEntity<Sale> put(@PathVariable("id") Integer id, @RequestBody Sale data) {
		if (sale.findById(data.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		sale.update(data);
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/rest/sales/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (sale.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		sale.delete(id);
		return ResponseEntity.ok().build();
	}

}
