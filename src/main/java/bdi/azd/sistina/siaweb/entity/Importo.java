package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the IMPORTO database table.
 * 
 */
@Entity
@Table(name = "IMPORTO", schema = "SISTINA")
public class Importo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IMPORTO_IDIMPORTO_GENERATOR", sequenceName="SISTINA.SEQ_IMPORTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMPORTO_IDIMPORTO_GENERATOR")
	@Column(name="ID_IMPORTO")
	private Long idImporto;

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

	@Column(name="VALORE_IMP")
	private BigDecimal valoreImp;

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;

	//bi-directional many-to-one association to Integrazione
	@ManyToOne
	@JoinColumn(name="ID_INTEGRAZIONE")
	private Integrazione integrazione;

	//bi-directional many-to-one association to TipoImporto
	@ManyToOne
	@JoinColumn(name="ID_TIPO_IMPORTO")
	private TipoImporto tipoImporto;

	public Importo() {
	}
	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
		dataInizioVal=new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }

	public Long getIdImporto() {
		return this.idImporto;
	}

	public void setIdImporto(Long idImporto) {
		this.idImporto = idImporto;
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

	public BigDecimal getValoreImp() {
		return this.valoreImp;
	}

	public void setValoreImp(BigDecimal valoreImp) {
		this.valoreImp = valoreImp;
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

	public TipoImporto getTipoImporto() {
		return this.tipoImporto;
	}

	public void setTipoImporto(TipoImporto tipoImporto) {
		this.tipoImporto = tipoImporto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(tipoImporto, valoreImp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Importo other = (Importo) obj;
		return Objects.equals(tipoImporto, other.tipoImporto) && Objects.equals(valoreImp, other.valoreImp);
	}

}