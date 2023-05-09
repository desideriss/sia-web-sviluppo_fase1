package bdi.azd.sistina.siaweb.dto;

import java.io.Serializable;
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
public class RuoloDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	private List<String> strutture;
	
}
