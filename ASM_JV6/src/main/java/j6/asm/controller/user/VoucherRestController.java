package j6.asm.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import j6.asm.entity.Sale;
import j6.asm.service.VoucherServive;

@CrossOrigin("*")
@RestController
@RequestMapping("/cart")

public class VoucherRestController {
	@Autowired
	VoucherServive voucherservive;

	@GetMapping("/{code}")
	public ResponseEntity<?> apply(@PathVariable("code") String code) {
		return voucherservive.apply(code);
	}

}
