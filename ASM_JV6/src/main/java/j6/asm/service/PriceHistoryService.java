package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.PriceHistory;

public interface PriceHistoryService {
	List<PriceHistory> findAll();

	PriceHistory findById(Integer id);

	void create(PriceHistory entity);

	void update(PriceHistory entity);

	void delete(Integer id);
	
	Double getPriceNew();
}
