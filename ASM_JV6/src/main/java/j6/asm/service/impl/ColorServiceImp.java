package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.ColorDAO;
import j6.asm.dao.ProductColorDAO;
import j6.asm.entity.Color;
import j6.asm.service.ColorService;
@Service
public class ColorServiceImp implements ColorService {

	@Autowired
	ColorDAO dao;
	@Autowired
	ProductColorDAO daoPrc;
	
	@Override
	public List<Color> findAll() {
		return dao.findAll();
	}
	@Override
	public Color findById(Integer id) {
		return dao.findById(id).get();
	}
	@Override
	public void create(Color entity) {
		dao.save(entity);
	}
	@Override
	public void update(Color entity) {
		dao.save(entity);
	}
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Boolean checkProduct(Integer id) {
		return daoPrc.findByColor(this.findById(id)).isEmpty();
	}
	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
