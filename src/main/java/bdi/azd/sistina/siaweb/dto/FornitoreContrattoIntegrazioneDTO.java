package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class FornitoreContrattoIntegrazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idFornitoreContratto;
	
	private long idContratto;

	private Timestamp dataAggior;

	private Timestamp dataFineVal;

	private Timestamp dataInizioVal;

	private Date dtFineEvento;

	private Date dtInizioEvento;

	private BigDecimal idRaggruppamento;

	private String useridAggior;

	private FornitoreDTO fornitore;

	private RuoloFornitoreDTO ruoloFornitore;

	private TipologiaFornitoreDTO tipologiaFornitore;
	
	
	
}
