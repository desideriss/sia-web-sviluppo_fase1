package bdi.azd.sistina.siaweb.service;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.CheckValidazioneIDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;

@Service
public interface DatiEssenzialiIService {
	
	/**
	 * Controlla che i dati essenziali siano stati inseriti in base alla tabella di riferimento:
	 * DATI_ESSENZIALI_I
	 * 
	 * @param idContratto
	 * @return
	 */
	CheckValidazioneIDTO checkDatiEssenzialiI(long idContratto);

	/**
	 * Controlla che i dati essenziali siano stati inseriti in base alla tabella di riferimento:
	 * DATI_ESSENZIALI_I
	 * 
	 * @param contratto
	 * @return
	 */
	CheckValidazioneIDTO checkDatiEssenzialiI(Contratto contratto);
	
    /**
     * Metodo per la validazione di primo livello di un contratto
     * 
     * @param idContratto
     */
	CheckValidazioneIDTO validaContrattoI(long idContratto);
    
    /**
     * Metodo per la validazione di un contratto valido solo per la Fase 1
     * 
     * @param idContratto
     */
    void validaContratto(long idContratto);
	
}
