package j6.asm.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orderdetails", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "[orderid]", "[productid]", "[color]" }) })
public class OrderDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	// @JsonIgnore // Ngăn chuyển đổi thuộc tính ordersId thành JSON
	@ManyToOne
	@JoinColumn(name = "[orderid]")
	private Orders ordersId;

	// @JsonIgnore // Ngăn chuyển đổi thuộc tính productsId thành JSON
	@ManyToOne
	@JoinColumn(name = "[productid]")
	private Products productsId;

	@Column(name = "[price]")
	private Double price;

	@Column(name = "[quantity]")
	private Integer qty;

	// @JsonIgnore // Ngăn chuyển đổi thuộc tính colorId thành JSON
	@ManyToOne()
	@JoinColumn(name = "[color]")
	private ProductColor colorId;

}