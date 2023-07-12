package dtn.asm.controller.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dtn.asm.entity.Accounts;
import dtn.asm.entity.Cart;
import dtn.asm.entity.Products;
import dtn.asm.service.ProductsService;
import dtn.asm.service.SessionService;
import dtn.asm.service.impl.CartServiceImp;
import dtn.asm.service.impl.ShoppingCartServiceImpl;

@CrossOrigin("*")
@RestController
public class CartRestController {
	@Autowired
	ShoppingCartServiceImpl cart;
	@Autowired
	CartServiceImp cartService;
	@Autowired
	ProductsService daoProduct;
	@Autowired
	SessionService session;

	RestTemplate resp = new RestTemplate();

	@GetMapping("/rest/Cart/create/{id}")
	public ResponseEntity<List<Cart>> addToCart(@PathVariable("id") Optional<Integer> id, HttpServletResponse resp)
			throws IOException {
		Accounts acc = session.get("account");
		if (acc == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500
		}
		if (id.isPresent()) {
			Products pro = daoProduct.findById(id.get());
			cart.add(1, pro, 1, acc);
		} else {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@PostMapping("/rest/Cart/create/{id}")
	public ResponseEntity<List<Cart>> addToCart(@PathVariable("id") Optional<Integer> id,
			@RequestBody() Optional<Integer> qty, HttpServletResponse resp) throws IOException {
		Accounts acc = session.get("account");
		if (acc == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500
		}
		if (id.isPresent()) {
			Products pro = daoProduct.findById(id.get());
			if (qty.isPresent()) {
				cart.add(1, pro, qty.get(), acc);
			} else {
				return ResponseEntity.noContent().build();
			}
		} else {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@GetMapping("/rest/Cart/delete/{id}")
	public ResponseEntity<List<Cart>> deleteToCart(@PathVariable("id") Optional<Integer> id) {
		Accounts acc = session.get("account");
		if (id.isPresent()) {
			Cart cartF = cartService.findById(id.get());
			if (cartF.getUserCart().getUsername().equals(acc.getUsername())) {
				cart.remove(id.get());
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@GetMapping("/rest/Cart/listCart")
	public ResponseEntity<List<Cart>> listCart() {
		Accounts acc = session.get("account");
		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@PutMapping("/rest/Cart/update")
	public ResponseEntity<Cart> updateToCart(@RequestBody Optional<Cart> cartBody) {
		Accounts acc = session.get("account");
		if (cartBody.isPresent()) {
			Cart put = cartService.findById(cartBody.get().getId());
			if (put.getUserCart().getUsername().equals(acc.getUsername())) {
				put.setQty(cartBody.get().getQty());
				cart.update(cartBody.get());
				return ResponseEntity.ok(cartBody.get());
			} else {
				return ResponseEntity.noContent().build();
			}
		} else {
			return ResponseEntity.noContent().build();
		}

	}

}
