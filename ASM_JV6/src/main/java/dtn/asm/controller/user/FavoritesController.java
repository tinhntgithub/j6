package dtn.asm.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dtn.asm.service.SessionService;

@Controller
public class FavoritesController {
	@Autowired
	SessionService session;

	// Product Favorite page
	@RequestMapping("/product_favorite.html")
	public String favoritesPage() {
		return "user/favorite/product-favorite";
	}

	// Blog page
	@RequestMapping("/blog.html")
	public String blogPage(Model m) {

		m.addAttribute("bl","active");
		return "user/blog/blog";
	}

	// Blog single page
	@RequestMapping("/single-blog.html")
	public String singleBlogPage() {
		return "user/blog/single-blog";
	}
}
