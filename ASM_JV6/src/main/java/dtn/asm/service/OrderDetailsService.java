package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.OrderDetails;
import dtn.asm.entity.Orders;
import dtn.asm.entity.Products;

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
