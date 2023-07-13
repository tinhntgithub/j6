package dtn.asm.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Table(name = "Sale")
public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[id]")
	private Integer id;

	@Column(name = "[code]")
	private String code;
	
	@Column(name = "[value]")
	private Double value;
	
	@Column(name = "[saledate]")
	private Date saleDate;
	
	
	@Column(name = "[amount]")
	private Integer amount;
	
	@Column(name = "[createdate]")
	private Date createDate = new Date();
	
	@JsonIgnore
	@OneToMany(mappedBy = "saleId")
	List<Orders> saleOrders;
	
}