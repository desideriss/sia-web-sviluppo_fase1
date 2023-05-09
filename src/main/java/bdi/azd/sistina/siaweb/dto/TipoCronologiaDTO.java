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
public class TipoCronologiaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idTpCronologia;

	private Timestamp dataAggior;

	private String flagStato;

	private String tipoCronologia;

	private String useridAggior;

}
