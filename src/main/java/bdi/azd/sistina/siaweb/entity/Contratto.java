package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bdi.azd.sistina.siaweb.util.UserUtil;


/**
 * The persistent class for the CONTRATTO database table.
 * 
 */
@Entity
@Table(name = "CONTRATTO", schema = "SISTINA")
public class Contratto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRATTO_IDCONTRATTO_GENERATOR", sequenceName="SISTINA.SEQ_CONTRATTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATTO_IDCONTRATTO_GENERATOR")
	@Column(name="ID_CONTRATTO")
	private long idContratto;

	@Column(name="CAT_MERCEOLOGICA")
	private String catMerceologica;

	private String cig;
	
	@Column(name="MOTIVO_COLL")
	private String motivazioneCollegamento;

	@Column(name="CIG_PADRE")
	private String cigPadre;

	private String comparto;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	@Column(name="DESCRIZIONE_CRTT")
	private String descrizioneCrtt;

	@Column(name="NOTE_STATO")
	private String noteStato;

	private String ricorrente;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to AttoreContratto
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<AttoreContratto> attoreContrattos;

	//uni-directional many-to-one association to Procedura
	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="ID_PROC_ORG")
	private Procedura proceduraOrg;

	//uni-directional many-to-one association to Procedura
	@ManyToOne
	@JoinColumn(name="ID_PROC_RIN")
	private Procedura proceduraRin;

	//uni-directional many-to-one association to StatoContratto
	@ManyToOne
	@JoinColumn(name="ID_ST_CONTRATTO")
	private StatoContratto statoContratto;

	//bi-directional many-to-one association to StatoProcesso
	@ManyToOne
	@JoinColumn(name="ID_STATO_PROCESSO")
	private StatoProcesso statoProcesso;

	//bi-directional many-to-one association to TipoCig
	@ManyToOne
	@JoinColumn(name="ID_TIPO_CIG")
	private TipoCig tipoCig;

	//bi-directional many-to-one association to TipoContratto
	@ManyToOne
	@JoinColumn(name="ID_TP_CONTRATTO")
	private TipoContratto tipoContratto;

	//bi-directional many-to-one association to TipoSubappalto
	@ManyToOne
	@JoinColumn(name="ID_SUBAPPALTO")
	private TipoSubappalto tipoSubappalto;

	//bi-directional many-to-one association to ContrattoStruttura
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<ContrattoStruttura> contrattoStrutturas;

	//bi-directional many-to-one association to Cronologia
	@OneToMany(mappedBy="contratto", orphanRemoval = true,  cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Cronologia> cronologias;

	//bi-directional many-to-one association to FornitoreContratto
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<FornitoreContratto> fornitoreContrattos;

	//bi-directional many-to-one association to Importo
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Importo> importos;

	//bi-directional many-to-one association to Integrazione
	@OneToMany(mappedBy="contrattoCigOrigine", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Integrazione> integraziones;

	//bi-directional many-to-one association to Riferimento
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Riferimento> riferimentos;
	
	@OneToMany(mappedBy="contratto", orphanRemoval = true, cascade = {CascadeType.REMOVE})
	private List<StoricoEssenzVal> storicoEssenzVals;

	

	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }

	public Contratto() {
	}

	public long getIdContratto() {
		return this.idContratto;
	}

	public void setIdContratto(long idContratto) {
		this.idContratto = idContratto;
	}

	public String getCatMerceologica() {
		return this.catMerceologica;
	}

	public void setCatMerceologica(String catMerceologica) {
		this.catMerceologica = catMerceologica;
	}

	public String getCig() {
		return this.cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCigPadre() {
		return this.cigPadre;
	}

	public void setCigPadre(String cigPadre) {
		this.cigPadre = cigPadre;
	}

	public String getComparto() {
		return this.comparto;
	}

	public void setComparto(String comparto) {
		this.comparto = comparto;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDescrizioneCrtt() {
		return this.descrizioneCrtt;
	}

	public void setDescrizioneCrtt(String descrizioneCrtt) {
		this.descrizioneCrtt = descrizioneCrtt;
	}

	public String getNoteStato() {
		return this.noteStato;
	}

	public void setNoteStato(String noteStato) {
		this.noteStato = noteStato;
	}

	public String getRicorrente() {
		return this.ricorrente;
	}

	public void setRicorrente(String ricorrente) {
		this.ricorrente = ricorrente;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public List<AttoreContratto> getAttoreContrattos() {
		return this.attoreContrattos;
	}

	public void setAttoreContrattos(List<AttoreContratto> attoreContrattos) {
		this.attoreContrattos = attoreContrattos;
		if(attoreContrattos != null) {
			for(AttoreContratto attoreContratto : attoreContrattos) {
				attoreContratto.setContratto(this);
			}
		}
	}

	public AttoreContratto addAttoreContratto(AttoreContratto attoreContratto) {
		getAttoreContrattos().add(attoreContratto);
		attoreContratto.setContratto(this);

		return attoreContratto;
	}

	public AttoreContratto removeAttoreContratto(AttoreContratto attoreContratto) {
		getAttoreContrattos().remove(attoreContratto);
		attoreContratto.setContratto(null);

		return attoreContratto;
	}

	public Procedura getProceduraOrg() {
		return this.proceduraOrg;
	}

	public void setProceduraOrg(Procedura proceduraOrg) {
		this.proceduraOrg = proceduraOrg;
	}

	public Procedura getProceduraRin() {
		return this.proceduraRin;
	}

	public void setProceduraRin(Procedura proceduraRin) {
		this.proceduraRin = proceduraRin;
	}

	public StatoContratto getStatoContratto() {
		return this.statoContratto;
	}

	public void setStatoContratto(StatoContratto statoContratto) {
		this.statoContratto = statoContratto;
	}

	public StatoProcesso getStatoProcesso() {
		return this.statoProcesso;
	}

	public void setStatoProcesso(StatoProcesso statoProcesso) {
		this.statoProcesso = statoProcesso;
	}

	public TipoCig getTipoCig() {
		return this.tipoCig;
	}

	public void setTipoCig(TipoCig tipoCig) {
		this.tipoCig = tipoCig;
	}

	public TipoContratto getTipoContratto() {
		return this.tipoContratto;
	}

	public void setTipoContratto(TipoContratto tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public TipoSubappalto getTipoSubappalto() {
		return this.tipoSubappalto;
	}

	public void setTipoSubappalto(TipoSubappalto tipoSubappalto) {
		this.tipoSubappalto = tipoSubappalto;
	}

	public List<ContrattoStruttura> getContrattoStrutturas() {
		return this.contrattoStrutturas;
	}

	public void setContrattoStrutturas(List<ContrattoStruttura> contrattoStrutturas) {
		this.contrattoStrutturas = contrattoStrutturas;
		if(contrattoStrutturas != null) {
			for(ContrattoStruttura contrattoStruttura : contrattoStrutturas) {
				contrattoStruttura.setContratto(this);
			}
		}
	}

	public ContrattoStruttura addContrattoStruttura(ContrattoStruttura contrattoStruttura) {
		getContrattoStrutturas().add(contrattoStruttura);
		contrattoStruttura.setContratto(this);

		return contrattoStruttura;
	}

	public ContrattoStruttura removeContrattoStruttura(ContrattoStruttura contrattoStruttura) {
		getContrattoStrutturas().remove(contrattoStruttura);
		contrattoStruttura.setContratto(null);

		return contrattoStruttura;
	}

	public List<Cronologia> getCronologias() {
		return this.cronologias;
	}

	public void setCronologias(List<Cronologia> cronologias) {
		this.cronologias = cronologias;
		if(cronologias != null) {
			for(Cronologia cronologia : cronologias) {
				cronologia.setContratto(this);
			}
		}
	}

	public Cronologia addCronologia(Cronologia cronologia) {
		getCronologias().add(cronologia);
		cronologia.setContratto(this);

		return cronologia;
	}

	public Cronologia removeCronologia(Cronologia cronologia) {
		getCronologias().remove(cronologia);
		cronologia.setContratto(null);

		return cronologia;
	}

	public List<FornitoreContratto> getFornitoreContrattos() {
		return this.fornitoreContrattos;
	}

	public void setFornitoreContrattos(List<FornitoreContratto> fornitoreContrattos) {
		this.fornitoreContrattos = fornitoreContrattos;
		if(fornitoreContrattos != null) {
			for(FornitoreContratto fornitoreContratto : fornitoreContrattos) {
				fornitoreContratto.setContratto(this);
			}
		}
	}

	public FornitoreContratto addFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().add(fornitoreContratto);
		fornitoreContratto.setContratto(this);

		return fornitoreContratto;
	}

	public FornitoreContratto removeFornitoreContratto(FornitoreContratto fornitoreContratto) {
		getFornitoreContrattos().remove(fornitoreContratto);
		fornitoreContratto.setContratto(null);

		return fornitoreContratto;
	}

	public List<Importo> getImportos() {
		return this.importos;
	}

	public void setImportos(List<Importo> importos) {
		this.importos = importos;
		if(importos != null) {
			for(Importo importo : importos) {
				importo.setContratto(this);
			}
		}
	}

	public Importo addImporto(Importo importo) {
		getImportos().add(importo);
		importo.setContratto(this);

		return importo;
	}

	public Importo removeImporto(Importo importo) {
		getImportos().remove(importo);
		importo.setContratto(null);

		return importo;
	}



	public List<Riferimento> getRiferimentos() {
		return this.riferimentos;
	}

	public void setRiferimentos(List<Riferimento> riferimentos) {
		this.riferimentos = riferimentos;
		if(riferimentos != null) {
			for(Riferimento riferimento : riferimentos) {
				riferimento.setContratto(this);
			}
		}
	}

	public Riferimento addRiferimento(Riferimento riferimento) {
		getRiferimentos().add(riferimento);
		riferimento.setContratto(this);

		return riferimento;
	}

	public Riferimento removeRiferimento(Riferimento riferimento) {
		getRiferimentos().remove(riferimento);
		riferimento.setContratto(null);

		return riferimento;
	}

	public String getMotivazioneCollegamento() {
		return motivazioneCollegamento;
	}

	public void setMotivazioneCollegamento(String motivazioneCollegamento) {
		this.motivazioneCollegamento = motivazioneCollegamento;
	}
	
	
	public List<StoricoEssenzVal> getStoricoEssenzVals() {
		return storicoEssenzVals;
	}

	public void setStoricoEssenzVals(List<StoricoEssenzVal> storicoEssenzVals) {
		this.storicoEssenzVals = storicoEssenzVals;
	}

	public List<Integrazione> getIntegraziones() {
		return integraziones;
	}

	public void setIntegraziones(List<Integrazione> integraziones) {
		this.integraziones = integraziones;
	}

}