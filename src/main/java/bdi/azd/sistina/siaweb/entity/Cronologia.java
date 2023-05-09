package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bdi.azd.sistina.siaweb.util.UserUtil;

/**
 * The persistent class for the CRONOLOGIA database table.
 * 
 */
@Entity
@Table(name = "CRONOLOGIA", schema = "SISTINA")
public class Cronologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CRONOLOGIA_IDCRONOLOGIA_GENERATOR", sequenceName = "SISTINA.SEQ_CRONOLOGIA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRONOLOGIA_IDCRONOLOGIA_GENERATOR")
	@Column(name = "ID_CRONOLOGIA")
	private long idCronologia;

	@Column(name = "DATA_AGGIOR")
	private Timestamp dataAggior;

	private Date dtInizioEvento;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_EVENTO")
	private Date dtFineEvento;

	@Column(name = "DATA_FINE_VAL")
	private Timestamp dataFineVal;

	@Column(name = "DATA_INIZIO_VAL")
	private Timestamp dataInizioVal;

	@Column(name = "FLAG_PRESUNTA")
	private String flagPresunta;

	private String motivazione;

	@Column(name = "USERID_AGGIOR")
	private String useridAggior;

	// bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name = "ID_CONTRATTO")
	private Contratto contratto;

	// bi-directional many-to-one association to Integrazione
	@ManyToOne
	@JoinColumn(name = "ID_INTEGRAZIONE")
	private Integrazione integrazione;

	// bi-directional many-to-one association to TipoCronologia
	@ManyToOne
	@JoinColumn(name = "ID_TIP_CRONOL")
	private TipoCronologia tipoCronologia;

	public Cronologia() {
	}

	@PrePersist
	public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
		dataInizioVal = new Timestamp(System.currentTimeMillis());
		useridAggior = UserUtil.getLoggedUserId();
	}

	public long getIdCronologia() {
		return this.idCronologia;
	}

	public void setIdCronologia(long idCronologia) {
		this.idCronologia = idCronologia;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public Date getDtInizioEvento() {
		return this.dtInizioEvento;
	}

	public void setDtInizioEvento(Date dtInizioEvento) {
		this.dtInizioEvento = dtInizioEvento;
	}

	public Date getDtFineEvento() {
		return this.dtFineEvento;
	}

	public void setDtFineEvento(Date dtFineEvento) {
		this.dtFineEvento = dtFineEvento;
	}

	public Timestamp getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Timestamp dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Timestamp getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Timestamp dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getFlagPresunta() {
		return this.flagPresunta;
	}

	public void setFlagPresunta(String flagPresunta) {
		this.flagPresunta = flagPresunta;
	}

	public String getMotivazione() {
		return this.motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public Contratto getContratto() {
		return this.contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public Integrazione getIntegrazione() {
		return this.integrazione;
	}

	public void setIntegrazione(Integrazione integrazione) {
		this.integrazione = integrazione;
	}

	public TipoCronologia getTipoCronologia() {
		return this.tipoCronologia;
	}

	public void setTipoCronologia(TipoCronologia tipoCronologia) {
		this.tipoCronologia = tipoCronologia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flagPresunta, motivazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cronologia other = (Cronologia) obj;
		return Objects.equals(flagPresunta, other.flagPresunta) && Objects.equals(motivazione, other.motivazione);
	}

}