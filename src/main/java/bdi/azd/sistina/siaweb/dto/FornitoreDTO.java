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
public class FornitoreDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codiceFiscale;
	private String partitaIva;
	private String pmi;
	private String ragioneSociale;
	private Long idFornitore;
	private String useridAggior;
	private Timestamp dataAggior;
	
}
