package dtn.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dtn.asm.entity.PriceHistory;

public interface PriceHistoryService {
	List<PriceHistory> findAll();

	PriceHistory findById(Integer id);

	void create(PriceHistory entity);

	void update(PriceHistory entity);

	void delete(Integer id);
	
	Double getPriceNew();
}
