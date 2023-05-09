package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the INTEGRAZIONE database table.
 * 
 */
@Entity
@Table(name = "INTEGRAZIONE", schema = "SISTINA")
public class Integrazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTEGRAZIONE_IDINTEGRAZIONE_GENERATOR", sequenceName="SISTINA.SEQ_INTEGRAZIONE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTEGRAZIONE_IDINTEGRAZIONE_GENERATOR")
	@Column(name="ID_INTEGRAZIONE")
	private long idIntegrazione;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	private String descrizione;

	private String motivazione;
	
	@Column(name="ID_CIG_GENERATO")
	private String cigGenerato;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to Cronologia
	@OneToMany(mappedBy="integrazione", orphanRemoval= true,cascade = {CascadeType.REMOVE})
	private List<Cronologia> cronologias;

	//bi-directional many-to-one association to FornitoreContratto
	@OneToMany(mappedBy="integrazione",  cascade = {CascadeType.REMOVE})
	private List<FornitoreContratto> fornitoreContrattos;

	//bi-directional many-to-one association to Importo
	@OneToMany(mappedBy="integrazione",  cascade = {CascadeType.REMOVE})
	private List<Importo> importos;

	

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CIG_ORIGINE")
	private Contratto contrattoCigOrigine;

	//bi-directional many-to-one association to Procedura
	@ManyToOne
	@JoinColumn(name="ID_PROC_PROROGA")
	private Procedura procedura;

	//bi-directional many-to-one association to TipoIntegrazione
	@ManyToOne
	@JoinColumn(name="ID_TP_INTEGRAZ")
	private TipoIntegrazione tipoIntegrazione;

	public Integrazione() {
	}

	public long getIdIntegrazione() {
		return this.idIntegrazione;
	}

	public void setIdIntegrazione(long idIntegrazione) {
		this.idIntegrazione = idIntegrazione;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMotivazione() {
		return this.motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public List<Cronologia> getCronologias() {
		return this.cronologias;
	}

	public void setCronologias(List<Cronologia> cronologias) {
		this.cronologias = cronologias;
	}

	public Cronologia addCronologia(Cronologia cronologia) {
		getCronologias().add(cronologia);
		cronologia.setIntegrazione(this);

		return cronologia;
	}

	public Cronologia removeCronologia(Cronologia cronologia) {
		getCronologias().remove(cronologia);
		cronologia.setIntegrazione(null);

		return cronologia;
	}

	public List<FornitoreContratto> getFornitoreContrattos() {
		return this.fornitoreContrattos;
	}

	public void setFornitoreContrattos(List<FornitoreContratto> fornitoreContrattos) {
		this.fornitoreContrattos = fornitoreContrattos;
	}

	public FornitoreContratto addFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().add(fornitoreContratto);
		fornitoreContratto.setIntegrazione(this);

		return fornitoreContratto;
	}

	public FornitoreContratto removeFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().remove(fornitoreContratto);
		fornitoreContratto.setIntegrazione(null);

		return fornitoreContratto;
	}

	public List<Importo> getImportos() {
		return this.importos;
	}

	public void setImportos(List<Importo> importos) {
		this.importos = importos;
	}

	public Importo addImporto(Importo importo) {
		getImportos().add(importo);
		importo.setIntegrazione(this);

		return importo;
	}

	public Importo removeImporto(Importo importo) {
		getImportos().remove(importo);
		importo.setIntegrazione(null);

		return importo;
	}


	public Contratto getContrattoCigOrigine() {
		return this.contrattoCigOrigine;
	}

	public void setContrattoCigOrigine(Contratto contrattoCigOrigine) {
		this.contrattoCigOrigine = contrattoCigOrigine;
	}

	public Procedura getProcedura() {
		return this.procedura;
	}

	public void setProcedura(Procedura procedura) {
		this.procedura = procedura;
	}

	public TipoIntegrazione getTipoIntegrazione() {
		return this.tipoIntegrazione;
	}

	public void setTipoIntegrazione(TipoIntegrazione tipoIntegrazione) {
		this.tipoIntegrazione = tipoIntegrazione;
	}

	public String getCigGenerato() {
		return cigGenerato;
	}

	public void setCigGenerato(String cigGenerato) {
		this.cigGenerato = cigGenerato;
	}

}