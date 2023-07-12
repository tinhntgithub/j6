package dtn.asm.controller.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dtn.asm.dao.AccountDAO;
import dtn.asm.dao.FavoritesDAO;
import dtn.asm.dao.ProductsDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.entity.Favorites;
import dtn.asm.entity.Products;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/rest/favorites")
public class FavoritesRestController {

	@Autowired
	FavoritesDAO fvrDao;

	@Autowired
	ProductsDAO pdutDao;

	@Autowired
	AccountDAO accDao;

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@GetMapping("all")
	public ResponseEntity<List<Favorites>> getAll() {
		List<Favorites> list = fvrDao.findAll();
		if (list.isEmpty() || list == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);
	}

	@PostMapping("like/{id}")
	public ResponseEntity<Object> likeByID(@PathVariable("id") String id) throws IOException {
		Integer idConvert = 0;
		String username = null;
		Accounts accounts = new Accounts();
		Products products = new Products();
		Favorites favorites = new Favorites();
		if (req.getUserPrincipal() == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500
		} else {
			username = req.getUserPrincipal().getName();
			accounts = accDao.findById(username).get();
		}
		if (NumberUtils.isParsable(id)) {
			idConvert = Integer.valueOf(id);
			if (pdutDao.findById(idConvert).isPresent()) {
				products = pdutDao.findById(idConvert).get();
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();// 424
			}
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();// 424
		}
		if (accounts != null && products != null) {
			favorites = fvrDao.findByProductsIdAndUsername(accounts.getUsername(), products.getId());
			if (favorites == null) {
				Favorites addFvr = new Favorites();
				addFvr.setProductsId(products);
				addFvr.setUserFvr(accounts);
				addFvr.setActive(true);
				fvrDao.save(addFvr);
			} else {
				if (favorites.getActive()) {
					favorites.setActive(false);
					favorites.setLikeDate(new Date());
					fvrDao.save(favorites);
				} else {
					favorites.setLikeDate(new Date());
					favorites.setActive(true);
					fvrDao.save(favorites);
				}
			}
		}
		return ResponseEntity.ok(favorites.getActive());// 200
	}
}
