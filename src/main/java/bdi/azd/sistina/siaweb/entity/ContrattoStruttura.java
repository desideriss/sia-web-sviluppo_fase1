package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
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
 * The persistent class for the CONTRATTO_STRUTTURA database table.
 * 
 */
@Entity
@Table(name="CONTRATTO_STRUTTURA", schema = "SISTINA")
@NamedQuery(name="ContrattoStruttura.findAll", query="SELECT c FROM ContrattoStruttura c")
public class ContrattoStruttura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRATTO_STRUTTURA_IDCONTRATTOSTRUTTURA_GENERATOR", sequenceName="SISTINA.SEQ_CONTRATTO_STRUTTURA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATTO_STRUTTURA_IDCONTRATTOSTRUTTURA_GENERATOR")
	@Column(name="ID_CONTRATTO_STRUTTURA")
	private long idContrattoStruttura;

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

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to AnagrafeStruttura
	@ManyToOne
	@JoinColumn(name="ID_STRUTTURA")
	private AnagrafeStruttura anagrafeStruttura;

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;

	//bi-directional many-to-one association to RuoloStruttura
	@ManyToOne
	@JoinColumn(name="ID_RUOLO_STR")
	private RuoloStruttura ruoloStruttura;

	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
		dataInizioVal=new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }
	
	public ContrattoStruttura() {
	}

	public long getIdContrattoStruttura() {
		return this.idContrattoStruttura;
	}

	public void setIdContrattoStruttura(long idContrattoStruttura) {
		this.idContrattoStruttura = idContrattoStruttura;
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

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public AnagrafeStruttura getAnagrafeStruttura() {
		return this.anagrafeStruttura;
	}

	public void setAnagrafeStruttura(AnagrafeStruttura anagrafeStruttura) {
		this.anagrafeStruttura = anagrafeStruttura;
	}

	public Contratto getContratto() {
		return this.contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public RuoloStruttura getRuoloStruttura() {
		return this.ruoloStruttura;
	}

	public void setRuoloStruttura(RuoloStruttura ruoloStruttura) {
		this.ruoloStruttura = ruoloStruttura;
	}
	
	

}