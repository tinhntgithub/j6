package dtn.asm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Otp")
public class Otp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "[id]")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "[userid]")
	private Accounts userOtp;

	@Column(name = "[otp]")
	private String otp;

	@Temporal(TemporalType.DATE)
	@Column(name = "[time]")
	private Date time = new Date();

}
