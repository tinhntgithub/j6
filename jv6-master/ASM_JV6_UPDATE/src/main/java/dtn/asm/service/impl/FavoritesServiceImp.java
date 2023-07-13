package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.FavoritesDAO;
import dtn.asm.entity.Favorites;
import dtn.asm.service.FavoritesService;
@Service
public class FavoritesServiceImp implements FavoritesService {

	@Autowired
	FavoritesDAO dao;
	
	@Override
	public List<Favorites> findAll() {
		return dao.findAll();
	}
	
	@Override
	public Favorites findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Favorites entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Favorites entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
