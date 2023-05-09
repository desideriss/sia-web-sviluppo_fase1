package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
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
public class RiferimentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idRiferimento;

	private String codRiferimento;

	private Timestamp dataAggior;

	private Date dataVal;

	private String descrizione;

	private String link;

	private String useridAggior;

	private TipoRiferimentoDTO tipoRiferimento;


	

}
