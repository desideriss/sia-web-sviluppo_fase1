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
public class FornitoreContrattoDettaglioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long fornitoreContrattoPk;
	
	private String codiceFiscale;
	
	private long ruoloFornitore;
	
	private String partitaIva;
	
	private String pmi;
	
	private String ragioneSociale;
	
	private TipoFornitoreDettaglioDTO tipoFornitore;

	private RuoloFornitoreDTO ruoloFornitoreBean;
	
	private Date inizioEvento;
	
	private String integrazione;

}
