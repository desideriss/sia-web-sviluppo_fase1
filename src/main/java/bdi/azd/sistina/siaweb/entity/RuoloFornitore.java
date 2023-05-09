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
 * The persistent class for the RUOLO_FORNITORE database table.
 * 
 */
@Entity
@Table(name="RUOLO_FORNITORE", schema = "SISTINA")
@NamedQuery(name="RuoloFornitore.findAll", query="SELECT r FROM RuoloFornitore r")
public class RuoloFornitore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RL_FORNITORE")
	private long idRlFornitore;

	@Column(name="RUOLO_FORNITORE")
	private String ruoloFornitore;


	public RuoloFornitore() {
	}

	public long getIdRlFornitore() {
		return this.idRlFornitore;
	}

	public void setIdRlFornitore(long idRlFornitore) {
		this.idRlFornitore = idRlFornitore;
	}

	public String getRuoloFornitore() {
		return this.ruoloFornitore;
	}

	public void setRuoloFornitore(String ruoloFornitore) {
		this.ruoloFornitore = ruoloFornitore;
	}


}