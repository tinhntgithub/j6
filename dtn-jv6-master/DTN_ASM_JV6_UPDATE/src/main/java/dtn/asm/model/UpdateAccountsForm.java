package dtn.asm.model;

import javax.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountsForm {
	@NotBlank
	private String fullname;
	@NotBlank
	@Email
	private String email;
	private String photo;
	@NotBlank
	@Pattern(regexp = "({0}|^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$)")
	private String phone;
}
