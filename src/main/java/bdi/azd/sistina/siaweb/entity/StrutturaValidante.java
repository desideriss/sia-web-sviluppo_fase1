package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the STRUTTURA_VALIDANTE database table.
 * 
 */
@Entity
@Table(name="STRUTTURA_VALIDANTE", schema = "SISTINA")
@NamedQuery(name="StrutturaValidante.findAll", query="SELECT s FROM StrutturaValidante s")
public class StrutturaValidante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_VALIDANTE")
	private long idValidante;

	@Column(name="COD_STRUTTURA_UO")
	private BigDecimal codStrutturaUo;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="DESCRIZIONE_UO")
	private String descrizioneUo;

	private String email;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public StrutturaValidante() {
	}

	public long getIdValidante() {
		return this.idValidante;
	}

	public void setIdValidante(long idValidante) {
		this.idValidante = idValidante;
	}

	public BigDecimal getCodStrutturaUo() {
		return this.codStrutturaUo;
	}

	public void setCodStrutturaUo(BigDecimal codStrutturaUo) {
		this.codStrutturaUo = codStrutturaUo;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDescrizioneUo() {
		return this.descrizioneUo;
	}

	public void setDescrizioneUo(String descrizioneUo) {
		this.descrizioneUo = descrizioneUo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlagStato() {
		return this.flagStato;
	}

	public void setFlagStato(String flagStato) {
		this.flagStato = flagStato;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}