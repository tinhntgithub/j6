package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.AccountDAO;
import j6.asm.dao.AuthoritiesDAO;
import j6.asm.dao.RolesDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Authorities;
import j6.asm.entity.Roles;
import j6.asm.service.AuthoritiesService;
@Service
public class AuthoritiesServiceImp implements AuthoritiesService{

	@Autowired
	AuthoritiesDAO dao;
	@Autowired
	RolesDAO daoRole;
	@Autowired
	AccountDAO daoAcc;
	
	@Override
	public List<Authorities> findAll() {
		return dao.findAll();
	}
	@Override
	public Authorities findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Authorities entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Authorities entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Boolean checkAuth(Integer id) {
//		Kiểm tra vai trò admin của tài khoản :)))
		return this.findById(id).getRoleId().getId().equalsIgnoreCase("DIRE");
	}

	@Override
	public Boolean checkAuth2(String roleid,String user) {
		return !dao.getAccountAuth(roleid, user).isEmpty();
	}
}
