package j6.asm.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import j6.asm.entity.Accounts;

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
        Optional<Accounts> checkDuplicateEmail(@Param("email") String email);

        @Query(value = "SELECT a.username,a.Fullname, o.CreateDate AS orderYear, COUNT(d.ProductId) AS productCount, SUM(d.Price * d.Quantity) AS totalSum "
                        +
                        "FROM Accounts a " +
                        "JOIN Orders o ON a.Username = o.Username " +
                        "JOIN OrderDetails d ON d.OrderId = o.Id " +
                        "JOIN Products p ON d.ProductId = p.Id " +
                        "WHERE o.CreateDate >= ?1 AND o.CreateDate <= ?2 AND o.StatusId = 3 AND a.Username = o.Username "
                        + // Đảm bảo dấu cách đúng
                        "GROUP BY  a.username,a.Fullname, o.CreateDate", nativeQuery = true) // Đảm bảo kết thúc câu
                                                                                             // truy vấn bằng dấu cách
        List<Object[]> getPurchaseDataByYearRange(Date from, Date to);

        @Query(value = "SELECT  a.username,a.Fullname, o.CreateDate AS orderYear, COUNT(d.ProductId) AS productCount, SUM(d.Price * d.Quantity) AS totalSum "
                        +
                        "FROM Accounts a " +
                        "JOIN Orders o ON a.Username = o.Username " +
                        "JOIN OrderDetails d ON d.OrderId = o.Id " +
                        "JOIN Products p ON d.ProductId = p.Id " +
                        "WHERE o.StatusId = 3 " +
                        "GROUP BY  a.username,a.Fullname, o.CreateDate", nativeQuery = true)
        List<Object[]> getReportAllCustomer();

        @Query(value = "SELECT a.Fullname, o.Id AS InvoiceId, SUM(d.Price * d.Quantity) AS TotalAmount, o.CreateDate AS OrderDate "
                        +
                        "FROM Accounts a " +
                        "JOIN Orders o ON a.Username = o.Username " +
                        "JOIN OrderDetails d ON o.Id = d.OrderId " +
                        "WHERE a.Username = ?1 " +
                        "GROUP BY a.Fullname, o.Id, o.CreateDate " +
                        "ORDER BY o.CreateDate DESC", nativeQuery = true)
        List<Object[]> getReportPrintCustomer(String username);

        // List<Object[]> getReportPrintCustomer(String username, Date date);

        @Query(value = "SELECT a.Fullname, SUM(d.ProductCount) AS totalProductCount, SUM(d.TotalSum) AS grandTotalSum, a.username "
                        +
                        "FROM Accounts a " +
                        "JOIN (" +
                        "   SELECT o.Username, COUNT(d.ProductId) AS ProductCount, SUM(d.Price * d.Quantity) AS TotalSum "
                        +
                        "   FROM Orders o " +
                        "   JOIN OrderDetails d ON o.Id = d.OrderId " +
                        "   WHERE o.StatusId = 3 " +
                        "   GROUP BY o.Username" +
                        ") d ON a.Username = d.Username " +
                        "GROUP BY a.Fullname,a.username ORDER BY grandTotalSum DESC", nativeQuery = true)
        List<Object[]> getResultList();
}
