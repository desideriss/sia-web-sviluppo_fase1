package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class AttoreContrattoModificaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idAttoreContratto;
	
	private RuoloAttoreDTO ruoloAttore;
	
	private String userId;
	
	private String nominativo;
	
	private BigDecimal codServAtt;

	private String denominServ;

	private Date dtInizioEvento;

	private String email;



	
}
