package dtn.asm.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Cart;
import dtn.asm.service.SessionService;
import dtn.asm.service.impl.ProductsServiceImp;
import dtn.asm.service.impl.ShoppingCartServiceImpl;

@Controller
public class CartController {
	@Autowired
	ShoppingCartServiceImpl cart;
	@Autowired
	ProductsServiceImp daoProduct;
	@Autowired
	SessionService session;

// Cart page
	@RequestMapping("/cart.html")
	public String cartPage() {
		return "user/cart/cart";
	}

//	@RequestMapping("/addToCart/{id}")
//	public String addToCart(@PathVariable("id") Integer id) {
//		Accounts acc = session.get("account");
//		cart.add(1, daoProduct.findById(id), 1, acc);
//		return "redirect:/cart.html";
//	}
	@ModelAttribute("listCart")
	public List<Cart> getList(){
		Accounts acc= session.get("account");
		return cart.getCarts(acc);
	}

}
