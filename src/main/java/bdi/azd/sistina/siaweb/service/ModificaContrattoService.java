package bdi.azd.sistina.siaweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.IntegrazioneFullDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.Integrazione;

@Service
public interface ModificaContrattoService {
	/**
	 * metodo per la modifica di un contratto
	 * @param ContrattoDTO
	 */
	void modificaContratto(ContrattoDTO contrattoIn);

	/**
	 * metodo per la modifica dei dati generali di un contratto
	 * 
	 * @param ModificaContrattoDTO
	 * @return Contratto
	 */
	Contratto modificaDatiGeneraliContratto(ContrattoDTO contrattoIn);

	/**
	 * metodo per modifica riferimento di un contratto.
	 * 
	 * @param RiferimentoDTO
	 */
	void modificaRiferimentoContratto(List<RiferimentoDTO> riferimentoDTO, long idContratto);

	/**
	 * metodo per la verifica della procedura
	 * @param ContrattoDTO
	 */
	void modificaProceduraContratto(ContrattoDTO contrattoIn);

	/**
	 * Metodo per la modifica delle date relative al contratto nella tabella
	 * Cronologia
	 * 
	 * @param cronologiaDTO
	 * @return
	 */
//	List<Cronologia> modificaDateCronologiaContratto(ContrattoDTO contrattoIn);

	/**
	 * metodo per la modifica degli importi legati ad un contratto
	 * @param importoModDTO
	 * @param idContratto
	 */




	


	/**
	 * @param contrattoStrutturaModifificaDTO
	 * @return
	 */
	void modificaContrattoStruttura(List<ContrattoStrutturaDTO> contrattoStrutturaDTO, long idContratto);

	void modificaFornitoreContratto(List<FornitoreContrattoDTO> fornitoreContrattoIn, long idContratto);



	/**
	 * Metodo per la modifica in Cronologia
	 * recupera il record relativo all'idContratto e idTipCronologia in input con dataFineVal = null
	 * imposta la dataFineVal = sysdate - 1
	 * inserisce il nuovo record
	 */

	void modificaDateCronologiaContratto(List<CronologiaDTO> cronologiaDTO, long idContratto);

	void modificaAttoreContratto(List<AttoreContrattoDTO> attoreContrattoDTO, long idContratto);


	



	/**
	 * Metodo per il salvataggio di un integrazione. 
	 * Verifica che uno tra cf e partita iva sia valorizzato 
	 * Verifica che pmi sia valorizzato 
	 * Imposta la data aggiornamento con la data odierna 
	 */
//	void modificaIntegrazioneContratto(ContrattoIntegrazioneDTO contrattoIn);

	/**
	 * Metodo per il salvataggio di un integrazione. 
	 * Verifica che uno tra cf e partita iva sia valorizzato 
	 * Verifica che pmi sia valorizzato 
	 * Imposta la data aggiornamento con la data odierna 
	 */
	void saveDatiIntegrazioni(IntegrazioneFullDTO integrazioneDTO, Integrazione integrazione);



	void modificaIntegrazione(List<IntegrazioneFullDTO> integrazioneList, long idContratto);

	void modificaImportoContratto(ContrattoDTO contrattoIn,long idContratto);

	




}