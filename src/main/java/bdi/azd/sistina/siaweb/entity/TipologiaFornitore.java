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
 * The persistent class for the TIPOLOGIA_FORNITORE database table.
 * 
 */
@Entity
@Table(name="TIPOLOGIA_FORNITORE", schema = "SISTINA")
@NamedQuery(name="TipologiaFornitore.findAll", query="SELECT t FROM TipologiaFornitore t")
public class TipologiaFornitore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_FORNITORE")
	private long idTpFornitore;

	@Column(name="TIPO_FORNITORE")
	private String tipoFornitore;

	

	public TipologiaFornitore() {
	}

	public long getIdTpFornitore() {
		return this.idTpFornitore;
	}

	public void setIdTpFornitore(long idTpFornitore) {
		this.idTpFornitore = idTpFornitore;
	}

	public String getTipoFornitore() {
		return this.tipoFornitore;
	}

	public void setTipoFornitore(String tipoFornitore) {
		this.tipoFornitore = tipoFornitore;
	}

	

}