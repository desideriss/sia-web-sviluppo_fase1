package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bdi.azd.sistina.siaweb.util.UserUtil;


/**
 * The persistent class for the FORNITORE_CONTRATTO database table.
 * 
 */
@Entity
@Table(name="FORNITORE_CONTRATTO", schema = "SISTINA")
@NamedQuery(name="FornitoreContratto.findAll", query="SELECT f FROM FornitoreContratto f")
public class FornitoreContratto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FORNITORE_CONTRATTO_IDFORNITORECONTRATTO_GENERATOR", sequenceName="SISTINA.SEQ_FORNITORE_CONTRATTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORNITORE_CONTRATTO_IDFORNITORECONTRATTO_GENERATOR")
	@Column(name="ID_FORNITORE_CONTRATTO")
	private long idFornitoreContratto;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="DATA_FINE_VAL")
	private Timestamp dataFineVal;

	@Column(name="DATA_INIZIO_VAL")
	private Timestamp dataInizioVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_EVENTO")
	private Date dtFineEvento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_EVENTO")
	private Date dtInizioEvento;

	@Column(name="ID_RAGGRUPPAMENTO")
	private BigDecimal idRaggruppamento;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;

	//bi-directional many-to-one association to Fornitore
	@ManyToOne
	@JoinColumn(name="ID_FORNITORE")
	private Fornitore fornitore;

	//bi-directional many-to-one association to Integrazione
	@ManyToOne
	@JoinColumn(name="ID_INTEGRAZIONE")
	private Integrazione integrazione;

	//bi-directional many-to-one association to RuoloFornitore
	@ManyToOne
	@JoinColumn(name="RUOLO_FORNITORE")
	private RuoloFornitore ruoloFornitore;

	//bi-directional many-to-one association to TipologiaFornitore
	@ManyToOne
	@JoinColumn(name="TIPO_FORNITORE")
	private TipologiaFornitore tipologiaFornitore;
	
	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
		dataInizioVal = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }

	public FornitoreContratto() {
	}

	public long getIdFornitoreContratto() {
		return this.idFornitoreContratto;
	}

	public void setIdFornitoreContratto(long idFornitoreContratto) {
		this.idFornitoreContratto = idFornitoreContratto;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
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

	public Date getDtFineEvento() {
		return this.dtFineEvento;
	}

	public void setDtFineEvento(Date dtFineEvento) {
		this.dtFineEvento = dtFineEvento;
	}

	public Date getDtInizioEvento() {
		return this.dtInizioEvento;
	}

	public void setDtInizioEvento(Date dtInizioEvento) {
		this.dtInizioEvento = dtInizioEvento;
	}

	public BigDecimal getIdRaggruppamento() {
		return this.idRaggruppamento;
	}

	public void setIdRaggruppamento(BigDecimal idRaggruppamento) {
		this.idRaggruppamento = idRaggruppamento;
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

	public Fornitore getFornitore() {
		return this.fornitore;
	}

	public void setFornitore(Fornitore fornitore) {
		this.fornitore = fornitore;
	}

	public Integrazione getIntegrazione() {
		return this.integrazione;
	}

	public void setIntegrazione(Integrazione integrazione) {
		this.integrazione = integrazione;
	}

	public RuoloFornitore getRuoloFornitore() {
		return this.ruoloFornitore;
	}

	public void setRuoloFornitore(RuoloFornitore ruoloFornitore) {
		this.ruoloFornitore = ruoloFornitore;
	}

	public TipologiaFornitore getTipologiaFornitore() {
		return this.tipologiaFornitore;
	}

	public void setTipologiaFornitore(TipologiaFornitore tipologiaFornitore) {
		this.tipologiaFornitore = tipologiaFornitore;
	}

}