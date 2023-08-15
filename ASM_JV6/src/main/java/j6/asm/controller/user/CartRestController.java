package j6.asm.controller.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import j6.asm.dao.AddressDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.entity.Cart;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;
import j6.asm.service.AddressService;
import j6.asm.service.ProductColorService;
import j6.asm.service.ProductsService;
import j6.asm.service.SessionService;
import j6.asm.service.impl.AddressServiceImp;
import j6.asm.service.impl.CartServiceImp;
import j6.asm.service.impl.ShoppingCartServiceImpl;

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
	@Autowired
	AddressDAO addressDAO;

	@Autowired
	AddressService addressService;

	@Autowired
	ProductColorService pdColorService;

	RestTemplate resp = new RestTemplate();

	@GetMapping("/update_profile/address")
	public ResponseEntity<List<Address>> getAddress() {
		Accounts acc = session.get("account");
		if (acc == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		List<Address> addressList = addressDAO.findByUsername(acc, null);
		return ResponseEntity.ok(addressList);
	}

	@PostMapping("/update_profile/address")
	public ResponseEntity<Address> createAddress(@RequestBody Address form) throws ParseException {

		Address adr = new Address();
		Accounts acc = session.get("account");
		adr.setUserAr(acc);
		adr.setAddress(form.getAddress());
		System.out.println("ooooooooooooooo" + form.getAddress());
		// Lưu đối tượng Address vào cơ sở dữ liệu bằng addressService
		addressService.create(adr);

		return ResponseEntity.ok(form);

	}

	@SuppressWarnings("unused")
	@PutMapping("/update_profile/address")
	public ResponseEntity<Address> PutOne(@RequestBody Address form,@RequestParam("id") String id) throws ParseException {
		Accounts acc = session.get("account");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if (acc == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Optional<Address> contacts = addressDAO.findContactIdByUserAr(Integer.parseInt(id), acc.getUsername());
		if(!contacts.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Address updateC= contacts.get();
		updateC.setUserAr(acc);
		updateC.setAddress(form.getAddress());
		
		addressService.create(updateC);
		return ResponseEntity.ok(form);
	}
	

	@DeleteMapping("/update_profile/deleteAddress/{id}")
	public ResponseEntity<JsonNode> deleteCartList(@PathVariable("id") String id) {
		Accounts acc = session.get("account");
		if (acc == null) {
			return ResponseEntity.status(404).build();
		}
		Optional<List<Address>> listAdr = addressDAO.findByUsername(acc.getUsername());
		if (listAdr.isPresent() && listAdr.get().size() <= 1) {
			return ResponseEntity.status(404).build();
		}

		if (!NumberUtils.isParsable(id)) {
			return ResponseEntity.status(404).build();
		}
		Optional<Address> contacts = addressDAO.findById(Integer.parseInt(id));
		if (!contacts.isPresent()) {
			return ResponseEntity.status(404).build();
		} else if (!contacts.get().getUserAr().getUsername().equals(acc.getUsername())) {
			return ResponseEntity.status(404).build();
		} else {
			addressDAO.delete(contacts.get());
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/Cart/create/{id}")
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

	// thêm giỏ hàng khi ở trang chủ, ...
	@PostMapping("/Cart/create/{id}")
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

	// thêm vào giỏ hàng tại sản phẩm chi tiết
	@PostMapping("/rest/cart/{pdid}/{colorid}")
	public ResponseEntity<List<Cart>> addToCart2(@PathVariable("pdid") Integer pdid,
			@PathVariable("colorid") Integer colorid,
			@RequestBody() Optional<Integer> qty, HttpServletResponse resp) throws IOException {
		Accounts acc = session.get("account");
		if (acc == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500
		}

		Products pd = daoProduct.findById(pdid);
		ProductColor productColor = pdColorService.getOne(colorid, pd.getId());
		if (qty.isPresent()) {
			cart.add2(qty.get(), acc, productColor);
		} else {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@GetMapping("/Cart/delete/{id}")
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

	@GetMapping("/Cart/listCart")
	public ResponseEntity<List<Cart>> listCart() {
		Accounts acc = session.get("account");
		return ResponseEntity.ok(cart.getCarts(acc));
	}

	@PutMapping("/Cart/update")
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

	private List<Cart> tempList = new ArrayList<>();

	@PostMapping("/rest/cart/savetemplist")
	public ResponseEntity<String> saveTempList(@RequestBody List<Cart> tempCarts) {
		tempList = tempCarts;
		return ResponseEntity.ok("Ok");
	}

	@GetMapping("/rest/cart/gettemplist")
	public ResponseEntity<List<Cart>> getTempList() {
		return ResponseEntity.ok(tempList);
	}

}
