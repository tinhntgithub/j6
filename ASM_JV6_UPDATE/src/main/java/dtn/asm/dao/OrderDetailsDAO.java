package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Color;
import dtn.asm.entity.OrderDetails;
import dtn.asm.entity.Orders;
import dtn.asm.entity.ProductColor;
import dtn.asm.entity.Products;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Integer> {

	List<OrderDetails> findByProductsId(Products productsId);
	
	List<OrderDetails> findByOrdersId(Orders ordersId);
	
	List<OrderDetails> findByColorId(ProductColor colorId);
	
	@Query("SELECT sum(o.qty*o.price) FROM OrderDetails o WHERE o.ordersId.id = ?1")
	Double getAmount(Integer id);
	
	@Query("SELECT sum(o.qty*o.price) FROM OrderDetails o WHERE o.ordersId.statusId.id = 3")
	Double getTotal();
	
	@Query("SELECT o.productsId FROM OrderDetails o WHERE o.id = ?1")
	List<Products> getAllProduct(Integer id);
	
	@Query (value = "select * from OrderDetails a where a.OrderId = ?1",nativeQuery = true)
	List<OrderDetails> find_Order_details(int orderid);
}
