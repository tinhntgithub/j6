package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.FavoritesDAO;
import j6.asm.entity.Favorites;
import j6.asm.service.FavoritesService;
@Service
public class FavoritesServiceImp implements FavoritesService {

	@Autowired
	FavoritesDAO dao;
	
	@Override
	public List<Favorites> findAll() {
		return dao.findAll();
	}
	@Override
	public List<Object[]> listReportFavorites(){
		return dao.listReportFavorites();
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
