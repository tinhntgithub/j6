package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.Favorites;

public interface FavoritesService {
	List<Favorites> findAll();

	Favorites findById(Integer id);

	void create(Favorites entity);

	void update(Favorites entity);

	void delete(Integer id);
}
