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
 * The persistent class for the RUOLO_STRUTTURA database table.
 * 
 */
@Entity
@Table(name="RUOLO_STRUTTURA", schema = "SISTINA")
@NamedQuery(name="RuoloStruttura.findAll", query="SELECT r FROM RuoloStruttura r")
public class RuoloStruttura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RUOLO_STR")
	private long idRuoloStr;

	@Column(name="RUOLO_STRUTTURA")
	private String ruoloStruttura;


	public RuoloStruttura() {
	}

	public long getIdRuoloStr() {
		return this.idRuoloStr;
	}

	public void setIdRuoloStr(long idRuoloStr) {
		this.idRuoloStr = idRuoloStr;
	}

	public String getRuoloStruttura() {
		return this.ruoloStruttura;
	}

	public void setRuoloStruttura(String ruoloStruttura) {
		this.ruoloStruttura = ruoloStruttura;
	}


}