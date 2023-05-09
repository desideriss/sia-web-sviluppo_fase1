package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ImportoModificaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idImporto;
	
	private Date dtInizioEvento;

	private String useridAggior;

	private BigDecimal valoreImp;
	
	//private IntegrazioneDTO integrazione;

	private TipoImportoDTO tipoImporto;

	

}
