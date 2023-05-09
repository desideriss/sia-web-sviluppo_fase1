package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Riferimento;

@Repository
public interface RiferimentoRepo extends JpaRepository<Riferimento, Long> {

	Riferimento findByIdRiferimento(long idRiferimento);
	
	List<Riferimento> findByContrattoIdContratto(long idContratto);
	
	@Query("select a.idRiferimento from Riferimento a where a.contratto.idContratto=:idContratto")
	List<Long> findIdRiferimentoByContrattoIdContratto(long idContratto);

}
