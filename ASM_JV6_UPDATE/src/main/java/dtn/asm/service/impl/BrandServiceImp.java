package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.BrandDAO;
import dtn.asm.dao.ProductsDAO;
import dtn.asm.entity.Brand;
import dtn.asm.service.BrandService;
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
