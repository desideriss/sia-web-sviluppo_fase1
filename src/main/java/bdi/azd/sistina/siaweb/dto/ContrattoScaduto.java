package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContrattoScaduto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cig;

    private String cigPadre;

    private String tipoCig;

    private String tipoContratto;

    private String denominazione;

    private BigDecimal importoMassimo;

}
