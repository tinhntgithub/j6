package j6.asm.controller.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import j6.asm.dao.AddressDAO;
import j6.asm.dao.CartDAO;
import j6.asm.dao.OrderDetailsDAO;
import j6.asm.dao.OrdersDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.dao.StatusDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.entity.Cart;
import j6.asm.entity.OrderDetails;
import j6.asm.entity.Orders;
import j6.asm.entity.Products;
import j6.asm.entity.Status;
import j6.asm.service.OrderDetailsService;
import j6.asm.service.OrdersService;
import j6.asm.service.SessionService;

@Controller
public class OrderController {

	@Autowired
	OrdersDAO orderdao;

	@Autowired
	SessionService session;

	@Autowired
	OrdersService orderservice;

	@Autowired
	OrderDetailsService orderdetailsservice;

	@Autowired
	OrderDetailsDAO orderdetaildao;

	@Autowired
	StatusDAO statusdao;

	@Autowired
	CartDAO cartDAO;

	@Autowired
	ProductsDAO productsDAO;

	@Autowired
	AddressDAO addressDAO;

	// Checkout page
	@RequestMapping("/checkout.html")
	public String checkoutPage(Model model) {
		// Lấy thông tin sản phẩm từ giỏ hàng
		Accounts account = session.get("account");
		model.addAttribute("fullName", account.getFullname());
		model.addAttribute("Phone", account.getPhone());
		model.addAttribute("address", account.getAddress());
		return "user/order/checkout";
	}

	@RequestMapping("/orders.html")
	public String ordersPage(Model model) throws IOException, ParseException {
		Accounts account = session.get("account");
		List<Cart> cart = cartDAO.findByUserCart(account);
		Pageable pageable = PageRequest.of(0, 10);
		List<Address> addresses = addressDAO.findByUsername(account, pageable);
		List<OrderDetails> listOrderDetails = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
		String dateString = now.format(formatter);

		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSS");
		Date date = inputFormat.parse(dateString);

		Status status = statusdao.getById(1);

		Orders orders = new Orders(null, account, date, null, "Kiên Giang", account.getFullname(), account.getPhone(),
				status,
				null);

		orderdao.save(orders); // Lưu đối tượng Orders và OrderDetails trong cùng một giao dịch

		OrderDetails orderDetails;

		for (Cart cartProduct : cart) {
			Products product = productsDAO.findByProductId(cartProduct.getProCart().getId());
			orderDetails = new OrderDetails(null, orders, product, cartProduct.getPrice(),
					cartProduct.getQty(), cartProduct.getColorCart());
			orderdetaildao.save(orderDetails);
			cartDAO.delete(cartProduct);
		}

		orders.setOrderDetails(listOrderDetails);

		orderdao.save(orders); // Lưu đối tượng Orders và OrderDetails trong cùng một giao dịch

		List<Orders> order_all = orderdao.find_LoginbyUsername(account.getUsername());
		model.addAttribute("or", order_all.get(0));

		return "redirect:/manageOrders.html";
	}

	// Order Manager
	@Transactional // Thêm annotation @Transactional để quản lý giao dịch
	@RequestMapping("/manageOrders.html")
	public String ordersManagePage(Model model) throws IOException, ParseException {
		Accounts account = session.get("account");
<<<<<<< HEAD
		if(account==null) {
=======
		if (account == null) {
>>>>>>> duylk
			return "redirect:/index.html";
		}
		List<Orders> order_huy = new ArrayList<>();
		order_huy = orderdao.find_ByHuy(account.getUsername());
		model.addAttribute("huy", order_huy);
		List<Orders> order_all = orderdao.find_LoginbyUsername(account.getUsername());
<<<<<<< HEAD
		
=======

>>>>>>> duylk
		model.addAttribute("or", order_all);
		return "user/order/orders";
	}

	// Order Details Manager
	@GetMapping("/order_details.html")
	public String orderDetailsPage(Model m, @RequestParam("id") Integer id) {
		Accounts account = session.get("account");
		List<OrderDetails> details = orderdetaildao.find_Order_details(id);
		m.addAttribute("details", details);

		// thông tin khách hàng
		List<Orders> order_all = orderdao.find_LoginbyUsername(account.getUsername());
		m.addAttribute("or", order_all.get(0));
		return "user/order/order-details";
	}

	// Order page
	@GetMapping("/orders/{id}")
	public String order_Get(Model model, @PathVariable("id") String id) {
		Accounts account = session.get("account");
		List<Orders> order_all = new ArrayList<>();
		List<Orders> order_huy = new ArrayList<>();
		if (account != null) {
			order_all = orderdao.find_LoginbyUsername(account.getUsername());
			order_huy = orderdao.find_ByHuy(account.getUsername());

			// Lấy thông tin sản phẩm trong giỏ hàng

		}
		model.addAttribute("huy", order_huy);
		model.addAttribute("or", order_all);
		return "user/order/orders";
	}

	@GetMapping("/orders.html/huy/{id}")
	public String order_Post(Model m, @PathVariable("id") Integer id) {
		Accounts account = session.get("account");

		Orders order = orderdao.findById(id).get();
		order.setStatusId(statusdao.findById(4).get());
		orderdao.save(order);
		return "redirect:/orders.html";
	}

}
