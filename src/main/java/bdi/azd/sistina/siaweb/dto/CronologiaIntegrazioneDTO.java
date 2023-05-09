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
public class CronologiaIntegrazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idCronologia;

	private Timestamp dataAggior;

	private Date dtInizioEvento;
	
	private Date dtFineEvento;

	private Timestamp dataFineVal;

	private Timestamp dataInizioVal;

	private String flagPresunta;

	private String motivazione;

	private String useridAggior;

	private TipoCronologiaDTO tipoCronologia;


}
