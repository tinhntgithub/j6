package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.BrandDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.Brand;
import j6.asm.service.BrandService;
@Service
public class BrandServiceImp implements BrandService {

	@Autowired
	BrandDAO dao;
	@Autowired
	ProductsDAO daoPr;
	
	@Override
	public List<Brand> findAll() {
		return dao.findAll();
	}
	@Override
	public Brand findById(Integer id) {
		return dao.findById(id).get();
	}
	
	@Override
	public void create(Brand entity) {
		dao.save(entity);
	}
	@Override
	public void update(Brand entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Boolean checkProduct(Integer id) {
		return daoPr.findByBrandPro(this.findById(id)).isEmpty();
	}
	
	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
