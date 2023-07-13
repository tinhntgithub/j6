package dtn.asm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
	@NotBlank
	String username;
	@NotBlank
	String password;
	@NotBlank
	String password2;
	@NotBlank
	String fullname;
	@NotBlank
	@Email
	String email;
	String photo;
	@NotBlank
	String phone;
	Boolean active = true;
}
