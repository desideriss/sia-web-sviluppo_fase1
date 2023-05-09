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
 * The persistent class for the TIPO_CONTRATTO database table.
 * 
 */
@Entity
@Table(name="TIPO_CONTRATTO", schema = "SISTINA")
@NamedQuery(name="TipoContratto.findAll", query="SELECT t FROM TipoContratto t")
public class TipoContratto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_CONTRATTO")
	private long idTpContratto;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_CONTRATTO")
	private String tipoContratto;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public TipoContratto() {
	}

	public long getIdTpContratto() {
		return this.idTpContratto;
	}

	public void setIdTpContratto(long idTpContratto) {
		this.idTpContratto = idTpContratto;
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

	public String getTipoContratto() {
		return this.tipoContratto;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}