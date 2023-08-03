package j6.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import j6.asm.entity.Categories;
import j6.asm.service.AccountsService;
import j6.asm.service.BrandService;
import j6.asm.service.CategoriesService;
import j6.asm.service.ColorService;
import j6.asm.service.OrdersService;
import j6.asm.service.PriceHistoryService;
import j6.asm.service.ProductsService;
import j6.asm.service.SaleService;

@CrossOrigin("*")
@RestController
public class IndexRestController {

	@Autowired
	AccountsService accounts;
	@Autowired
	ProductsService products;
	@Autowired
	CategoriesService category;
	@Autowired
	BrandService brands;
	@Autowired
	OrdersService orders;
	@Autowired
	SaleService sales;
	@Autowired
	ColorService colors;
	@Autowired
	PriceHistoryService prices;

	@GetMapping("/rest/indexCount")
	public JsonNode getCountIndex() {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("accountCount", accounts.getCount());
		node.put("productCount", products.getCount());
		node.put("cateCount", category.getCount());
		node.put("brandCount", brands.getCount());
		node.put("orderCount", orders.getCount());
		node.put("saleCount", sales.getCount());
		node.put("colorCount", colors.getCount());
		node.put("priceNew", prices.getPriceNew());

		return node;

	}

	@GetMapping("/rest/revenueData")
	public ResponseEntity<List<Object[]>> getRevenueByYear() {
		List<Object[]> revenueData = orders.getRevenueByYear();
		return ResponseEntity.ok(revenueData);
	}

}
