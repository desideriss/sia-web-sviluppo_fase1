package bdi.azd.sistina.siaweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.ProceduraDTO;


@Service
public interface ProceduraService {
	
	/**
	 * metodo per il recupero dei dati dalla tabella Procedura
	 *
	 * @param codiceProcedura string che indica il parametro CodProcedura
	 * @return ProceduraDTO
	 */
    ProceduraDTO getProceduraByCodiceProcedura(String codiceProcedura);
    
    /**
     * metodo per l'inserimento di un oggetto Procedura
     * @param ProceduraDTO
     * @return ProceduraDTO
     */
    ProceduraDTO insertProcedura(ProceduraDTO procedura);
    
    List<ProceduraDTO> getAllProcedure();
	
	List<ProceduraDTO> getProceduraByCodiceProceduraLike(String codiceProcedura);
    
}
