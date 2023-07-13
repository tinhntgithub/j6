package dtn.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import dtn.asm.dao.OrdersDAO;
import dtn.asm.entity.OrderDetails;
import dtn.asm.entity.Orders;
import dtn.asm.entity.Products;
import dtn.asm.service.OrderDetailsService;
import dtn.asm.service.OrdersService;
import dtn.asm.service.StatusService;

@CrossOrigin("*")
@RestController
public class OrderRestController {

	@Autowired
	OrdersService orderService;
	@Autowired
	OrderDetailsService ordersService;
	@Autowired
	StatusService statusService;
	
//	Đơn hàng đang chờ Restful API
	@GetMapping("/rest/order-wait")
	public ResponseEntity<List<Orders>> getAll() {
		return ResponseEntity.ok(orderService.findOrderWait());
	}
//	Đơn hàng đang giao :))
	@GetMapping("/rest/order-delivered")
	public ResponseEntity<List<Orders>> getAllOrder2() {
		return ResponseEntity.ok(orderService.findAllOrderDelivired());
	}
//	Đơn hàng đã hủy
	@GetMapping("/rest/order-cancel")
	public ResponseEntity<List<Orders>> getAllOrder3(){
		return ResponseEntity.ok(orderService.findAllOrderCancel());
	}
//	Đơn hàng đã giao api restful ahihi :))
	@GetMapping("/rest/order-done")
	public ResponseEntity<List<Orders>> getAllOrder4(){
		return ResponseEntity.ok(orderService.findAllOrderDone());
	}
	
	@GetMapping("/rest/order/{id}")
	public ResponseEntity<Orders> getOrder(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(orderService.findById(id));
	}
	@PutMapping("/rest/order/{id}/{status}")
	public void updateOrders(@PathVariable("id") Integer id,@PathVariable("status") Integer status) {
		Orders ord = orderService.findById(id);
		ord.setStatusId(statusService.findById(status));
		orderService.update(ord);
	}
	
	@GetMapping("/rest/order-details/{id}")
	public ResponseEntity<List<OrderDetails>> getOrderDetails(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ordersService.getOrderDetails(id));
	}
	@GetMapping("/rest/orders-details/{id}")
	public Double getAmount(@PathVariable("id") Integer id) {
		return ordersService.getAmount(id);
	}
	

}
