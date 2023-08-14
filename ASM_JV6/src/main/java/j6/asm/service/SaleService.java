package j6.asm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import j6.asm.entity.Sale;

public interface SaleService {
	List<Sale> findAll();

	Sale findById(Integer id);

	void create(Sale entity);

	void update(Sale entity);

	void delete(Integer id);

	Boolean checkOrder(Integer id);

	Integer getCount();

	Optional<List<Sale>> findByCode(String code);

	void applyDiscount(Integer code); // Trừ số lượng mã giảm giá khi sử dụng

	Sale save(Sale sale);
	
}
