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
 * The persistent class for the RUOLO_ATTORE database table.
 * 
 */
@Entity
@Table(name="RUOLO_ATTORE", schema = "SISTINA")
@NamedQuery(name="RuoloAttore.findAll", query="SELECT r FROM RuoloAttore r")
public class RuoloAttore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RUOLO_ATTORE")
	private long idRuoloAttore;

	@Column(name="RUOLO_ATTORE")
	private String ruoloAttore;

	

	public RuoloAttore() {
	}

	public long getIdRuoloAttore() {
		return this.idRuoloAttore;
	}

	public void setIdRuoloAttore(long idRuoloAttore) {
		this.idRuoloAttore = idRuoloAttore;
	}

	public String getRuoloAttore() {
		return this.ruoloAttore;
	}

	public void setRuoloAttore(String ruoloAttore) {
		this.ruoloAttore = ruoloAttore;
	}

	

}