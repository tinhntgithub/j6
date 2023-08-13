package j6.asm.controller.admin;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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

import j6.asm.dao.SaleDAO;
import j6.asm.entity.Sale;
import j6.asm.message.ResponseMessageError;
import j6.asm.service.SaleService;

@CrossOrigin("*")
@RestController
public class SalesRestController {

	@Autowired
	SaleService sale;
	@Autowired
	SaleDAO dao;

	@GetMapping("/rest/sales")
	public ResponseEntity<List<Sale>> getAll(Model m) {

		return ResponseEntity.ok(sale.findAll());
	}

	@GetMapping("/rest/sales/checkcode/{code}")
	public ResponseEntity<?> getByCOde(@PathVariable("code") String code, Model m) {
		Optional<List<Sale>> list = sale.findByCode(code);
		//Optional<List<Sale>> list1 = sale.discountcode(code);
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		System.out.println(cld.getTime().getTime());
		if (list.isPresent()) {
			Sale sale2 = list.get().get(0);
			System.out.println(sale2.getCreateDate().getTime());
			if (sale2.getEndDate().getTime() < cld.getTime().getTime()) {
				System.err.println("MÃ GIÃM GIÁ ĐÃ HẾT HẠN");
				return ResponseEntity.badRequest()
						.body(new ResponseMessageError("MÃ GIÃM GIÁ ĐÃ HẾT HẠN!", "coupon_code"));
			}
			if (sale2.getAmountused() == 0) {
				System.err.println("SỐ LƯỢNG CỦA MÃ GIẢM GIÁ ĐÃ HẾT");
				return ResponseEntity.badRequest()
						.body(new ResponseMessageError("SỐ LƯỢNG CỦA MÃ GIẢM GIÁ ĐÃ HẾT!", "coupon_code"));
			}
			if (sale2.getAmount() < sale2.getAmountused()) {
				System.err.println("SỐ LƯỢNG CỦA MÃ GIẢM GIÁ ĐÃ NHẬP HẾT");
				return ResponseEntity.badRequest()
						.body(new ResponseMessageError("SỐ LƯỢNG CỦA MÃ GIẢM GIÁ ĐÃ NHẬP HẾT!", "coupon_code"));
			}
			
			return ResponseEntity.ok(sale2);
		} else {
			return ResponseEntity.badRequest().body(new ResponseMessageError("MÃ GIÃM GIÁ KHÔNG ĐÚNG!", "coupon_code"));
		}

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
