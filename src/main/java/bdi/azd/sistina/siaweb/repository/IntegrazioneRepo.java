package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Integrazione;

@Repository
public interface IntegrazioneRepo extends JpaRepository<Integrazione, Long> {

	Integrazione findByIdIntegrazione(long id);
	
	@Query("select i.idIntegrazione from Integrazione i where i.contrattoCigOrigine.idContratto=:idContratto")
	List<Long> findIdIntegrazioneByContrattoCigOrigine(long idContratto);
	
	
//	@Query("select i.idIntegrazione from Integrazione i where i.cigGenerato=:idContratto")
//	List<Long> findIdIntegrazioneByContrattoCigGenerato(long idContratto);

}
