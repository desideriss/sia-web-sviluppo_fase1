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
 * The persistent class for the ATTORE_CONTRATTO database table.
 * 
 */
@Entity
@Table(name="ATTORE_CONTRATTO", schema = "SISTINA")
@NamedQuery(name="AttoreContratto.findAll", query="SELECT a FROM AttoreContratto a")
public class AttoreContratto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ATTORE_CONTRATTO_IDATTORECONTRATTO_GENERATOR", sequenceName="SISTINA.SEQ_ATTORE_CONTRATTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTORE_CONTRATTO_IDATTORECONTRATTO_GENERATOR")
	@Column(name="ID_ATTORE_CONTRATTO")
	private long idAttoreContratto;

	@Column(name="COD_SERV_ATT")
	private BigDecimal codServAtt;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="DATA_FINE_VAL")
	private Timestamp dataFineVal;

	@Column(name="DATA_INIZIO_VAL")
	private Timestamp dataInizioVal;

	@Column(name="DENOMIN_SERV")
	private String denominServ;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_EVENTO")
	private Date dtFineEvento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_EVENTO")
	private Date dtInizioEvento;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;
	
	@Column(name="DEC_DL_PRESENTE")
	private String decDlPresente;
	
	//bi-directional many-to-one association to Attore
	@ManyToOne
	@JoinColumn(name="ID_ATTORE")
	private Attore attore;

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;

	//bi-directional many-to-one association to RuoloAttore
	@ManyToOne
	@JoinColumn(name="RUOLO_ATTORE")
	private RuoloAttore ruoloAttoreBean;

	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
		dataInizioVal = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }

	public AttoreContratto() {
	}

	public long getIdAttoreContratto() {
		return this.idAttoreContratto;
	}

	public void setIdAttoreContratto(long idAttoreContratto) {
		this.idAttoreContratto = idAttoreContratto;
	}

	public BigDecimal getCodServAtt() {
		return this.codServAtt;
	}

	public void setCodServAtt(BigDecimal codServAtt) {
		this.codServAtt = codServAtt;
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

	public String getDenominServ() {
		return this.denominServ;
	}

	public void setDenominServ(String denominServ) {
		this.denominServ = denominServ;
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

	public Attore getAttore() {
		return this.attore;
	}

	public void setAttore(Attore attore) {
		this.attore = attore;
	}

	public Contratto getContratto() {
		return this.contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public RuoloAttore getRuoloAttoreBean() {
		return this.ruoloAttoreBean;
	}

	public void setRuoloAttoreBean(RuoloAttore ruoloAttoreBean) {
		this.ruoloAttoreBean = ruoloAttoreBean;
	}

	public String getDecDlPresente() {
		return decDlPresente;
	}

	public void setDecDlPresente(String decDlPresente) {
		this.decDlPresente = decDlPresente;
	}
	
	

}