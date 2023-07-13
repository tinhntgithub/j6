package dtn.asm.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dtn.asm.entity.Categories;
import dtn.asm.service.AccountsService;
import dtn.asm.service.BrandService;
import dtn.asm.service.CategoriesService;
import dtn.asm.service.ColorService;
import dtn.asm.service.OrdersService;
import dtn.asm.service.PriceHistoryService;
import dtn.asm.service.ProductsService;
import dtn.asm.service.SaleService;

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
		node.put("accountCount",accounts.getCount());
		node.put("productCount",products.getCount());
		node.put("cateCount",category.getCount());
		node.put("brandCount",brands.getCount());
		node.put("orderCount",orders.getCount());
		node.put("saleCount",sales.getCount());
		node.put("colorCount",colors.getCount());
		node.put("priceNew",prices.getPriceNew());
		
		return node;
		
	}
	

}
