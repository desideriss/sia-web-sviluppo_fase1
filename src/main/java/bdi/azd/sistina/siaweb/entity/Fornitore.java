package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import bdi.azd.sistina.siaweb.util.UserUtil;


/**
 * The persistent class for the FORNITORE database table.
 * 
 */
@Entity
@Table(name = "FORNITORE", schema = "SISTINA")
public class Fornitore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FORNITORE_IDFORNITORE_GENERATOR", sequenceName="SISTINA.SEQ_FORNITORE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORNITORE_IDFORNITORE_GENERATOR")
	@Column(name="ID_FORNITORE")
	private Long idFornitore;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	private String pmi;

	@Column(name="RAGIONE_SOCIALE")
	private String ragioneSociale;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to FornitoreContratto
	@OneToMany(mappedBy="fornitore")
	private List<FornitoreContratto> fornitoreContrattos;

	
	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }
	
	public Fornitore() {
	}

	public Long getIdFornitore() {
		return this.idFornitore;
	}

	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getPmi() {
		return this.pmi;
	}

	public void setPmi(String pmi) {
		this.pmi = pmi;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public List<FornitoreContratto> getFornitoreContrattos() {
		return this.fornitoreContrattos;
	}

	public void setFornitoreContrattos(List<FornitoreContratto> fornitoreContrattos) {
		this.fornitoreContrattos = fornitoreContrattos;
	}

	public FornitoreContratto addFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().add(fornitoreContratto);
		fornitoreContratto.setFornitore(this);

		return fornitoreContratto;
	}

	public FornitoreContratto removeFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().remove(fornitoreContratto);
		fornitoreContratto.setFornitore(null);

		return fornitoreContratto;
	}

}