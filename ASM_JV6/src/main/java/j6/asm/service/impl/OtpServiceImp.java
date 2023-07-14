package j6.asm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import j6.asm.dao.OtpDAO;
import j6.asm.entity.Otp;
import j6.asm.service.OtpService;
@Service
public class OtpServiceImp implements OtpService {

	@Autowired
	OtpDAO dao;
	
	@Override
	public List<Otp> findAll() {
		return dao.findAll();
	}
	
	@Override
	public Otp findById(Integer id) {
		return dao.getById(id);
	}
	
	@Override
	public void create(Otp entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(Otp entity) {
		dao.save(entity);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
