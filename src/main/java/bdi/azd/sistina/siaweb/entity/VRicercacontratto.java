package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the V_RICERCACONTRATTO database table.
 * 
 */
@Entity
@Table(name="V_RICERCACONTRATTO", schema = "SISTINA")
@NamedQuery(name="VRicercacontratto.findAll", query="SELECT v FROM VRicercacontratto v")
public class VRicercacontratto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cig;

	@Column(name="CIG_PADRE")
	private String cigPadre;

	@Column(name="COD_PROCEDURA")
	private String codProcedura;

	private String comparto;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_EVENTO")
	private Date dataInizioEvento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_EVENTO")
	private Date dataFineEvento;

	private String denominazione;

	@Column(name="FLAG_VALIDANTE")
	private BigDecimal flagValidante;

	@Column(name="ID_PROC_ORG")
	private BigDecimal idProcOrg;

	@Column(name="ID_PROC_RIN")
	private BigDecimal idProcRin;

	@Column(name="ID_PROCEDURA")
	private BigDecimal idProcedura;

	@Column(name="ID_ST_CONTRATTO")
	private BigDecimal idStContratto;

	@Column(name="ID_TIP_CRONOL")
	private BigDecimal idTipCronol;

	@Column(name="ID_TIPO_CIG")
	private BigDecimal idTipoCig;

	private String nominativo;

	@Column(name="RAGIONE_SOCIALE")
	private String ragioneSociale;

	@Column(name="RUOLO_ATTORE")
	private BigDecimal ruoloAttore;

	@Column(name="TIPO_FORNITORE")
	private BigDecimal tipoFornitore;

	@Column(name="ID_RUOLO_STR")
	private BigDecimal idRuoloStr;

	@Column(name="ID_STRUTTURA")
	private BigDecimal idStruttura;

	@Column(name="STRUTTURA")
	private String struttura;
	
	@Id
	@GeneratedValue
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private byte[] uuid;
	
	@Column(name="CODICE_PROCEDURA_ORIGINE")
	private String codProcOrg;
	
	public String getCodProcOrg() {
		return codProcOrg;
	}

	public void setCodProcOrg(String codProcOrg) {
		this.codProcOrg = codProcOrg;
	}

	public String getCodProcRin() {
		return codProcRin;
	}

	public void setCodProcRin(String codProcRin) {
		this.codProcRin = codProcRin;
	}

	public Date getDataPrimaScadenza() {
		return dataPrimaScadenza;
	}

	public void setDataPrimaScadenza(Date dataPrimaScadenza) {
		this.dataPrimaScadenza = dataPrimaScadenza;
	}

	public Date getDataUltimaScadenza() {
		return dataUltimaScadenza;
	}

	public void setDataUltimaScadenza(Date dataUltimaScadenza) {
		this.dataUltimaScadenza = dataUltimaScadenza;
	}

	@Column(name="CODICE_PROCEDURA_RINNOVO")
	private String codProcRin;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_PRIMA_SCADENZA")
	private Date dataPrimaScadenza;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ULTIMA_SCADENZA")
	private Date dataUltimaScadenza;

	public VRicercacontratto() {
	}

	public String getCig() {
		return this.cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCigPadre() {
		return this.cigPadre;
	}

	public void setCigPadre(String cigPadre) {
		this.cigPadre = cigPadre;
	}

	public String getCodProcedura() {
		return this.codProcedura;
	}

	public void setCodProcedura(String codProcedura) {
		this.codProcedura = codProcedura;
	}

	public String getComparto() {
		return this.comparto;
	}

	public void setComparto(String comparto) {
		this.comparto = comparto;
	}


	public Date getDataInizioEvento() {
		return dataInizioEvento;
	}

	public void setDataInizioEvento(Date dataInizioEvento) {
		this.dataInizioEvento = dataInizioEvento;
	}

	public Date getDataFineEvento() {
		return dataFineEvento;
	}

	public void setDataFineEvento(Date dataFineEvento) {
		this.dataFineEvento = dataFineEvento;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public BigDecimal getFlagValidante() {
		return this.flagValidante;
	}

	public void setFlagValidante(BigDecimal flagValidante) {
		this.flagValidante = flagValidante;
	}

	public BigDecimal getIdProcOrg() {
		return this.idProcOrg;
	}

	public void setIdProcOrg(BigDecimal idProcOrg) {
		this.idProcOrg = idProcOrg;
	}

	public BigDecimal getIdProcRin() {
		return this.idProcRin;
	}

	public void setIdProcRin(BigDecimal idProcRin) {
		this.idProcRin = idProcRin;
	}

	public BigDecimal getIdProcedura() {
		return this.idProcedura;
	}

	public void setIdProcedura(BigDecimal idProcedura) {
		this.idProcedura = idProcedura;
	}

	public BigDecimal getIdStContratto() {
		return this.idStContratto;
	}

	public void setIdStContratto(BigDecimal idStContratto) {
		this.idStContratto = idStContratto;
	}

	public BigDecimal getIdTipCronol() {
		return this.idTipCronol;
	}

	public void setIdTipCronol(BigDecimal idTipCronol) {
		this.idTipCronol = idTipCronol;
	}

	public BigDecimal getIdTipoCig() {
		return this.idTipoCig;
	}

	public void setIdTipoCig(BigDecimal idTipoCig) {
		this.idTipoCig = idTipoCig;
	}

	public String getNominativo() {
		return this.nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public BigDecimal getRuoloAttore() {
		return this.ruoloAttore;
	}

	public void setRuoloAttore(BigDecimal ruoloAttore) {
		this.ruoloAttore = ruoloAttore;
	}

	public BigDecimal getTipoFornitore() {
		return this.tipoFornitore;
	}

	public void setTipoFornitore(BigDecimal tipoFornitore) {
		this.tipoFornitore = tipoFornitore;
	}

	public byte[] getUuid() {
		return this.uuid;
	}

	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getIdRuoloStr() {
		return idRuoloStr;
	}

	public void setIdRuoloStr(BigDecimal idRuoloStr) {
		this.idRuoloStr = idRuoloStr;
	}

	public BigDecimal getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(BigDecimal idStruttura) {
		this.idStruttura = idStruttura;
	}

	public String getStruttura() {
		return struttura;
	}

	public void setStruttura(String struttura) {
		this.struttura = struttura;
	}

	

	
}