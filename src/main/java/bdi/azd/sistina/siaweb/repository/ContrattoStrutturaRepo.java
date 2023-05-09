package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;

@Repository
public interface ContrattoStrutturaRepo extends JpaRepository<ContrattoStruttura, Long> {

	ContrattoStruttura findByIdContrattoStruttura(long idContrattoStruttura);
	
	@Query("select a.idContrattoStruttura from ContrattoStruttura a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and a.ruoloStruttura.idRuoloStr=:idRuolo ")
	List<Long> findIdContrattoStrutturaByContrattoIdContrattoAndDataFineVal(long idContratto, long idRuolo);
	
	@Query("select c from ContrattoStruttura c where c.ruoloStruttura.idRuoloStr=:idRuolo and c.contratto.idContratto=:idContratto and c.dataFineVal is not null order by c.dataFineVal desc nulls last")
	List<ContrattoStruttura> findStruttureChiuse(long idContratto, long idRuolo);
	
	@Query("select a from ContrattoStruttura a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and a.ruoloStruttura.idRuoloStr=:idRuolo ")
	List<ContrattoStruttura> findStrutturaAperta(long idContratto, long idRuolo);
}
