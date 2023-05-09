package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the STRUTTURA_CUSTOM database table.
 * 
 */
@Entity
@Table(name="STRUTTURA_CUSTOM", schema = "SISTINA")
@NamedQuery(name="StrutturaCustom.findAll", query="SELECT s FROM StrutturaCustom s")
public class StrutturaCustom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ID_RUOLO_STR")
	private BigDecimal idRuoloStr;

	@Column(name="ID_STRUTTURA")
	private BigDecimal idStruttura;

	@Column(name="STRUTTURA")
	private String struttura;

	@Id
	@GeneratedValue
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private byte[] uuid;

	public StrutturaCustom() {
	}

	public BigDecimal getIdRuoloStr() {
		return idRuoloStr;
	}

	public void setIdRuoloStr(BigDecimal idRuoloStr) {
		this.idRuoloStr = idRuoloStr;
	}

	public BigDecimal getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(BigDecimal idStruttura) {
		this.idStruttura = idStruttura;
	}

	public String getStruttura() {
		return struttura;
	}

	public void setStruttura(String struttura) {
		this.struttura = struttura;
	}

	public byte[] getUuid() {
		return uuid;
	}

	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	

}