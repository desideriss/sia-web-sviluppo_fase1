package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.util.Date;

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
public class RiferimentiDocumentaliDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idRiferimento;
	
	private TipoRiferimentoDettaglioDTO tipoRiferimento;

	private String codRiferimento;
	
	private String descrizione;
	
	private Date dataValidita;
	
	private String link;


	

}
