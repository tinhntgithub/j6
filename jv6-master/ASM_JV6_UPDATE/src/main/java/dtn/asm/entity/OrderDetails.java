package dtn.asm.entity;

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
@Table(name = "Orderdetails", uniqueConstraints = { @UniqueConstraint(columnNames = { "[orderid]","[productid]","[color]" }) })
public class OrderDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "[orderid]")
	private Orders ordersId;

	@ManyToOne
	@JoinColumn(name = "[productid]")
	private Products productsId;

	@Column(name = "[price]")
	private Double price;

	@Column(name = "[quantity]")
	private Integer qty;

	@ManyToOne()
	@JoinColumn(name = "[color]")
	private ProductColor colorId;

}