package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class AttoreContrattoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idAttoreContratto;
	
	private long idContratto;

	private BigDecimal codServAtt;

	private Timestamp dataAggior;

	private Timestamp dataFineVal;

	private Timestamp dataInizioVal;

	private String denominServ;

	private Date dtFineEvento;

	private Date dtInizioEvento;

	private String useridAggior;

	private AttoreDTO attore;

	private RuoloAttoreDTO ruoloAttoreBean;
	
}
