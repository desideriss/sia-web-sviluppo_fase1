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

public class ContrattoFilterDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer statoId;
	private Integer cigId;
	private String cig;
	private Date dataStipula;
	private Date dataDecorrenza;
	private Date dataPrimaScadenza;
	private Date dataUltimaScadenza;
	private String codiceProceduraOrigine;
	private String codiceProceduraRinnovo;
	private String ragioneSocialeFornitore;
	private String rupNominativo;
	private String strutturaValidante;
	private String strutturaResponsabile;
	private String strutturaDestinataria;
	private String cigPadre;
	private String comparto;
	//aggiunto il 15/11 per bug fix
	private String idStContratto;
	private String descrizione;
	
}
