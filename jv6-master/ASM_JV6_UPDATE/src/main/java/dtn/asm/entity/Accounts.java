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
@Table(name = "Accounts", uniqueConstraints = { @UniqueConstraint(columnNames = { "[email]" }),
		@UniqueConstraint(columnNames = { "[phone]" }) })
public class Accounts implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[username]")
	String username;

	@Column(name = "[password]")
	String password;

	@Column(name = "[fullname]")
	String Fullname;

	@Column(name = "[email]")
	String email;

	@Column(name = "[photo]")
	String photo;

	@Column(name = "[phone]")
	String phone;

	@Column(name = "[active]")
	Boolean active = true;

	@JsonIgnore
	@OneToMany(mappedBy = "userAuthor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Authorities> authorities;

	@JsonIgnore
	@OneToMany(mappedBy = "userCart")
	List<Cart> cart;

	@JsonIgnore
	@OneToMany(mappedBy = "userFvr")
	List<Favorites> favorites;

	@JsonIgnore
	@OneToMany(mappedBy = "userAr")
	List<Address> address;

	@JsonIgnore
	@OneToMany(mappedBy = "userOrder")
	List<Orders> order;

	@JsonIgnore
	@OneToMany(mappedBy = "userOtp")
	List<Otp> otp;

}