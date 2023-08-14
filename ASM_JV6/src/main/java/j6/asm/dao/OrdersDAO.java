package j6.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import j6.asm.entity.Accounts;
import j6.asm.entity.Orders;
import j6.asm.entity.Sale;

public interface OrdersDAO extends JpaRepository<Orders, Integer> {

	List<Orders> findBySaleId(Sale saleId);

	List<Orders> findByUserOrder(Accounts userOrder);

	@Query(value = "SELECT * FROM Orders WHERE statusid = ?", nativeQuery = true)
	List<Orders> findAllById2(Integer id);

	@Query("SELECT count(o) FROM Orders o WHERE o.statusId.id = 1")
	Integer getWaitCount();
	@Query("SELECT count(o) FROM Orders o WHERE o.statusId.id = 2")
	Integer getDelivingCount();
	@Query("SELECT count(o) FROM Orders o WHERE o.statusId.id = 3")
	Integer getCount();
	@Query(value = "select * from Orders a where a.username=?1 and a.statusid = 1 or a.statusid =2", nativeQuery = true)
	List<Orders> find_LoginbyUsername(String username);

	@Query(value = "select * from Orders a where a.statusid = 4 and a.username=?1", nativeQuery = true)
	List<Orders> find_ByHuy(String username);

	@Query(value = "select * from Orders a where a.statusid = 3 and a.username=?1", nativeQuery = true)
	List<Orders> find_ByNhan(String username);

	@Query("select a from Orders a where a.userOrder.username=?1")
	Orders find_ByUsername(String username);

	@Query("SELECT YEAR(b.date) as year, SUM(o.price * o.qty) as revenue FROM Orders b " +
			"JOIN OrderDetails o ON o.ordersId.id = b.id " +
			"GROUP BY YEAR(b.date)")
	List<Object[]> getRevenueByYear();

	@Query("SELECT MONTH(b.date) as month, SUM(o.price * o.qty) as revenue " +
			"FROM Orders b " +
			"JOIN OrderDetails o ON o.ordersId.id = b.id " +
			"WHERE YEAR(b.date) = YEAR(GETDATE()) " +
			"GROUP BY MONTH(b.date)")
	List<Object[]> getRevenueByMonth();

	@Query(value = "select * from Orders a join OrderDetails od on a.Id = od.OrderId where od.Id = ?1", nativeQuery = true)
	Orders findByOrderDtId(Integer id);

}
