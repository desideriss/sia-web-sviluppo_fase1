package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class ProceduraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idProcedura;

	private String codProcedura;

	private Timestamp dataAggior;

	private Date dataTrasmContratto;

	private String descrizione;

	private String useridAggior;

//	private List<IntegrazioneDettaglioDTO> integraziones;
}
