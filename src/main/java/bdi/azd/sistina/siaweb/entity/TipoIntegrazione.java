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
 * The persistent class for the TIPO_INTEGRAZIONE database table.
 * 
 */
@Entity
@Table(name="TIPO_INTEGRAZIONE", schema = "SISTINA")
@NamedQuery(name="TipoIntegrazione.findAll", query="SELECT t FROM TipoIntegrazione t")
public class TipoIntegrazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_INTEGRAZ")
	private long idTpIntegraz;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_INTEGR")
	private String tipoIntegr;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public TipoIntegrazione() {
	}

	public long getIdTpIntegraz() {
		return this.idTpIntegraz;
	}

	public void setIdTpIntegraz(long idTpIntegraz) {
		this.idTpIntegraz = idTpIntegraz;
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

	public String getTipoIntegr() {
		return this.tipoIntegr;
	}

	public void setTipoIntegr(String tipoIntegr) {
		this.tipoIntegr = tipoIntegr;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}