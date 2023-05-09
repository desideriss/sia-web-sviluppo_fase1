package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class TipologiaFornitoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idTpFornitore;

	private Timestamp dataAggior;

	private String flagStato;

	private String tipoFornitore;

	private String useridAggior;

	private List<FornitoreContrattoDTO> fornitoreContrattos;


}
