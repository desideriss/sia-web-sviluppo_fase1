package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bdi.azd.sistina.siaweb.util.UserUtil;


/**
 * The persistent class for the RIFERIMENTO database table.
 * 
 */
@Entity
@Table(name = "RIFERIMENTO", schema = "SISTINA")
public class Riferimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RIFERIMENTO_IDRIFERIMENTO_GENERATOR", sequenceName="SISTINA.SEQ_RIFERIMENTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RIFERIMENTO_IDRIFERIMENTO_GENERATOR")
	@Column(name="ID_RIFERIMENTO")
	private long idRiferimento;

	@Column(name="COD_RIFERIMENTO")
	private String codRiferimento;

	@Column(name="DATA_AGGIOR")
	private Timestamp dataAggior;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_VAL")
	private Date dataVal;

	private String descrizione;

	@Column(name="LINK")
	private String link;

	@Column(name="USERID_AGGIOR")
	private String useridAggior;

	//bi-directional many-to-one association to Contratto
	@ManyToOne
	@JoinColumn(name="ID_CONTRATTO")
	private Contratto contratto;

	//bi-directional many-to-one association to TipoRiferimento
	@ManyToOne
	@JoinColumn(name="TIPO_DOCUMENTO")
	private TipoRiferimento tipoRiferimento;

	public Riferimento() {
	}
	
	@PrePersist
    public void prePersist() {
		dataAggior = new Timestamp(System.currentTimeMillis());
        useridAggior = UserUtil.getLoggedUserId();
    }

	public long getIdRiferimento() {
		return this.idRiferimento;
	}

	public void setIdRiferimento(long idRiferimento) {
		this.idRiferimento = idRiferimento;
	}

	public String getCodRiferimento() {
		return this.codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}

	public Timestamp getDataAggior() {
		return this.dataAggior;
	}

	public void setDataAggior(Timestamp dataAggior) {
		this.dataAggior = dataAggior;
	}

	public Date getDataVal() {
		return this.dataVal;
	}

	public void setDataVal(Date dataVal) {
		this.dataVal = dataVal;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUseridAggior() {
		return this.useridAggior;
	}

	public void setUseridAggior(String useridAggior) {
		this.useridAggior = useridAggior;
	}

	public Contratto getContratto() {
		return this.contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public TipoRiferimento getTipoRiferimento() {
		return this.tipoRiferimento;
	}

	public void setTipoRiferimento(TipoRiferimento tipoRiferimento) {
		this.tipoRiferimento = tipoRiferimento;
	}

}