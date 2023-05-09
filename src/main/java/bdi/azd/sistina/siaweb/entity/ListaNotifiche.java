package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the LISTA_NOTIFICHE database table.
 * 
 */
@Entity
@Table(name="LISTA_NOTIFICHE", schema = "SISTINA")
@NamedQuery(name="ListaNotifiche.findAll", query="SELECT l FROM ListaNotifiche l")
public class ListaNotifiche implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LISTA_NOTIFICHE_IDNOTIFICHE_GENERATOR", sequenceName="SISTINA.SEQ_NOTIFICHE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LISTA_NOTIFICHE_IDNOTIFICHE_GENERATOR")
	@Column(name="ID_NOTIFICHE")
	private long idNotifiche;

	private String ambito;

	private String corpo;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	private String destinatario;

	private String oggetto;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public ListaNotifiche() {
	}

	public long getIdNotifiche() {
		return this.idNotifiche;
	}

	public void setIdNotifiche(long idNotifiche) {
		this.idNotifiche = idNotifiche;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getCorpo() {
		return this.corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

}