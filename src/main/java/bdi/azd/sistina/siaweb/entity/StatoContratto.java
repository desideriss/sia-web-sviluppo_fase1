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
 * The persistent class for the STATO_CONTRATTO database table.
 * 
 */
@Entity
@Table(name="STATO_CONTRATTO", schema = "SISTINA")
@NamedQuery(name="StatoContratto.findAll", query="SELECT s FROM StatoContratto s")
public class StatoContratto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ST_CONTRATTO")
	private long idStContratto;

	@Column(name="STATO_CONTRATTO")
	private String statoContratto;

	public StatoContratto() {
	}

	public long getIdStContratto() {
		return this.idStContratto;
	}

	public void setIdStContratto(long idStContratto) {
		this.idStContratto = idStContratto;
	}

	public String getStatoContratto() {
		return this.statoContratto;
	}

	public void setStatoContratto(String statoContratto) {
		this.statoContratto = statoContratto;
	}

}