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
public class ModificaContrattoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idContratto;
	
	private String cig;
	
	private String descrizione;
	
	private String cigPadre;
	
	private Long tipoContratto;
	
	private Long tipoSubappalto;

	private String note;
	
	private List<RiferimentoDTO> riferimento;
	
	private String proceduraOrigine;
	
	private String proceduraRinnovo;
	
	private List<CronologiaUpdateFilterDTO> cronologie;
	
	private List<ImportoModificaDTO> importi;
	
	private List<ContrattoStrutturaModificaDTO> contrattoStruttura;
	
	private List<AttoreContrattoModificaDTO> attoreContrattos;
	
	private List<FornitoreContrattoModificaDTO> fornitoreContratto;
}
