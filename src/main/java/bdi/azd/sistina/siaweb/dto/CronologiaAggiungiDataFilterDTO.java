package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CronologiaAggiungiDataFilterDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long idContratto;
	private Date dtInizioEvento;
	private long idTpCronologia;
	private String flagPresunta;
	private String motivazione;
	private Date dataInizioVal;
	private long idIntegrazione;
	private Date dtFineEvento;
}
