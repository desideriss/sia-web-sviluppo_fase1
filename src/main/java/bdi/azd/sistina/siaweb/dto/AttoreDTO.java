package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttoreDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private RuoloAttoreDettaglioDTO ruoloAttore;
	
	private Long idAttoreContratto;
	
	private Long idAttore;
	
	private String nome;
	
	private String cognome;
	
	private String userid;
	
	private String nominativo;
	
	private Date dataInizioEvento;

	private String servizioDiAppartenenza;
	
	private String email;

}
