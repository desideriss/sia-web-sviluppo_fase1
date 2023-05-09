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
 * The persistent class for the TIPO_CIG database table.
 * 
 */
@Entity
@Table(name="TIPO_CIG", schema = "SISTINA")
@NamedQuery(name="TipoCig.findAll", query="SELECT t FROM TipoCig t")
public class TipoCig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_CIG")
	private long idTipoCig;


	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_CIG")
	private String tipoCig;


	public TipoCig() {
	}

	public long getIdTipoCig() {
		return this.idTipoCig;
	}

	public void setIdTipoCig(long idTipoCig) {
		this.idTipoCig = idTipoCig;
	}


	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getTipoCig() {
		return this.tipoCig;
	}

	public void setTipoCig(String tipoCig) {
		this.tipoCig = tipoCig;
	}


}