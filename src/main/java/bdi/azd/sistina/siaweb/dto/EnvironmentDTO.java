package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnvironmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	 private String  tokenName;
	 private String  environment;
	 private String  baseApi;
	 private String  redirectLogOut;

}
