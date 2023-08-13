package j6.asm.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import j6.asm.dao.AddressDAO;
import j6.asm.dao.OrdersDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.dao.StatusDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.entity.Cart;
import j6.asm.entity.OrderDetails;
import j6.asm.entity.Orders;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;
import j6.asm.entity.Sale;
import j6.asm.entity.Status;
import j6.asm.service.AddressService;
import j6.asm.service.CartService;
import j6.asm.service.OrderDetailsService;
import j6.asm.service.OrdersService;
import j6.asm.service.ProductColorService;
import j6.asm.service.SaleService;
import j6.asm.service.SessionService;
import j6.asm.service.StatusService;

@CrossOrigin("*")
@RestController
public class OrderRestController {

	@Autowired
	OrdersService orderService;
	@Autowired
	OrderDetailsService ordersService;
	@Autowired
	StatusService statusService;
	@Autowired
	SessionService session;
	@Autowired
	AddressService addressDAO;
	@Autowired
	StatusService statusdao;
	@Autowired
	SaleService saleService;
	@Autowired
	CartService cartService;
	@Autowired
	ProductsDAO productsDAO;
	@Autowired
	StatusDAO statusDao;
	@Autowired
	ProductColorService productColorService;

	// Đơn hàng đang chờ Restful API
	@GetMapping("/rest/order-wait")
	public ResponseEntity<List<Orders>> getAll() {
		return ResponseEntity.ok(orderService.findOrderWait());
	}

	// Đơn hàng đang giao :))
	@GetMapping("/rest/order-delivered")
	public ResponseEntity<List<Orders>> getAllOrder2() {
		return ResponseEntity.ok(orderService.findAllOrderDelivired());
	}

	// Đơn hàng đã hủy
	@GetMapping("/rest/order-cancel")
	public ResponseEntity<List<Orders>> getAllOrder3() {
		return ResponseEntity.ok(orderService.findAllOrderCancel());
	}

	// Đơn hàng đã giao api restful ahihi :))
	@GetMapping("/rest/order-done")
	public ResponseEntity<List<Orders>> getAllOrder4() {
		return ResponseEntity.ok(orderService.findAllOrderDone());
	}

	@GetMapping("/rest/order/{id}")
	public ResponseEntity<Orders> getOrder(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(orderService.findById(id));
	}

	@PutMapping("/rest/order/{id}/{status}")
	public void updateOrders(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
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

	@PostMapping("/rest/order/neworder")
	public ResponseEntity<Orders> newOrder(@RequestBody JsonNode data) throws Exception {
		Accounts account = session.get("account");
		List<OrderDetails> listOrderDetails = new ArrayList<>();

		String address;
		JsonNode addNode = data.get("address");

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
		String dateString = now.format(formatter);
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSS");
		Date date = inputFormat.parse(dateString);

		Integer statusid = 1;
		Status status = statusService.findById(statusid);

		String fullname = data.get("fullname").asText();
		String phone = data.get("phone").asText();
		List<Cart> cartList = new ArrayList<Cart>();

		JsonNode listNode = data.get("list");
		if (listNode != null && listNode.isArray()) {
			for (JsonNode node : listNode) {
				Integer cartid = node.get("id").asInt();
				Cart cart = cartService.findById(cartid);
				cartList.add(cart);
			}
		}

		Integer saleid = data.get("voucher").asInt();

		System.err.println("Lấy Mã voucher: " + saleid);
		Sale check = saleService.findById(saleid);
		if (check == null) {
			System.err.println("Không thấy bên sale nè");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (check.getAmountused() > 0) {
			check.setAmountused(check.getAmountused() + 1);
			final Sale updateAmountuser = saleService.save(check);
			System.err.println("Lấy thành công: ");
		}

		Orders orders = new Orders();
		orders.setDate(date);
		orders.setUserOrder(account);
		orders.setStatusId(status);
		orders.setFullname(fullname);
		orders.setPhone(phone);
		if (addNode == null) {
			Optional<List<Address>> addrList = addressDAO.findByUsername(account.getUsername());
			if (addrList.isPresent()) {
				address = addrList.get().get(0).getAddress();
				orders.setAddress(address);
			}
		} else {
			address = addNode.asText();
			orders.setAddress("Chân cầu Cần Thơ");
		}

		if (saleid == 0) {
			orders.setSaleId(null);
		} else {
			Sale sale = saleService.findById(saleid);
			orders.setSaleId(sale);
		}

		orderService.create(orders); // Lưu đối tượng Orders và OrderDetails trong cùng một giao dịch

		OrderDetails orderDetails;

		for (Cart cartProduct : cartList) {
			Products product = productsDAO.findByProductId(cartProduct.getProCart().getId());
			orderDetails = new OrderDetails();

			orderDetails.setOrdersId(orders);
			orderDetails.setColorId(cartProduct.getColorCart());
			orderDetails.setProductsId(product);
			orderDetails.setPrice(cartProduct.getPrice());
			orderDetails.setQty(cartProduct.getQty());

			ordersService.create(orderDetails);
			ProductColor productColor = cartProduct.getColorCart();
			productColor.setQty(productColor.getQty() - cartProduct.getQty());
			productColorService.update(productColor);

			cartService.delete(cartProduct.getId());
		}

		return ResponseEntity.ok(orders);
	}

}
