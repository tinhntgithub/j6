package dtn.asm.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassForm {
	@NotBlank
	String password;
	@NotBlank
	String newpassword;
	@NotBlank
	String confirmpassword;
}
