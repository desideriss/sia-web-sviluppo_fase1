package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the TIPO_SUBAPPALTO database table.
 * 
 */
@Entity
@Table(name="TIPO_SUBAPPALTO", schema = "SISTINA")
@NamedQuery(name="TipoSubappalto.findAll", query="SELECT t FROM TipoSubappalto t")
public class TipoSubappalto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_SUB")
	private long idTipoSub;


	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_SUBAPPALTO")
	private String tipoSubappalto;


	public TipoSubappalto() {
	}

	public long getIdTipoSub() {
		return this.idTipoSub;
	}

	public void setIdTipoSub(long idTipoSub) {
		this.idTipoSub = idTipoSub;
	}


	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getTipoSubappalto() {
		return this.tipoSubappalto;
	}

	public void setTipoSubappalto(String tipoSubappalto) {
		this.tipoSubappalto = tipoSubappalto;
	}


}