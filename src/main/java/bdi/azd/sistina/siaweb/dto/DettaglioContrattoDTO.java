package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class DettaglioContrattoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long contrattoPk;
	
	private String cig;
	
	private String descrizione;
	
	private TipoCigDettaglioDTO tipoCig;
	
	private String motivazioneColl;
	
	private String cigPadre;
	
	private String motivoColl;
	
	private TipoContrattoDettaglioDTO tipoContratto;

	private StatoContrattoDettaglioDTO statoContratto;

	private BigDecimal importoMassimo;
	
	private StrutturaDTO strutturaResponsabile;
	
	private StrutturaDTO strutturaValidante;
	
	private List<StrutturaDTO> elencoStruttDestinataria;
	
	private TipoSubappaltoDettaglioDTO tipoSubappalto;

	private String noteStato;
	
	private ProgrammazioneDettaglioDTO programmazione;
	
	private List<RiferimentiDocumentaliDTO> riferimentiDocumentali;

	private List<CronologiaDettaglioDTO> cronologie;

	private List<FornitoreContrattoDettaglioDTO> fornitori;

	private List<AttoreDTO> attori;
	
	private List<IntegrazioneDettaglioDTO> integrazioni;
	
	private List<ImportoDettaglioDTO> importi;
	
	private CheckValidazioneIDTO checkValidazioneI;
}
