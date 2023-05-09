package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class AnagrafeStrutturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idStruttura;
	private BigDecimal codiceStruttura;
	private String denominazione;
	private BigDecimal codSiparium;
}
