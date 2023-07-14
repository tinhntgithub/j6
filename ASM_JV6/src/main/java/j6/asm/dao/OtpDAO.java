package j6.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import j6.asm.entity.Otp;

public interface OtpDAO extends JpaRepository<Otp, Integer> {

}
