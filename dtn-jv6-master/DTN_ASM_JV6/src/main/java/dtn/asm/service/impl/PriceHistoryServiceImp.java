package dtn.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.PriceHistoryDAO;
import dtn.asm.entity.PriceHistory;
import dtn.asm.service.PriceHistoryService;
@Service
public class PriceHistoryServiceImp implements PriceHistoryService {

	@Autowired
	PriceHistoryDAO dao;
	
	@Override
	public List<PriceHistory> findAll() {
		return dao.findAll();
	}
	
	@Override
	public PriceHistory findById(Integer id) {
		return dao.findById(id).get();
	}
	
	@Override
	public void create(PriceHistory entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(PriceHistory entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Double getPriceNew() {
		return this.findById(dao.getPriceNew()).getPrice();
	}

}
