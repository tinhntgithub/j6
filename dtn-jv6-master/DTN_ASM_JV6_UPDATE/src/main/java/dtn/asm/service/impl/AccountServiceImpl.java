package dtn.asm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtn.asm.dao.AccountDAO;
import dtn.asm.dao.FavoritesDAO;
import dtn.asm.dao.OrdersDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.service.AccountsService;

@Service
public class AccountServiceImpl implements AccountsService {
	@Autowired
	AccountDAO dao;
	@Autowired 
	OrdersDAO daoOrder;
	@Autowired
	FavoritesDAO daoFav;

	@Override
	public List<Accounts> findAll() {

		return dao.findAll();
	}

	@Override
	public Accounts findById(String id) {
		Optional<Accounts> acc = dao.findById(id);
		if (acc.isPresent()) {
			return acc.get();
		}
		return null;
	}

	@Override
	public void create(Accounts entity) {
		dao.save(entity);
	}

	@Override
	public void update(Accounts entity) {
		dao.save(entity);
	}

	@Override
	public void delete(String id) {
		dao.delete(this.findById(id));
	}

	@Override
	public Boolean check(String id) {
		Accounts ac = this.findById(id);
		return daoFav.findByUserFvr(ac).isEmpty() &&  daoOrder.findByUserOrder(ac).isEmpty();
	}
	
	@Override
	public Boolean checkUsername(String id) {
		return this.findById(id) != null;
	}
	@Override
	public Boolean checkEmail(String email) {
		return !dao.findByEmail(email).isEmpty();
	}
	
	@Override
	public Boolean checkPhone(String phone) {
		return !dao.findByPhone(phone).isEmpty();
	}
	
	@Override
	public Integer getCount() {
		return dao.getCount();
	}
}
