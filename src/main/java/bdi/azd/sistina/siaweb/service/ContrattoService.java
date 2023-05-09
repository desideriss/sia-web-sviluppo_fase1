package bdi.azd.sistina.siaweb.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.CheckStoricoAttoreFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoCronologiaFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoImportiFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoStruttureFilter;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoFilterDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoScaduto;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoSummaryDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaAggiungiDataFilterDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.DateContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.StatoProcessoDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.Cronologia;


@Service
public interface ContrattoService {
	
	/**
	 * metodo per il recupero dei dati dalla tabella Contratto 
	 * @param codiceContratto string che indica il parametro CIG
	 * @return ContrattoDTO
	 */
    ContrattoDTO getContratto(String codiceContratto);
    
    /**
     * metodo per il recupero di una lista di contratti dati alcuni campi
     * @param contrattoFilterDTO
     * @return lista di contratti
     */
    Set<RicercaContrattoDTO> findContratti(ContrattoFilterDTO contrattoFilterDTO);
    
    /**
     * metodo per il recupero di tutti i contratti
     * @return lista di contratti
     */
	List<RicercaContrattoDTO> getListaContratti();
    
    /**
     * metodo per il recupero dei contratti dato uno statoProcesso
     * @param statoProcesso
     * @return List<ContrattoSummaryDTO>
     */
    List<ContrattoSummaryDTO> getContrattiByStatoProcesso(StatoProcessoDTO statoProcesso);
    
    /**
     * metodo per l'export dei contratti per statoProcesso
     * @param contrattiList
     * @return String
     */
    String downloadContrattiByStatoProcesso(List<ContrattoSummaryDTO>contrattiList);
    
    /**
     * metodo per l'inserimento di un contratto
     * @param ContrattoDTO
     * @return ContrattoDTO che è stato creato
     */
    ContrattoDTO insertContratto(ContrattoDTO contratto);
    
    /**
     * metodo per il recupero di un contratto dato l'id
     * @param id
     * @return ContrattoDTO
     */
    ContrattoDTO findContrattoById(long id);
    
    /**
     * metodo per il recupero di un contratto dato il cig
     * @param cig
     * @return ContrattoDTO
     */
    ContrattoDTO findContrattoByCig(String cig);
    
    /**
     * metodo per l'eliminazione di un contratto
     * @param long id
     */
    void deleteContratto(long idContratto);
    
	List<RicercaContrattoDTO> getRicercaCigPadre(String cigContratto);

   
    
    /**
     * metodo per il recupero di una lista di nominativi per l'autocomplete
     * @param nominativo
     * @return lista di String contenenti i nominativi 
     */
    List<String> getRupNominativoLike(String nominativo);
    
    /**
     * metodo per la gestione degli stati del contratto
     * @param contrattoIn
     * @return contrattoOut
     */
    Contratto aggiornaStati(Contratto contrattoIn);
    
    
	

    List<ContrattoScaduto> getContractByRangeDate(Pageable pageable);

	List<String> getListUserId(String userid);
	
	public String downloadContrattiInScadenza(List<ContrattoScaduto> contrattoScadutiList);
	
	/**
	 *  metodo per l'aggiunta di una data in cronologia 
	 * @param dataIn
	 * @return lista cronologie
	 */
	List<Cronologia> aggiungiDateCronologiaContratto(CronologiaAggiungiDataFilterDTO contrattoIn);

	DateContrattoDTO getDateContratto(long idContratto);
	
	/**
     * metodo per l'export dei contratti per ricerca
     * @param ContrattoSummaryDTO
     * @return String
     */
    String downloadContrattiByRicerca(List<RicercaContrattoDTO>contrattiList);
    
    /**
     * metodo per verificare l'esistenza di un contratto
     * @param cig
     * @return boolean 
     */
    boolean getEsistenzaContratto(String cig);
    
    /**
     * metodo per recuperare lo storico struttura
     * @param filtroStorico
     * @return ContrattoStrutturaDTO
     */
    ContrattoStrutturaDTO getStoricoStruttura(CheckStoricoStruttureFilter filtroStorico);
    
    /**
     * metodo per recuperare lo storico attore
     * @param filtroStorico
     * @return AttoreContrattoDTO
     */
    AttoreContrattoDTO getStoricoAttore(CheckStoricoAttoreFilter filtroStorico);
    
    /**
     * metodo per recuperare lo storico cronologia
     * @param filtroStorico
     * @return cronologiaDTO
     */
    CronologiaDTO getStoricoCronologia(CheckStoricoCronologiaFilter filtroStorico);
    
    /**
     * metodo per recuperare lo storico importo
     * @param filtroStorico
     * @return importoDTO
     */
    ImportoDTO getStoricoImporto(CheckStoricoImportiFilter filtroStorico);
}
