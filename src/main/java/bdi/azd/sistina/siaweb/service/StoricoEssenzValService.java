package bdi.azd.sistina.siaweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.StoricoEssenzValDTO;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;

@Service
public interface StoricoEssenzValService {
	
    /**
     * Ritorna la lista dei record StoricoEssenzVal dato l'id del Contratto.
     * 
     * @param idContratto
     * @return
     */
	public List<StoricoEssenzValDTO> getStoricoEssenzVal(Long idContratto);
	
    /**
     * Cancella tutti i record dato l'id del Contratto. 
     * 
     * @param idContratto
     */
	public void deleteStoricoEssenzVal(Long idContratto);
	
    /**
     * Inserisce un nuovo record in StoricoEssenzVal.
     * 
     * @param idContratto
     * @param idDatiEssenzialiI
     * @param oldValue
     * @param newValue
     */
	public void newStoricoEssenzVal(Long idContratto, Long idDatiEssenzialiI, String oldValue, String newValue);
	
    /**
     * Aggiorna la Motivazione e il flag StatoAccettaz.
     * 
     * @param storicoEssenzVal
     */
	public void updateStoricoEssenzVal(StoricoEssenzValDTO storicoEssenzVal);
	
    /**
     * Controlla se i dati Essenziali del Fornitore in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
     * 
     * @param idContratto
     * @param fornitoreDTO
     */
	public void checkFornitoreStoricoEssenzVal(Long idContratto, FornitoreDTO fornitoreDTO);
	
    /**
     * Controlla se i dati Essenziali del FornitoreContratto in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
     * 
     * @param idContratto
     * @param fornitoreContrattoModificaDTOs
     */
	public void checkFornitoreContrattoStoricoEssenzVal(Long idContratto, List<FornitoreContrattoDTO> fornitoreContrattoModificaDTOs);
		
	/**
	 * Controlla se i dati Essenziali dell'AttoreContratto in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
	 * 
	 * @param idContratto
	 * @param attoreContrattoModificaDTO
	 */
	public void checkAttoreContrattoStoricoEssenzVal(AttoreContratto oldAT, AttoreContratto newAT);

	/**
	 * Controlla se i dati Essenziali di ContrattoStruttura in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
	 * 
	 * @param idContratto
	 * @param contrattoStrutturaModificaDTOs
	 */
	public void checkContrattoStrutturaStoricoEssenzVal(Long idContratto, List<ContrattoStrutturaDTO> contrattoStrutturaModificaDTOs);
	
	/**
	 * Controlla se i dati Essenziali di Cronologia in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
	 * 
	 * @param idContratto
	 * @param cronologiaDTOs
	 */
	public void checkCronologiaStoricoEssenzVal(Long idContratto, List<CronologiaDTO> cronologiaDTOs);
	
	/**
	 * Controlla se i dati Essenziali degli Importi in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
	 * 
	 * @param idContratto
	 * @param importoModificaDTOs
	 */
	public void checkImportoStoricoEssenzVal(Long idContratto, List<ImportoDTO> importoModificaDTOs);
	
	/**
	 * Controlla se i dati Essenziali dei Riferimenti in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
     * 
	 * @param idContratto
	 * @param riferimentoDTOs
	 */
	public void checkRiferimentoStoricoEssenzVal(Long idContratto, List<RiferimentoDTO> riferimentoDTOs);
	
    /**
     * Controlla se i dati Essenziali del Contratto in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
     * 
     * @param contrattoDTO
     */
	public void checkContrattoStoricoEssenzVal(ContrattoDTO contrattoDTO);
	
	/**
     * Controlla se i dati Essenziali di tutto il Contratto in input sono stati cambiati.
     * Se sono differenti vengono salvati nella tabella STORICO_ESSENZ_VAL
     * 
     * @param contrattoDTO
     */
	public void checkStoricoEssenzVal(ContrattoDTO contrattoDTO);
	
}
