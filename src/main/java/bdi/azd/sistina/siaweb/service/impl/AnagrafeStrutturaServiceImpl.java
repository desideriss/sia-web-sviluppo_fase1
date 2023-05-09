package bdi.azd.sistina.siaweb.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.AnagrafeStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.StrutturaDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.AnagrafeStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoStrutturaRepo;
import bdi.azd.sistina.siaweb.service.AnagrafeStrutturaService;


@Service
public class AnagrafeStrutturaServiceImpl implements AnagrafeStrutturaService {

	
    final AnagrafeStrutturaRepo anagrafeStrutturaRepository;
    final ContrattoStrutturaRepo contrattoStrutturaRepo;
    final DtoMapper dtoMapper;
    
    public AnagrafeStrutturaServiceImpl(AnagrafeStrutturaRepo anagrafeStrutturaRepository,ContrattoStrutturaRepo contrattoStrutturaRepo,DtoMapper dtoMapper) {
        this.anagrafeStrutturaRepository = anagrafeStrutturaRepository;
        this.contrattoStrutturaRepo = contrattoStrutturaRepo;
        this.dtoMapper=dtoMapper;
    }

	@Override
	public List<AnagrafeStrutturaDTO> getAnagrafeStruttura(BigDecimal flagValidante) {
		
		List<AnagrafeStrutturaDTO> res= new ArrayList<AnagrafeStrutturaDTO>();
		try {
			List<AnagrafeStruttura> anagrafeStrutturas = anagrafeStrutturaRepository.findAll((root, query, criteriaBuilder) -> {
				Predicate flagValidantePredicate = null;

			    List<Predicate> allFilter = new ArrayList<>();
			    //flag Validante
			    if (flagValidante != null) {
			    	flagValidantePredicate = criteriaBuilder.equal(root.get("flagValidante"), flagValidante);
			        allFilter.add(flagValidantePredicate);
			    }
			    
			    //Combine both predicates
			    return criteriaBuilder.and(allFilter.toArray(new Predicate[0]));
			});
			
			res = dtoMapper.listAnagrafeStrutturaToListAnagrafeStrutturaDTO(anagrafeStrutturas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}

	@Override
	public List<StrutturaDTO> getStrutturaValidanteLike(String denominazione) {
		List<StrutturaDTO> listStrutture = new ArrayList<StrutturaDTO>();
		try {
			List<AnagrafeStruttura>conStruttList=anagrafeStrutturaRepository.findStrutturaValidante(BigDecimal.ONE,"%"+denominazione.toUpperCase()+"%");
			for(AnagrafeStruttura con:conStruttList) {
				StrutturaDTO s = new StrutturaDTO();
				s.setDenominazione(con.getDenominazione());
				s.setCodiceStruttura(con.getCodiceStruttura());
				s.setCodSiparium(con.getCodSiparium());
				s.setIdStruttura(con.getIdStruttura());
				listStrutture.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listStrutture;
	}

	@Override
	public List<StrutturaDTO> getStrutturaResponsabileLike(String denominazione) {
		List<StrutturaDTO> listStrutture = new ArrayList<StrutturaDTO>();
		
		try {
			List<AnagrafeStruttura>conStruttList=anagrafeStrutturaRepository.findStrutture("%"+denominazione.toUpperCase()+"%");
			
			for(AnagrafeStruttura con:conStruttList) {
				StrutturaDTO s = new StrutturaDTO();
				s.setDenominazione(con.getDenominazione());
				s.setCodiceStruttura(con.getCodiceStruttura());
				s.setCodSiparium(con.getCodSiparium());
				s.setIdStruttura(con.getIdStruttura());
				listStrutture.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listStrutture;
	}

	@Override
	public List<StrutturaDTO> getStrutturaDestinatariaLike(String denominazione) {
		List<StrutturaDTO> listStrutture = new ArrayList<StrutturaDTO>();
		try {
			List<AnagrafeStruttura>conStruttList=anagrafeStrutturaRepository.findStrutture("%"+denominazione.toUpperCase()+"%");
			for(AnagrafeStruttura con:conStruttList) {
				StrutturaDTO s = new StrutturaDTO();
				s.setDenominazione(con.getDenominazione());
				s.setCodiceStruttura(con.getCodiceStruttura());
				s.setCodSiparium(con.getCodSiparium());
				s.setIdStruttura(con.getIdStruttura());
				listStrutture.add(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listStrutture;
	}

	


}
