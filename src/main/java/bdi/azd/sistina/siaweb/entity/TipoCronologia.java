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
 * The persistent class for the TIPO_CRONOLOGIA database table.
 * 
 */
@Entity
@Table(name="TIPO_CRONOLOGIA", schema = "SISTINA")
@NamedQuery(name="TipoCronologia.findAll", query="SELECT t FROM TipoCronologia t")
public class TipoCronologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_CRONOLOGIA")
	private long idTpCronologia;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_CRONOLOGIA")
	private String tipoCronologia;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public TipoCronologia() {
	}

	public long getIdTpCronologia() {
		return this.idTpCronologia;
	}

	public void setIdTpCronologia(long idTpCronologia) {
		this.idTpCronologia = idTpCronologia;
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

	public String getTipoCronologia() {
		return this.tipoCronologia;
	}

	public void setTipoCronologia(String tipoCronologia) {
		this.tipoCronologia = tipoCronologia;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}