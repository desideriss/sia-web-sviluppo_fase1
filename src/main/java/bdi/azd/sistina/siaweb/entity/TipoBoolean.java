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
 * The persistent class for the TIPO_BOOLEAN database table.
 * 
 */
@Entity
@Table(name="TIPO_BOOLEAN", schema = "SISTINA")
@NamedQuery(name="TipoBoolean.findAll", query="SELECT t FROM TipoBoolean t")
public class TipoBoolean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_BOOLEAN")
	private long idTpBoolean;

	@Column(name="TIPO_BOOLEAN")
	private String tipoBoolean;

	public TipoBoolean() {
	}

	public long getIdTpBoolean() {
		return this.idTpBoolean;
	}

	public void setIdTpBoolean(long idTpBoolean) {
		this.idTpBoolean = idTpBoolean;
	}

	public String getTipoBoolean() {
		return this.tipoBoolean;
	}

	public void setTipoBoolean(String tipoBoolean) {
		this.tipoBoolean = tipoBoolean;
	}

}