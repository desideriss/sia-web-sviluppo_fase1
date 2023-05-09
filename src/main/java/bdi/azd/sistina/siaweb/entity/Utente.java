package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the UTENTE database table.
 * 
 */
@Entity
@Table(name = "UTENTE", schema = "SISTINA")
public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_UTENTE")
	private long idUtente;

	@Column(name="COD_DIVISIONE")
	private String codDivisione;

	@Column(name="COD_SERVIZIO")
	private String codServizio;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	private String funzione;

	private String ruolo;

	@Column(name="USER_WIN")
	private String userWin;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public Utente() {
	}

	public long getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public String getCodDivisione() {
		return this.codDivisione;
	}

	public void setCodDivisione(String codDivisione) {
		this.codDivisione = codDivisione;
	}

	public String getCodServizio() {
		return this.codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getFunzione() {
		return this.funzione;
	}

	public void setFunzione(String funzione) {
		this.funzione = funzione;
	}

	public String getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUserWin() {
		return this.userWin;
	}

	public void setUserWin(String userWin) {
		this.userWin = userWin;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}