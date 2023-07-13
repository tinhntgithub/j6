package dtn.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dtn.asm.entity.PriceHistory;
import dtn.asm.service.PriceHistoryService;

@CrossOrigin("*")
@RestController
public class HistorySaleRestController {

	@Autowired
	PriceHistoryService priceService;
	
	@GetMapping("/rest/pricehistory")
	public ResponseEntity<List<PriceHistory>> getAll(Model m) {
		return ResponseEntity.ok(priceService.findAll());
	}

}
