package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FornitoreFilterDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String ragioneSociale;
	private String codiceFiscale;
	private String partitaIva;
	
}
