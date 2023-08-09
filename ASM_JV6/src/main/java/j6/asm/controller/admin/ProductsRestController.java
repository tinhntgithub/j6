package j6.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import j6.asm.dao.ProductImgDAO;
import j6.asm.entity.Brand;
import j6.asm.entity.Categories;
import j6.asm.entity.PriceHistory;
import j6.asm.entity.ProductColor;
import j6.asm.entity.ProductImg;
import j6.asm.entity.Products;
import j6.asm.service.PriceHistoryService;
import j6.asm.service.ProductColorService;
import j6.asm.service.ProductsService;

@CrossOrigin("*")
@RestController
public class ProductsRestController {

	@Autowired
	ProductsService product;
	@Autowired
	PriceHistoryService priceService;
	@Autowired
	ProductImgDAO pdimgdao;

	@Autowired
	ProductColorService pcs;

	@GetMapping("/rest/products")
	public ResponseEntity<List<Products>> getAll(Model m) {
		return ResponseEntity.ok(product.findAll());
	}

	@GetMapping("/rest/products/{id}")
	public ResponseEntity<Products> getOne(@PathVariable("id") Integer id) {
		if (product.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product.findById(id));
	}
	@GetMapping("/rest/productss/{id}")
	public JsonNode getName(@PathVariable("id") Integer id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name",product.findById(id).getName());
		return node;
	}

	@GetMapping("/rest/products/check/{id}")
	public Boolean check(@PathVariable("id") Integer id) {
		return product.check(id);
	}

	@PostMapping("/rest/products")
	public ResponseEntity<Products> post(@RequestBody Products data) {
//		if(product.findById(data.getId()) != null) {
//			return ResponseEntity.badRequest().build();
//		}
		if (data.getImg() == null) {
			data.setImg("default.jpg");
		}
		product.create(data);
		return ResponseEntity.ok(product.findById(data.getId()));
	}

	@PutMapping("/rest/products/{id}")
	public ResponseEntity<Products> put(@PathVariable("id") Integer id, @RequestBody Products data) {
		if (product.findById(data.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		if (!product.findById(data.getId()).getPrice().equals(data.getPrice()) || !product.findById(data.getId()).getSale().equals(data.getSale())) {
			PriceHistory history = new PriceHistory();
			history.setProduct(product.findById(data.getId()));
			history.setPrice(data.getPrice());
			history.setSale(data.getSale());
			priceService.create(history);	
		}
		product.update(data);
		return ResponseEntity.ok(data);
	}
	
	@PutMapping("/rest/productss/{id}")
	public ResponseEntity<Void> putCatego(@PathVariable("id") Integer id, @RequestBody Categories data) {
		Products pr = product.findById(id);
		pr.setCatePro(data);
		product.update(pr);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/rest/products_brand/{id}")
	public ResponseEntity<Void> putBrand(@PathVariable("id") Integer id, @RequestBody Brand data) {
		Products pr = product.findById(id);
		pr.setBrandPro(data);
		product.update(pr);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/rest/products/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (product.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		 //xóa tất cả ảnh chi tiết
        List<ProductImg> list = pdimgdao.findByPdid(id);
        if(list != null){
            for (ProductImg pdimg : list) {
                pdimgdao.delete(pdimg);
            }
        }

		// xóa màu đi theo sp
		List<ProductColor> list2 = pcs.findByPd(id);
		if(list2 != null){
			for (ProductColor productColor : list2) {
				pcs.delete(productColor.getId());
			}
		}
		product.delete(id);
		return ResponseEntity.ok().build();
	}

}
