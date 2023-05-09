package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the TIPO_IMPORTO database table.
 * 
 */
@Entity
@Table(name="TIPO_IMPORTO", schema = "SISTINA")
@NamedQuery(name="TipoImporto.findAll", query="SELECT t FROM TipoImporto t")
public class TipoImporto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIP_IMPORTO")
	private long idTipImporto;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_IMPORTO")
	private String tipoImporto;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public TipoImporto() {
	}

	public long getIdTipImporto() {
		return this.idTipImporto;
	}

	public void setIdTipImporto(long idTipImporto) {
		this.idTipImporto = idTipImporto;
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

	public String getTipoImporto() {
		return this.tipoImporto;
	}

	public void setTipoImporto(String tipoImporto) {
		this.tipoImporto = tipoImporto;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTipImporto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoImporto other = (TipoImporto) obj;
		return idTipImporto == other.idTipImporto;
	}

}