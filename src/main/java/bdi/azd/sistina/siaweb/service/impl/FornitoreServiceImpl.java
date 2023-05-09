package bdi.azd.sistina.siaweb.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.constants.ErrorMessage;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreFilterDTO;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.exception.BadRequestException;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.FornitoreRepo;
import bdi.azd.sistina.siaweb.repository.custom.FornitoreRepositoryCustom;
import bdi.azd.sistina.siaweb.service.FornitoreService;
import bdi.azd.sistina.siaweb.util.UserUtil;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class FornitoreServiceImpl implements FornitoreService {
    final FornitoreRepo fornitoreRepo;

    final DtoMapper dtoMapper;
    
    final FornitoreRepositoryCustom fornitoreRepositoryCustom;
    

    public FornitoreServiceImpl(FornitoreRepo fornitoreRepo, DtoMapper dtoMapper, FornitoreRepositoryCustom fornitoreRepositoryCustom) {
        this.fornitoreRepo = fornitoreRepo;
        this.dtoMapper = dtoMapper;
        this.fornitoreRepositoryCustom=fornitoreRepositoryCustom;
    }
    
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *Metodo per la ricerca del fornitore, controlla nel dto i campi valorizzati e costruisce la query
     */
    @Override
    public List<FornitoreDTO> findFornitori(FornitoreFilterDTO fornitoreFilter) {
        List<Fornitore> fornitores = fornitoreRepositoryCustom.findFornitori(fornitoreFilter);
        return dtoMapper.listFornitoreToListFornitoreDTO(fornitores);
    }

    /**
     * Metodo per il salvataggio di un fornitore. 
     * Verifica che uno tra cf e partita iva sia valorizzato 
     * Verifica che pmi sia valorizzato 
     * Imposta la data aggiornamento con la data odierna 
     */

    @Override
    public FornitoreDTO saveFornitori(FornitoreDTO fornitoreDTO) {
       if ((fornitoreDTO.getCodiceFiscale() == null || fornitoreDTO.getCodiceFiscale().isEmpty())
                && (fornitoreDTO.getPartitaIva() == null || fornitoreDTO.getPartitaIva().isEmpty()))
            throw new BadRequestException(ErrorMessage.FORNITORE_EXCEPTION_PI_CF);

//        if (fornitoreDTO.getPmi() == null || fornitoreDTO.getPmi().isEmpty())
//            throw new BadRequestException(ErrorMessage.FORNITORE_EXCEPTION_PMI);

        Fornitore fornitore = dtoMapper.fornitoreDtoTofornitore(fornitoreDTO);
        fornitore.setDataAggior((new Timestamp(System.currentTimeMillis())));
        fornitore.setUseridAggior(UserUtil.getLoggedUserId());
        return salvataggio(fornitore);
    }
    
    /**
     * Metodo per l'aggiornamento di un fornitore 
     * Verfica che uno tra CF e Partita IVA sia valorizzato
     */

    @Override
    public FornitoreDTO updateFornitore(long id, FornitoreDTO fornitoreDTO) {

        Fornitore fornitore = fornitoreRepo.findByIdFornitore(id);
        if (fornitore == null)
            throw new ResourceNotContentException(ErrorMessage.FORNITORE_NOT_FOUND);

        if ((fornitoreDTO.getCodiceFiscale() == null || fornitoreDTO.getCodiceFiscale().isEmpty()) &&
                (fornitoreDTO.getPartitaIva() == null || fornitoreDTO.getPartitaIva().isEmpty()))
            throw new BadRequestException(ErrorMessage.FORNITORE_EXCEPTION_PI_CF);

        fornitore.setCodiceFiscale(fornitoreDTO.getCodiceFiscale());
        fornitore.setPartitaIva(fornitoreDTO.getPartitaIva());
        fornitore.setRagioneSociale(fornitoreDTO.getRagioneSociale());
        fornitore.setPmi(fornitoreDTO.getPmi());
        fornitore.setDataAggior((new Timestamp((new Date()).getTime())));
        fornitore.setUseridAggior(UserUtil.getLoggedUserId());
        
        
        return salvataggio(fornitore);
    }
    
    /**
     * Metodo che effettua il salvataggio 
     * Verifica che il fornitore non sia già presenta
     * @param fornitore
     * @return l'oggetto inserito
     */

    private FornitoreDTO salvataggio(Fornitore fornitore) {
        try {
    		checkIntegrity(fornitore.getPartitaIva(),fornitore.getIdFornitore(),"PIVA");
    		checkIntegrity(fornitore.getCodiceFiscale(),fornitore.getIdFornitore(),"CF");
    		checkIntegrity(fornitore.getRagioneSociale(),fornitore.getIdFornitore(),"RAG_SOC");
            Fornitore saved = fornitoreRepo.save(fornitore);
            return dtoMapper.fornitoreToFornitoreDTO(saved);
        } catch (DataIntegrityViolationException exception){
        	exception.printStackTrace();
        	throw new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT);
		}
    }


	
	public void checkIntegrity(String item, Long idFornitore, String campo) {

		List<Fornitore> find = new ArrayList<>();

		if (item != null && !item.isEmpty()) {
			switch (campo) {
			case "PIVA":
				find = fornitoreRepo.findPartitaIva(item.toUpperCase());
				break;
			case "CF":
				find = fornitoreRepo.findCodiceFiscale(item.toUpperCase());
				break;
			case "RAG_SOC":
				find = fornitoreRepo.findRagioneSociale(item.toUpperCase());
				break;
			default:
				break;
			}
			if (find != null && !find.isEmpty()) {
				if (find.get(0).getIdFornitore() == null || idFornitore==null || !idFornitore.equals(find.get(0).getIdFornitore())) {
					throw new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT);
				}
			}
		}
	}
	
	

    /**
     * Metodo per la cancellazione del fornitore eseguita per idFornitore
     */

    @Override
    public void deleteFornitore(FornitoreDTO fornitoreDto) {
        Fornitore fornitore = fornitoreRepo.findByIdFornitore(fornitoreDto.getIdFornitore());
        if (fornitore != null)
            fornitoreRepo.delete(fornitore);
    }


    /**
     * Metodo che esegue la ricerca di un fornitore per ID
     */
    
    @Override
    public FornitoreDTO findByIdFornitore(long idFornitore) {
        Fornitore f = fornitoreRepo.findByIdFornitore(idFornitore);
        return dtoMapper.fornitoreToFornitoreDTO(f);
    }

    /**
     * Metodo che restituisce la lista completa dei fornitori 
     */
    
    @Override
    public List<FornitoreDTO> getListaFornitori() {
        List<Fornitore> lFornitore = fornitoreRepo.findAll();
        return dtoMapper.listFornitoreToListFornitoreDTO(lFornitore);
    }

	
	@Override
	public List<FornitoreDTO> findRagioneSociale(String ragioneSociale, boolean like) {
		List<FornitoreDTO> listForn= new ArrayList<FornitoreDTO>();
		List<Fornitore>ragioneSocialeList=null;
		try {
			if(like) {
				ragioneSocialeList= fornitoreRepo.findRagioneSocialeLike("%"+ragioneSociale.toUpperCase()+"%");
			}else {
				ragioneSocialeList= fornitoreRepo.findRagioneSociale(ragioneSociale.toUpperCase());
			}
		listForn = new ArrayList<>();
		for(Fornitore forn:ragioneSocialeList) {
			FornitoreDTO f = new FornitoreDTO();
			f.setRagioneSociale(forn.getRagioneSociale());
			f.setCodiceFiscale(forn.getCodiceFiscale());
			f.setPartitaIva(forn.getPartitaIva());
			f.setPmi(forn.getPmi());
			f.setIdFornitore(forn.getIdFornitore());
			listForn.add(f);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return listForn;
	}

}
