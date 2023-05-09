package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the STRUTTURA_RESP_DEST database table.
 * 
 */
@Entity
@Table(name="STRUTTURA_RESP_DEST", schema = "SISTINA")
@NamedQuery(name="StrutturaRespDest.findAll", query="SELECT s FROM StrutturaRespDest s")
public class StrutturaRespDest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RESP_DEST")
	private long idRespDest;

	@Column(name="COD_RESP_DEST")
	private BigDecimal codRespDest;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="DESC_RESP_DESC")
	private String descRespDesc;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public StrutturaRespDest() {
	}

	public long getIdRespDest() {
		return this.idRespDest;
	}

	public void setIdRespDest(long idRespDest) {
		this.idRespDest = idRespDest;
	}

	public BigDecimal getCodRespDest() {
		return this.codRespDest;
	}

	public void setCodRespDest(BigDecimal codRespDest) {
		this.codRespDest = codRespDest;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDescRespDesc() {
		return this.descRespDesc;
	}

	public void setDescRespDesc(String descRespDesc) {
		this.descRespDesc = descRespDesc;
	}

	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}