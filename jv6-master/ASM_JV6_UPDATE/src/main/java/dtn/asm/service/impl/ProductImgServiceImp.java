package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.ProductImgDAO;
import dtn.asm.entity.ProductImg;
import dtn.asm.service.ProductImgService;
@Service
public class ProductImgServiceImp implements ProductImgService {

	@Autowired
	ProductImgDAO dao;
	
	@Override
	public List<ProductImg> findAll() {
		return dao.findAll();
	}
	
	@Override
	public ProductImg findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(ProductImg entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(ProductImg entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
