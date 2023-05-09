package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.util.List;

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
public class CheckValidazioneIDTO implements Serializable {
	
	private static final long serialVersionUID = -616656947050809976L;

	private boolean valido;
	
	private boolean storicoEssenzValido;
	
	private List<CampoNonValidoDTO> campiNonValidi;

}
