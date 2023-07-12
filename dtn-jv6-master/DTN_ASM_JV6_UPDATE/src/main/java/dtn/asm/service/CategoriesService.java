package dtn.asm.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import dtn.asm.entity.Categories;

public interface CategoriesService {
	List<Categories> findAll();

	Categories findById(Integer id);

	void create(Categories entity);

	void update(Categories entity);

	void delete(Integer id);
	
	Boolean checkProduct(Integer id);
	
	Integer getCount();
}
