package bdi.azd.sistina.siaweb.dto;
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
public class UserDTO {
	private String id;
	private String nome;
	private String cognome;
	private List<RuoloDTO> ruoli;
	private String authToken;
}
