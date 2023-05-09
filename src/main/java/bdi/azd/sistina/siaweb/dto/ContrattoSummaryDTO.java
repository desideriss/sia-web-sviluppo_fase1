package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class ContrattoSummaryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String cig;
	
	private TipoCigDettaglioDTO tipoCig;
	
	private String cigPadre;
	
	private String descrizione;
	
	private TipoContrattoDettaglioDTO tipoContratto;

	private BigDecimal importoMassimo;
	
}
