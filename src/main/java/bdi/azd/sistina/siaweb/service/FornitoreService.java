package bdi.azd.sistina.siaweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreFilterDTO;

@Service
public interface FornitoreService {

	//Page<FornitoreDTO> findFornitori(int pageNumber, int resultsForPage, String sortDirection, String sortParam ,String ragioneSociale, String codiceFiscale, String partitaIva);

	List<FornitoreDTO> findFornitori(FornitoreFilterDTO fornitoreFilter);

	FornitoreDTO saveFornitori(FornitoreDTO fornitoreDTO);

	FornitoreDTO updateFornitore(long id, FornitoreDTO fornitoreDTO);
   
	List<FornitoreDTO> getListaFornitori();
	
	void deleteFornitore(FornitoreDTO fornitoreDto);

	FornitoreDTO findByIdFornitore(long idFornitore);
	
	/**
	 * metodo per l'autocomplete della ragione sociale
	 * @param ragioneSociale
	 * @return lista di String
	 */
	List<FornitoreDTO> findRagioneSociale(String ragioneSociale, boolean like);

}
