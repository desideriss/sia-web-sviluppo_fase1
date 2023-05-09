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
 * The persistent class for the DATI_ESSENZIALI_I database table.
 * 
 */
@Entity
@Table(name="DATI_ESSENZIALI_I", schema = "SISTINA")
@NamedQuery(name="DatiEssenzialiI.findAll", query="SELECT d FROM DatiEssenzialiI d")
public class DatiEssenzialiI implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATI_ESSENZIALI_I_IDESSENZIALII_GENERATOR", sequenceName="SISTINA.SEQ_ESSENZIALI_I", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DATI_ESSENZIALI_I_IDESSENZIALII_GENERATOR")
	@Column(name="ID_ESSENZIALI_I")
	private long idEssenzialiI;

	@Column(name="CONDIZIONE_RICERCA")
	private String condizioneRicerca;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="DATO")
	private String dato;

	@Column(name="FLAG_ESSENZIALE")
	private String flagEssenziale;

	@Column(name="TABELLA_DATO")
	private String tabellaDato;
	
	@Column(name="AREA_DATO")
	private String areaDato;
	
	@Column(name="DATO_CONFRONTO")
	private String datoConfronto;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;


	public DatiEssenzialiI() {
	}

	public String getAreaDato() {
		return areaDato;
	}

	public void setAreaDato(String areaDato) {
		this.areaDato = areaDato;
	}

	public String getDatoConfronto() {
		return datoConfronto;
	}

	public void setDatoConfronto(String datoConfronto) {
		this.datoConfronto = datoConfronto;
	}

	public long getIdEssenzialiI() {
		return this.idEssenzialiI;
	}

	public void setIdEssenzialiI(long idEssenzialiI) {
		this.idEssenzialiI = idEssenzialiI;
	}

	public String getCondizioneRicerca() {
		return this.condizioneRicerca;
	}

	public void setCondizioneRicerca(String condizioneRicerca) {
		this.condizioneRicerca = condizioneRicerca;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDato() {
		return this.dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getFlagEssenziale() {
		return this.flagEssenziale;
	}

	public void setFlagEssenziale(String flagEssenziale) {
		this.flagEssenziale = flagEssenziale;
	}

	public String getTabellaDato() {
		return this.tabellaDato;
	}

	public void setTabellaDato(String tabellaDato) {
		this.tabellaDato = tabellaDato;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}


}