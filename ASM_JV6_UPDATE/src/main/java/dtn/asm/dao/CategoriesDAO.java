package dtn.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dtn.asm.entity.Categories;

public interface CategoriesDAO extends JpaRepository<Categories, Integer> {
	@Query (value = "select b.Id, b.Name from  Products a inner join Categories b on a.CategoryId=b.Id group by b.Name, b.Id",nativeQuery = true)
	List<Categories> listCateInProduct();
	
	@Query("SELECT count(o) FROM Categories o")
	Integer getCount();
}
