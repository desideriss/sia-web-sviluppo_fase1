package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the TIPO_PROCEDURA database table.
 * 
 */
@Entity
@Table(name="TIPO_PROCEDURA", schema = "SISTINA")
@NamedQuery(name="TipoProcedura.findAll", query="SELECT t FROM TipoProcedura t")
public class TipoProcedura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_PRO")
	private long idTipoPro;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_PROCEDURA")
	private String tipoProcedura;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public TipoProcedura() {
	}

	public long getIdTipoPro() {
		return this.idTipoPro;
	}

	public void setIdTipoPro(long idTipoPro) {
		this.idTipoPro = idTipoPro;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getTipoProcedura() {
		return this.tipoProcedura;
	}

	public void setTipoProcedura(String tipoProcedura) {
		this.tipoProcedura = tipoProcedura;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}