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
public class StrutturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idStruttura;
	
	private String denominazione;
	
	private BigDecimal codiceStruttura;
	
	private BigDecimal codSiparium;
	
	private Date dtFineEvento;
	
}
