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
 * The persistent class for the TIPO_RIFERIMENTO database table.
 * 
 */
@Entity
@Table(name="TIPO_RIFERIMENTO", schema = "SISTINA")
@NamedQuery(name="TipoRiferimento.findAll", query="SELECT t FROM TipoRiferimento t")
public class TipoRiferimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_RIFERIMENTO")
	private long idTpRiferimento;


	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="TIPO_RIFERIMENTO")
	private String tipoRiferimento;


	public TipoRiferimento() {
	}

	public long getIdTpRiferimento() {
		return this.idTpRiferimento;
	}

	public void setIdTpRiferimento(long idTpRiferimento) {
		this.idTpRiferimento = idTpRiferimento;
	}


	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getTipoRiferimento() {
		return this.tipoRiferimento;
	}

	public void setTipoRiferimento(String tipoRiferimento) {
		this.tipoRiferimento = tipoRiferimento;
	}


}