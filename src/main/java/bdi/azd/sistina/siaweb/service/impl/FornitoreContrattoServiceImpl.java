package bdi.azd.sistina.siaweb.service.impl;


import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.RuoloFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.StatoProcessoRepo;
import bdi.azd.sistina.siaweb.repository.TipoFornitoreRepo;
import bdi.azd.sistina.siaweb.service.FornitoreContrattoService;


@Service
public class FornitoreContrattoServiceImpl implements FornitoreContrattoService {

	final StatoProcessoRepo statoProcessoRepo;
	final FornitoreContrattoRepo fornitoreRepository;
    final RuoloFornitoreRepo ruoloFornitoreRepo;
    final ContrattoRepo contrattoRepo;
    final TipoFornitoreRepo tipoFornitoreRepo;
    final DtoMapper dtoMapper;

    public FornitoreContrattoServiceImpl(FornitoreContrattoRepo fornitoreRepository,TipoFornitoreRepo tipoFornitoreRepo,ContrattoRepo contrattoRepo,RuoloFornitoreRepo ruoloFornitoreRepo,StatoProcessoRepo statoProcessoRepo,DtoMapper dtoMapper) {
    	this.statoProcessoRepo = statoProcessoRepo;
        this.fornitoreRepository = fornitoreRepository;
		this.ruoloFornitoreRepo = ruoloFornitoreRepo;
		this.contrattoRepo=contrattoRepo;
		this.tipoFornitoreRepo=tipoFornitoreRepo;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public Integer getCountFornitoreContrattoByIdFornitore(long idFornitore) {
        return fornitoreRepository.getCountFornitoreContrattoByPiva(idFornitore);
    }

	

}
