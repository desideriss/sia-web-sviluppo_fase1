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
 * The persistent class for the STATO_PROCESSO database table.
 * 
 */
@Entity
@Table(name="STATO_PROCESSO", schema = "SISTINA")
@NamedQuery(name="StatoProcesso.findAll", query="SELECT s FROM StatoProcesso s")
public class StatoProcesso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ST_PROCESSO")
	private long idStProcesso;

	@Column(name="STATO_PROCESSO")
	private String statoProcesso;

	public StatoProcesso() {
	}

	public long getIdStProcesso() {
		return this.idStProcesso;
	}

	public void setIdStProcesso(long idStProcesso) {
		this.idStProcesso = idStProcesso;
	}

	public String getStatoProcesso() {
		return this.statoProcesso;
	}

	public void setStatoProcesso(String statoProcesso) {
		this.statoProcesso = statoProcesso;
	}

}