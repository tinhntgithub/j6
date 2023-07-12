package dtn.asm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cart", uniqueConstraints = { @UniqueConstraint(columnNames = { "[username]","[productid]","[color]" }) })
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "[username]")
	private Accounts userCart;

	@ManyToOne
	@JoinColumn(name = "[productid]")
	private Products proCart;

	@Column(name = "[quantity]")
	private Integer qty=1;

	@Column(name = "[price]")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "[color]")
	private ProductColor colorCart;
}
