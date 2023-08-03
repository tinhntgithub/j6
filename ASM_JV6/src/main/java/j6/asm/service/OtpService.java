package j6.asm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import j6.asm.entity.Otp;

public interface OtpService {
	List<Otp> findAll();

	Otp findById(Integer id);

	void create(Otp entity);

	void update(Otp entity);

	void delete(Integer id);
}
