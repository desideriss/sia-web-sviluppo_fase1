package bdi.azd.sistina.siaweb.auth.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RuoliUtil {
	
	private RuoliUtil() {}
	
	public static UserDTO getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
        	return (UserDTO)authentication.getPrincipal();
        }
        //throw new IllegalStateException();
        //TEST
        List<RuoloDTO> ruoli= new ArrayList<>();
        List<String> strutture= new ArrayList<>();
        strutture.add("999");
        ruoli.add(new RuoloDTO("SISTINA_GESTORE", "GESTORE", strutture));
//        ruoli.add(new RuoloDTO("SISTINA_UTENTE", "UTENTE", strutture));
        
        return new UserDTO("UserID", "Nome", "Cognome", ruoli, null);
	}
	
	public static boolean checkRuolo(String descrizione) {
		UserDTO userDTO = getLoggedUser();
		if (userDTO != null) {
			log.info("UserDTO: " + userDTO);
			// Check Ruoli Prende i Ruoli dall'oggetto UserDTO
			List<RuoloDTO> ruoli = userDTO.getRuoli();
			if (ruoli != null) {
				for (RuoloDTO ruoloDTO : ruoli) {
					if (ruoloDTO.getCodice().equalsIgnoreCase(descrizione)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String getStrutturaAssociata(String descrizioneRuolo) {
		UserDTO userDTO = getLoggedUser();
		if (userDTO != null) {
			log.info("UserDTO: " + userDTO);
			List<RuoloDTO> ruoli = userDTO.getRuoli();
			if (ruoli != null) {
				for (RuoloDTO ruoloDTO : ruoli) {
					log.info("ruolo trovato:" + ruoloDTO.getDescrizione());
					if (ruoloDTO.getCodice().equalsIgnoreCase(descrizioneRuolo)) {
						log.info("ruolo match");
						if (ruoloDTO.getStrutture() != null && !ruoloDTO.getStrutture().isEmpty()) {
							return ruoloDTO.getStrutture().get(0);
						}
					}
				}
			}
		}
		return null;
	}

}
