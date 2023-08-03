package j6.asm.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import j6.asm.dao.OrderDetailsDAO;
import j6.asm.dao.OrdersDAO;
import j6.asm.dao.StatusDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.OrderDetails;
import j6.asm.entity.Orders;
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

	// Checkout page
	@RequestMapping("/checkout.html")
	public String checkoutPage() {
		return "user/order/checkout";
	}

	// Order Manager
//	@RequestMapping("/orders.html")
//	public String ordersPage() {
//		return "user/order/orders";
//	}

//	 Order Details Manager
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
	@GetMapping("/orders.html")
	public String order_Get(Model m) {
		Accounts account = session.get("account");
		List<Orders> order_all = new ArrayList<>();
		List<Orders> order_huy = new ArrayList<>();
		if (account != null) {
			order_all = orderdao.find_LoginbyUsername(account.getUsername());
			order_huy = orderdao.find_ByHuy(account.getUsername());
		}
		m.addAttribute("huy", order_huy);
		m.addAttribute("or", order_all);
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
