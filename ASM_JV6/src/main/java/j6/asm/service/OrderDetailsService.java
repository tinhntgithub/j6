package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.OrderDetails;
import j6.asm.entity.Orders;
import j6.asm.entity.Products;

public interface OrderDetailsService {
	List<OrderDetails> findAll();

	OrderDetails findById(Integer id);

	void create(OrderDetails entity);

	void update(OrderDetails entity);

	void delete(Integer id);
	
	List<OrderDetails> getOrderDetails(Integer id);
	
	Double getAmount(Integer id);
	
	Double getTotal();
	
	List<Products> getProduct(Integer id);
}
