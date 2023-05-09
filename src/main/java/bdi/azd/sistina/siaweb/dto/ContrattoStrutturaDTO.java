package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ContrattoStrutturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idContrattoStruttura;

	private Timestamp dataAggior;

	private Timestamp dataFineVal;

	private Timestamp dataInizioVal;

	
	private Date dtFineEvento;

	private Date dtInizioEvento;

	private String useridAggior;

	private AnagrafeStrutturaDTO anagrafeStruttura;

	private RuoloStrutturaDTO ruoloStruttura;
	
}
