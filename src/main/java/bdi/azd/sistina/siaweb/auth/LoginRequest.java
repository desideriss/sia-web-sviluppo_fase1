package bdi.azd.sistina.siaweb.auth;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
