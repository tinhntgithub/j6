package j6.asm.service;

import org.springframework.http.ResponseEntity;

import j6.asm.entity.Sale;


public interface VoucherServive {
	  ResponseEntity<?>  apply(String code);
}
