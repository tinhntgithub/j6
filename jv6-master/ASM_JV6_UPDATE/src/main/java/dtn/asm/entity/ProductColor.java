package dtn.asm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Productcolor", uniqueConstraints = { @UniqueConstraint(columnNames = { "productId", "colorId" }) })
public class ProductColor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "[productid]")
	private Products products;

	@ManyToOne
	@JoinColumn(name = "[colorid]")
	private Color color;

	@JsonIgnore
	@OneToMany(mappedBy = "colorId")
	List<OrderDetails> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "colorCart")
	List<Cart> carts;
}