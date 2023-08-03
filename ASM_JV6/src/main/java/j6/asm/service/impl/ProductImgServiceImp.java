package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.ProductImgDAO;
import j6.asm.entity.ProductImg;
import j6.asm.service.ProductImgService;
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
