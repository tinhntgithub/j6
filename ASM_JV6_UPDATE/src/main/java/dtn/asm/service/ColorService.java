package dtn.asm.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import dtn.asm.entity.Color;

public interface ColorService {
	List<Color> findAll();

	Color findById(Integer id);

	void create(Color entity);

	void update(Color entity);

	void delete(Integer id);
	
	Boolean checkProduct(Integer id);
	
	Integer getCount();
}
