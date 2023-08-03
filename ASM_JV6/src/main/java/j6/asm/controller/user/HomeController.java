package j6.asm.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import j6.asm.dao.CategoriesDAO;
import j6.asm.dao.FavoritesDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.Categories;
import j6.asm.entity.Favorites;
import j6.asm.entity.ProductColor;
import j6.asm.entity.ProductImg;
import j6.asm.entity.Products;
import j6.asm.service.CategoriesService;
import j6.asm.service.ProductColorService;
import j6.asm.service.ProductImgService;
import j6.asm.service.ProductsService;
import j6.asm.service.SessionService;
import j6.asm.service.impl.CategoryServiceImp;

@Controller
public class HomeController {
	@Autowired
	SessionService session;
	@Autowired
	ProductsDAO productdao;
	@Autowired
	ProductsService productservice;
	@Autowired
	CategoryServiceImp categoriesservice;
	@Autowired
	CategoriesDAO cateDAO;
	@Autowired
	FavoritesDAO favoritesDAO;
	@Autowired
	ProductImgService productImgService;
	@Autowired
	ProductColorService colorService;

	// Index Page :))
	@RequestMapping("/index.html")
	public String index(Model m, @RequestParam("cateid") Optional<Integer> cateid) {

		List<Products> product = productservice.findAll();
		m.addAttribute("product", product);

		List<Categories> categories = cateDAO.listCateInProduct();
		if (!categories.isEmpty()) {
			m.addAttribute("cate", categories);
		}

		List<Products> pro_cate = productdao.listProduct_InCategories(cateid.orElse(1));
		m.addAttribute("pro_cate", pro_cate);

		m.addAttribute("cateid", cateid.orElse(1));

		return "/user/home/index";
	}

	// About page
	@RequestMapping("/about.html")
	public String about(Model m) {

		m.addAttribute("ab", "active");
		return "/user/home/about";
	}

	// Contact page
	@RequestMapping("/contact.html")
	public String contact(Model m) {

		m.addAttribute("ct", "active");
		return "/user/home/contact";
	}

	// Product page
	@RequestMapping("/shop.html")
    public String shopPage(Model m, @RequestParam("cateid") Optional<Integer> cateid,
            @RequestParam("p") Optional<Integer> p) {

        List<Categories> categories = cateDAO.listCateInProduct();
        if (!categories.isEmpty()) {
            m.addAttribute("cate", categories);
        }

        Pageable pageable = PageRequest.of(p.orElse(0), 6);

        if (cateid.isPresent()) {
            Page<Products> page = productservice.listProduct_InCategoriesPage(cateid.get(), pageable);
            m.addAttribute("product", page);
        } else {
            Page<Products> page = productservice.findAllPage(pageable);
            m.addAttribute("product", page);
        }

        List<Favorites> favorites = favoritesDAO.findAll();
        m.addAttribute("kt", 0);
        m.addAttribute("favorites", favorites);
        m.addAttribute("sp", "active");
        return "/user/home/shop";
    }

	// Product details page
	@RequestMapping("/product.html")
	public String product(Model m, @RequestParam("id") Integer id) {
		Products product = productservice.findById(id);
		m.addAttribute("item", product);

		List<Products> pro = productservice.findByCateId(product.getCatePro().getId());
		m.addAttribute("product", pro);

		List<ProductImg> productImages = productImgService.findByImgPro(id);
		m.addAttribute("productImages", productImages);

		List<ProductColor> color = colorService.getColorId(id);
		m.addAttribute("listColor", color);

		return "/user/home/product";
	}

}
