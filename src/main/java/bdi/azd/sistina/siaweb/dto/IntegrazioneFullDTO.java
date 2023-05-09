package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
public class IntegrazioneFullDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idIntegrazione;

	private Timestamp dataAggior;

	private String descrizione;

	private String motivazione;

	private String useridAggior;

	private List<CronologiaIntegrazioneDTO> cronologias;

	private List<FornitoreContrattoIntegrazioneDTO> fornitoreContrattos;

	private List<ImportoIntegrazioneDTO> importos;

	private String cigGenerato;

//	private ContrattoDTO contrattoCigOrigine;

	private ProceduraDTO procedura;

	private TipoIntegrazioneDTO tipoIntegrazione;
	

}
