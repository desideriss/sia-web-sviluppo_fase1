package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.StoricoEssenzVal;

@Repository
public interface StoricoEssenzValRepo extends JpaRepository<StoricoEssenzVal, Long> {

	long countByContrattoIdContratto(long idContratto);
	
	List<StoricoEssenzVal> findByContrattoIdContratto(long idContratto);

}
