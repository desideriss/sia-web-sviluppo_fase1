package bdi.azd.sistina.siaweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "JWTTOKEN", schema = "SISTINA")
public class JWTToken {

	@Id
	@SequenceGenerator(name="JWTTOKEN_ID_TOKEN_GENERATOR", sequenceName="SISTINA.SEQ_JWTTOKEN", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JWTTOKEN_ID_TOKEN_GENERATOR")
	@Column(name="ID_TOKEN")
	private Long idToken;

	@Column(name="JWT_TOKEN")
	private String jwtToken;

	@Column(name="VALIDO")
	private Boolean valido;
	
	public Long getIdToken() {
		return this.idToken;
	}
	
	public String getJwtToken() {
		return this.jwtToken;
	}
	
	public Boolean isValido() {
		return this.valido;
	}
	
	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}
	
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public void setValido(Boolean valido) {
		this.valido = valido;
	}
	
}
