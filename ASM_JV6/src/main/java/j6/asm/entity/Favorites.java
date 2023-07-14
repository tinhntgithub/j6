package j6.asm.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Favorites", uniqueConstraints = { @UniqueConstraint(columnNames = { "[username]","[productid]" }) })
public class Favorites implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[id]")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name = "[username]")
	private Accounts userFvr;

	@ManyToOne()
	@JoinColumn(name = "[productid]")
	private Products productsId;

	@Column(name = "[likedate]")
	@Temporal(TemporalType.DATE)
	private Date likeDate = new Date();

	@Column(name = "[active]")
	private Boolean active = true;

}
