package bdi.azd.sistina.siaweb.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the STORICO_ESSENZ_VAL database table.
 * 
 */
@Entity
@Table(name="STORICO_ESSENZ_VAL", schema = "SISTINA")
@NamedQuery(name="StoricoEssenzVal.findAll", query="SELECT d FROM StoricoEssenzVal d")
public class StoricoEssenzVal {
	
	@Id
	@SequenceGenerator(name="SEQ_CONTRATTO_ESSENZ", sequenceName="SISTINA.SEQ_CONTRATTO_ESSENZ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CONTRATTO_ESSENZ")
	@Column(name="ID_CONTRATTO_ESSENZ")
	private long idContrattoEssenz;

	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;
	
	@ManyToOne
	@JoinColumn(name="ID_ESSENZIALI_I")
	private DatiEssenzialiI datiEssenzialiI;
	
	@Column(name="VALORE_OLD")
	private String valoreOld;
	
	@Column(name="VALORE_NEW")
	private String valoreNew;
	
	@Column(name="STATO_ACCETTAZ")
	private String statoAccettaz;
	
	@Column(name="MOTIVAZIONE")
	private String motivazione;
	
	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;
	
	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	public long getIdContrattoEssenz() {
		return idContrattoEssenz;
	}

	public void setIdContrattoEssenz(long idContrattoEssenz) {
		this.idContrattoEssenz = idContrattoEssenz;
	}

	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public DatiEssenzialiI getDatiEssenzialiI() {
		return datiEssenzialiI;
	}

	public void setDatiEssenzialiI(DatiEssenzialiI datiEssenzialiI) {
		this.datiEssenzialiI = datiEssenzialiI;
	}

	public String getValoreOld() {
		return valoreOld;
	}

	public void setValoreOld(String valoreOld) {
		this.valoreOld = valoreOld;
	}

	public String getValoreNew() {
		return valoreNew;
	}

	public void setValoreNew(String valoreNew) {
		this.valoreNew = valoreNew;
	}

	public String getStatoAccettaz() {
		return statoAccettaz;
	}

	public void setStatoAccettaz(String statoAccettaz) {
		this.statoAccettaz = statoAccettaz;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public Timestamp getDataAggior() {
		return dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getUseridAggior() {
		return useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}
	
}
