package bdi.azd.sistina.siaweb.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.AnagrafeStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.StrutturaDTO;


@Service
public interface AnagrafeStrutturaService {
	
	/**
	 * metodo per il recupero dei dati dalla tipologica Anagrafe Struttura
	 * @param flagValidante flag che indica se è una struttura validante 
	 * @return una Lista di AnagrafeStrutturaDTO
	 */
    List<AnagrafeStrutturaDTO> getAnagrafeStruttura(BigDecimal flagValidante);
    
    /**
     * metodo per il recupero del codice e denominazione delle strutture validanti
     * @param denominazione
     * @return lista di String di Codice e Denominazione concatenate
     */
    List<StrutturaDTO> getStrutturaValidanteLike(String denominazione);
    
    /**
     * metodo per il recupero del codice e denominazione delle strutture responsabili
     * @param denominazione
     * @return lista di String di Codice e Denominazione concatenate
     */
    List<StrutturaDTO> getStrutturaResponsabileLike(String denominazione);
    
    /**
     * metodo per il recupero del codice e denominazione delle strutture destinatarie
     * @param denominazione
     * @return lista di String di Codice e Denominazione concatenate
     */
    List<StrutturaDTO> getStrutturaDestinatariaLike(String denominazione);
}
