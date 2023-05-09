package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class ContrattoIntegrazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idContratto;


	private String cig;

	private String cigPadre;

	private List<IntegrazioneFullDTO> integraziones;

	
}
