package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bdi.azd.sistina.siaweb.util.UserUtil;


/**
 * The persistent class for the PROCEDURA database table.
 * 
 */
@Entity
@Table(name = "PROCEDURA", schema = "SISTINA")
public class Procedura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROCEDURA_IDPROCEDURA_GENERATOR", sequenceName="SISTINA.SEQ_PROCEDURA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROCEDURA_IDPROCEDURA_GENERATOR")
	@Column(name="ID_PROCEDURA")
	private Long idProcedura;

	@Column(name="COD_PROCEDURA")
	private String codProcedura;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_TRASM_CONTRATTO")
	private Date dataTrasmContratto;

	private String descrizione;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to Integrazione
	@OneToMany(mappedBy="procedura")
	private List<Integrazione> integraziones;

	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }
	
	public Procedura() {
	}

	public Long getIdProcedura() {
		return this.idProcedura;
	}

	public void setIdProcedura(Long idProcedura) {
		this.idProcedura = idProcedura;
	}

	public String getCodProcedura() {
		return this.codProcedura;
	}

	public void setCodProcedura(String codProcedura) {
		this.codProcedura = codProcedura;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public Date getDataTrasmContratto() {
		return this.dataTrasmContratto;
	}

	public void setDataTrasmContratto(Date dataTrasmContratto) {
		this.dataTrasmContratto = dataTrasmContratto;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public List<Integrazione> getIntegraziones() {
		return this.integraziones;
	}

	public void setIntegraziones(List<Integrazione> integraziones) {
		this.integraziones = integraziones;
	}

	public Integrazione addIntegrazione(Integrazione integrazione) {
		getIntegraziones().add(integrazione);
		integrazione.setProcedura(this);

		return integrazione;
	}

	public Integrazione removeIntegrazione(Integrazione integrazione) {
		getIntegraziones().remove(integrazione);
		integrazione.setProcedura(null);

		return integrazione;
	}

}