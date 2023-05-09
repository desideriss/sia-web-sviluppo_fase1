package bdi.azd.sistina.siaweb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ATTORE database table.
 * 
 */
@Entity
@Table(name="ATTORE", schema = "SISTINA")
public class Attore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ATTORE_IDATTORE_GENERATOR", sequenceName="SISTINA.SEQ_ATTORE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTORE_IDATTORE_GENERATOR")
	@Column(name="ID_ATTORE")
	private long idAttore;

	private String cognome;

	private String email;

	private String nome;

	private String nominativo;

	private String userid;

	//bi-directional many-to-one association to AttoreContratto
	@OneToMany(mappedBy="attore")
	private List<AttoreContratto> attoreContrattos;

	public Attore() {
	}

	public long getIdAttore() {
		return this.idAttore;
	}

	public void setIdAttore(long idAttore) {
		this.idAttore = idAttore;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNominativo() {
		return this.nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<AttoreContratto> getAttoreContrattos() {
		return this.attoreContrattos;
	}

	public void setAttoreContrattos(List<AttoreContratto> attoreContrattos) {
		this.attoreContrattos = attoreContrattos;
	}

	public AttoreContratto addAttoreContratto(AttoreContratto attoreContratto) {
		getAttoreContrattos().add(attoreContratto);
		attoreContratto.setAttore(this);

		return attoreContratto;
	}

	public AttoreContratto removeAttoreContratto(AttoreContratto attoreContratto) {
		getAttoreContrattos().remove(attoreContratto);
		attoreContratto.setAttore(null);

		return attoreContratto;
	}

}