package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class DatiEssenzialiDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idEssenzialiI;

	private String condizioneRicerca;

	private Timestamp dataAggior;

	private String dato;

	private String flagEssenziale;

	private String tabellaDato;

	private String areaDato;

	private String datoConfronto;

	private String useridAggior;
}
