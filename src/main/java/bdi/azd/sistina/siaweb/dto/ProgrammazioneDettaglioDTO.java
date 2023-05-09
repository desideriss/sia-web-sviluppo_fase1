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
public class ProgrammazioneDettaglioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String comparto;

	private String categoriaMerceologica;
	
	private String proceduraOrigine;
	
	private String proceduraRinnovo;
	
	private String ricorrente;


	

}
