package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class StoricoEssenzValDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idContrattoEssenz;

	private DatiEssenzialiDTO datiEssenzialiI;

	private String valoreOld;

	private String valoreNew;
	
	private String statoAccettaz;

	private String motivazione;

	private Timestamp dataAggior;

	private String useridAggior;
}
