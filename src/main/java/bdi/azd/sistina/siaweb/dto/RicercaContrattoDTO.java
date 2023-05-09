package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RicercaContrattoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codProcOrg;
	private String codProcRin;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dataPrimaScadenza;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dataUltimaScadenza;
	private String cig;
	private String tipoFornitore;
	private String denominazione;
	@Transient
	private List<RicercaStrutturaDTO> strutturas;
	@Transient
	private List<String> ragioniSociali;
	@JsonIgnore
	private String cigPadre;
	@JsonIgnore
	private String codProcedura;
	@JsonIgnore
	private String comparto;
	@JsonIgnore
	private Date dataInizioEvento;
	@JsonIgnore
	private Date dataFineEvento;
	@JsonIgnore
	private BigDecimal flagValidante;
	@JsonIgnore
	private BigDecimal idProcOrg;
	@JsonIgnore
	private BigDecimal idProcRin;
	@JsonIgnore
	private BigDecimal idProcedura;
	@JsonIgnore
	private BigDecimal idRuoloStr;
	@JsonIgnore
	private BigDecimal idStContratto;
	@JsonIgnore
	private BigDecimal idStruttura;
	@JsonIgnore
	private BigDecimal idTipCronol;
	@JsonIgnore
	private BigDecimal idTipoCig;
	@JsonIgnore
	private String nominativo;
	@JsonIgnore
	private String ragioneSociale;
	@JsonIgnore
	private BigDecimal ruoloAttore;
	@JsonIgnore
	private String struttura;
	
}
