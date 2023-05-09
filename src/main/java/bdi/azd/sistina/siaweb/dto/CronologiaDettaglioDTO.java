package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CronologiaDettaglioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private TipoCronologiaDettaglioDTO evento;
	
	private Long idCronologia;

	private Date dtInizioEvento;
	
	private Date dtFineEvento;
	
	private String dataPresunta;
	
	private String motivazione;
	
	private String integrazione;


}
