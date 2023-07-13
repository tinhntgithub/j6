package dtn.asm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pricehistory")
public class PriceHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	@ManyToOne()
	@JoinColumn(name = "[productid]")
	private Products product;

	@Column(name = "[price]")
	private Double price;

	@Column(name = "[sale]")
	private Double sale;

	@Column(name = "[changedate]")
	private Date date = new Date();
}
