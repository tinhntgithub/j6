package j6.asm.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import j6.asm.entity.Accounts;
import j6.asm.entity.Address;

public interface AccountDAO extends JpaRepository<Accounts, String> {
    @Query(value = "select * from Accounts where Email like ?1", nativeQuery = true)
    String findEmail(String email);

    @Query(value = "select * from Accounts where Phone=?1", nativeQuery = true)
    String findPhone(String phone);

    List<Accounts> findByEmail(String email);

    List<Accounts> findByPhone(String phone);

    @Query("SELECT count(o) FROM Accounts o")
    Integer getCount();

    @Query(value = "SELECT o FROM Accounts o WHERE o.username=:username and o.active=:active")
    Optional<Accounts> findByIdAndActiveTrue(String username, Boolean active);

    @Query("SELECT a FROM Accounts a WHERE a.email = :email and a.active = true")
    Optional<Accounts>   checkDuplicateEmail(@Param("email") String email);


    @Query(value = "SELECT a.Fullname, o.CreateDate AS orderYear, COUNT(d.ProductId) AS productCount, SUM(d.Price * d.Quantity) AS totalSum "
            +
            "FROM Accounts a " +
            "JOIN Orders o ON a.Username = o.Username " +
            "JOIN OrderDetails d ON d.OrderId = o.Id " +
            "JOIN Products p ON d.ProductId = p.Id " +
            "WHERE o.CreateDate >= ?1 AND o.CreateDate <= ?2 AND o.StatusId = 3 " +
            "GROUP BY a.Fullname, o.CreateDate", nativeQuery = true)
    List<Object[]> getPurchaseDataByYearRange(Date from, Date to);

    @Query(value = "SELECT a.Fullname, o.CreateDate AS orderYear, COUNT(d.ProductId) AS productCount, SUM(d.Price * d.Quantity) AS totalSum "
            +
            "FROM Accounts a " +
            "JOIN Orders o ON a.Username = o.Username " +
            "JOIN OrderDetails d ON d.OrderId = o.Id " +
            "JOIN Products p ON d.ProductId = p.Id " +
            "WHERE o.StatusId = 3 " +
            "GROUP BY a.Fullname, o.CreateDate", nativeQuery = true)
    List<Object[]> getReportAllCustomer();


}
