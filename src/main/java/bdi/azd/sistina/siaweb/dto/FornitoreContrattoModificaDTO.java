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
public class FornitoreContrattoModificaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idFornitoreContratto;

	private Timestamp dataInizioVal;

	private Long ruoloFornitore;

	private Long tipologiaFornitore;
	
}
