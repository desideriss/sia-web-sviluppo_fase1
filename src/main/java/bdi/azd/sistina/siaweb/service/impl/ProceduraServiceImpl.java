package bdi.azd.sistina.siaweb.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.ProceduraDTO;
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.ProceduraRepo;
import bdi.azd.sistina.siaweb.service.ProceduraService;

@Service
public class ProceduraServiceImpl implements ProceduraService {


	final ProceduraRepo proceduraRepo;
	final DtoMapper dtoMapper;
	

	public ProceduraServiceImpl(ProceduraRepo proceduraRepo,DtoMapper dtoMapper) {

		this.proceduraRepo = proceduraRepo;
		this.dtoMapper=dtoMapper;
	}


	@Override
	public ProceduraDTO getProceduraByCodiceProcedura(String codiceProcedura) {
		Procedura procedura=proceduraRepo.findByCodProcedura(codiceProcedura);
		return dtoMapper.proceduraEntityToProceduraDTO(procedura);
	}


	@Override
	public ProceduraDTO insertProcedura(ProceduraDTO proceduraDTO) {
		Procedura procedura= proceduraRepo.save(dtoMapper.proceduraDTOToProcedura(proceduraDTO));
		return dtoMapper.proceduraEntityToProceduraDTO(procedura);
	}


	@Override
	public List<ProceduraDTO> getAllProcedure() {
		return dtoMapper.proceduraEntityListToProceduraDTOList(proceduraRepo.findAll());
	}

	@Override
	public List<ProceduraDTO> getProceduraByCodiceProceduraLike(String codiceProcedura) {
		List<Procedura> procedure=proceduraRepo.findByCodProceduraLike("%"+codiceProcedura+"%");
		return dtoMapper.proceduraEntityListToProceduraDTOList(procedure);
	}


}
