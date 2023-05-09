package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
public class ContrattoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idContratto;

	private String catMerceologica;
	
	private String motivazioneCollegamento;

	private String cig;

	private String cigPadre;

	private String comparto;

	private Timestamp dataAggior;

	private Date dataInserimento;

	private String descrizioneCrtt;

	private String noteStato;

	private String ricorrente;

	private String useridAggior;

	private List<AttoreContrattoDTO> attoreContrattos;

	private ProceduraDTO proceduraOrg;

	private ProceduraDTO proceduraRin;

	private StatoContrattoDTO statoContratto;

	private StatoProcessoDTO statoProcesso;

	private TipoCigDTO tipoCig;

	private TipoContrattoDTO tipoContratto;

	private TipoSubappaltoDTO tipoSubappalto;

	private List<ContrattoStrutturaDTO> contrattoStrutturas;

	private List<CronologiaDTO> cronologias;

	private List<FornitoreContrattoDTO> fornitoreContrattos;

	private List<ImportoDTO> importos;


	private List<IntegrazioneFullDTO> integraziones;

	private List<RiferimentoDTO> riferimentos;
	
}
