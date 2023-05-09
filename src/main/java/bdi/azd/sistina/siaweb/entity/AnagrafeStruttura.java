package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ANAGRAFE_STRUTTURA database table.
 * 
 */
@Entity
@Table(name="ANAGRAFE_STRUTTURA", schema = "SISTINA")
@NamedQuery(name="AnagrafeStruttura.findAll", query="SELECT a FROM AnagrafeStruttura a")
public class AnagrafeStruttura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ANAGRAFE_STRUTTURA_IDSTRUTTURA_GENERATOR", sequenceName="SISTINA.SEQ_STRUTTURA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ANAGRAFE_STRUTTURA_IDSTRUTTURA_GENERATOR")
	@Column(name="ID_STRUTTURA")
	private long idStruttura;

	@Column(name="COD_SIPARIUM")
	private BigDecimal codSiparium;

	@Column(name="CODICE_STRUTTURA")
	private BigDecimal codiceStruttura;

	@Column(name="CODICE_TIPOLOGIA")
	private String codiceTipologia;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	private String denominazione;

	private BigDecimal dipartimento;

	private String email;

	@Column(name="FLAG_STATO")
	private String flagStato;

	@Column(name="FLAG_VALIDANTE")
	private BigDecimal flagValidante;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to ContrattoStruttura
	@OneToMany(mappedBy="anagrafeStruttura")
	private List<ContrattoStruttura> contrattoStrutturas;

	public AnagrafeStruttura() {
	}

	public long getIdStruttura() {
		return this.idStruttura;
	}

	public void setIdStruttura(long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public BigDecimal getCodSiparium() {
		return this.codSiparium;
	}

	public void setCodSiparium(BigDecimal codSiparium) {
		this.codSiparium = codSiparium;
	}

	public BigDecimal getCodiceStruttura() {
		return this.codiceStruttura;
	}

	public void setCodiceStruttura(BigDecimal codiceStruttura) {
		this.codiceStruttura = codiceStruttura;
	}

	public String getCodiceTipologia() {
		return this.codiceTipologia;
	}

	public void setCodiceTipologia(String codiceTipologia) {
		this.codiceTipologia = codiceTipologia;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public BigDecimal getDipartimento() {
		return this.dipartimento;
	}

	public void setDipartimento(BigDecimal dipartimento) {
		this.dipartimento = dipartimento;
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

	public BigDecimal getFlagValidante() {
		return this.flagValidante;
	}

	public void setFlagValidante(BigDecimal flagValidante) {
		this.flagValidante = flagValidante;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public List<ContrattoStruttura> getContrattoStrutturas() {
		return this.contrattoStrutturas;
	}

	public void setContrattoStrutturas(List<ContrattoStruttura> contrattoStrutturas) {
		this.contrattoStrutturas = contrattoStrutturas;
	}

	public ContrattoStruttura addContrattoStruttura(ContrattoStruttura contrattoStruttura) {
		getContrattoStrutturas().add(contrattoStruttura);
		contrattoStruttura.setAnagrafeStruttura(this);

		return contrattoStruttura;
	}

	public ContrattoStruttura removeContrattoStruttura(ContrattoStruttura contrattoStruttura) {
		getContrattoStrutturas().remove(contrattoStruttura);
		contrattoStruttura.setAnagrafeStruttura(null);

		return contrattoStruttura;
	}

}