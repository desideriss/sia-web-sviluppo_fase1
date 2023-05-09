package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class ImportoIntegrazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idImporto;

	private Timestamp dataAggior;

	private Timestamp dataFineVal;

	private Timestamp dataInizioVal;
	
	private Date dtInizioEvento;

	private String useridAggior;

	private BigDecimal valoreImp;

	private TipoImportoDTO tipoImporto;
	

	

}
