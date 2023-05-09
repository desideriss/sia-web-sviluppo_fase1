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
public class CampoNonValidoDTO implements Serializable {

	private static final long serialVersionUID = -8275709962430134745L;

	private String dato;
	
	private String areaDati;
	
}
