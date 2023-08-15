package j6.asm.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import j6.asm.entity.Accounts;

@Service
public interface AccountsService {

	List<Accounts> findAll();

	List<Object[]> getPurchaseDataByYearRange(Date from, Date to);

	List<Object[]> getReportAllCustomer();

	Accounts findById(String id);

	void create(Accounts entity);

	void update(Accounts entity);

	void delete(String id);

	Boolean check(String id);

	Boolean checkUsername(String id);

	Boolean checkEmail(String email);

	Boolean checkPhone(String phone);

	Integer getCount();

	Optional<Accounts> checkDuplicateEmail(@Param("email") String email);
	List<Object[]> getReportPrintCustomer(String username);
	List<Object[]> getResultList();
}
