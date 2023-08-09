package j6.asm.controller.admin;

import java.util.List;
import java.util.Map;

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

import j6.asm.dao.ColorDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.Color;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;
import j6.asm.service.ProductColorService;
import j6.asm.service.ProductsService;

@CrossOrigin("*")
@RestController
public class ProductColorRestController {

	@Autowired
	ProductsDAO pddao;

	@Autowired
	ColorDAO colorDAO;

	@Autowired
	ProductColorService colorPr;
	@Autowired
	ProductsService products;

	@GetMapping("/rest/product_colors")
	public ResponseEntity<List<ProductColor>> getAll(Model m) {
		return ResponseEntity.ok(colorPr.findAll());
	}

	@GetMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> getOne(@PathVariable("id") Integer id) {
		if (colorPr.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(colorPr.findById(id));
	}

	@GetMapping("/rest/pdcl/{id}")
	public ResponseEntity<List<ProductColor>> getByPd(@PathVariable("id") Integer id) {
		if (colorPr.findByPd(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(colorPr.findByPd(id));
	}

	@GetMapping("/rest/product_colors/check/{id}")
	public Boolean checkProduct(@PathVariable("id") Integer id) {
		return colorPr.checkOrder(id);
	}

	@GetMapping("/rest/product_colors/check2/{id}/{idd}")
	public Boolean checkProductColor(@PathVariable("id") Integer id, @PathVariable("idd") Integer idd) {
		return colorPr.checkExitsProduct(id, idd);
	}

	@PostMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> post(@PathVariable("id") Integer id, @RequestBody Color data) {
		Products pr = products.findById(id);
		ProductColor colorsPro = new ProductColor();
		colorsPro.setProducts(pr);
		colorsPro.setColor(data);
		colorPr.create(colorsPro);

		return ResponseEntity.ok(colorPr.findById(colorsPro.getId()));
	}

	@PostMapping("/rest/pdcolor/{productid}")
	public ResponseEntity<String> post2(@PathVariable("productid") Integer productid,
			@RequestBody List<Map<String, Object>> colorList) {
		for (Map<String, Object> data : colorList) {
			Integer id = (int) data.get("id");
			Integer quantity = (int) data.get("quantity");

			Products product = pddao.getById(productid);
			Color color = colorDAO.getById(id);

			ProductColor colorProduct = new ProductColor();
			colorProduct.setProducts(product);
			colorProduct.setColor(color);
			colorProduct.setQty(quantity);

			colorPr.create(colorProduct);
		}

		return ResponseEntity.ok("Ok");
	}

	@PutMapping("/rest/pdcolor/{productid}")
	public ResponseEntity<String> put2(@PathVariable("productid") Integer productid,
			@RequestBody List<Map<String, Object>> colorList) {
		for (Map<String, Object> data : colorList) {
			Integer id = (int) data.get("id");
			Integer quantity = (int) data.get("quantity");

			Boolean a = colorPr.checkExitsProduct(id, productid);
			if (a) {
				Products product = pddao.getById(productid);
				Color color = colorDAO.getById(id);

				ProductColor pd = colorPr.getOne(id, productid);

				pd.setQty(quantity);
				pd.setColor(color);
				pd.setProducts(product);
				colorPr.create(pd);
			}
		}

		return ResponseEntity.ok("Ok");
	}

	@PutMapping("/rest/product_colors/{id}")
	public ResponseEntity<ProductColor> put(@PathVariable("id") Integer id, @RequestBody Color data) {
		Products pr = products.findById(id);
		ProductColor colorsPro = new ProductColor();
		colorsPro.setProducts(pr);
		colorsPro.setColor(data);
		colorPr.create(colorsPro);

		return ResponseEntity.ok(colorPr.findById(colorsPro.getId()));
	}

	@DeleteMapping("/rest/product_colors/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if (colorPr.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		colorPr.delete(id);
		return ResponseEntity.ok().build();
	}

}
