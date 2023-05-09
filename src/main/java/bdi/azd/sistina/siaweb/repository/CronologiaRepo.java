package bdi.azd.sistina.siaweb.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Cronologia;

@Repository
public interface CronologiaRepo extends JpaRepository<Cronologia, Long>, JpaSpecificationExecutor<Cronologia> {
	
	@Modifying
	@Transactional
	@Query(value="update Cronologia c set c.dataFineVal = sysdate -1, c.dtFineEvento = :dtFineEvento  where c.contratto.idContratto = :idContratto and c.tipoCronologia.idTpCronologia = :idTpCronologia and c.dataFineVal = null")
	void updateByIdContratto(@Param("idContratto") long idContratto, @Param("idTpCronologia") long idTpCronologia, @Param("dtFineEvento") Date dtFineEvento);
	
	@Query(value="select c from Cronologia c where c.contratto.idContratto = :idContratto and DATA_FINE_VAL = null and c.tipoCronologia.idTpCronologia = :idTpCronologia")
	List<Cronologia> searchByIdContratto(@Param("idContratto") long idContratto, @Param("idTpCronologia") long idTpCronologia);
	
	List<Cronologia> findByContrattoIdContratto(long idContratto);
	
	@Query(value="select c from Cronologia c where c.contratto.idContratto = :idContratto and c.dataFineVal is null ")
	List<Cronologia> findByIdContratto(@Param("idContratto") long idContratto);

	Cronologia findByIdCronologia(long idCronologia);
	
	@Query("select a.idCronologia from Cronologia a where a.contratto.idContratto=:idContratto and a.dataFineVal is null ")
	List<Long> findIdCronologiaByContrattoIdContrattoAndDataFineVal(long idContratto);
	
	@Query("select c from Cronologia c where c.contratto.idContratto=:idContratto and c.integrazione.idIntegrazione is null and c.tipoCronologia.idTpCronologia = :idTpCronologia and c.dataFineVal is not null order by c.dataFineVal desc nulls last")
	List<Cronologia> findCronologieChiuse(long idContratto,long idTpCronologia);

	@Query("select a from Cronologia a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and a.tipoCronologia.idTpCronologia = :idTpCronologia")
	List<Cronologia> findCronologiaAperta(long idContratto,long idTpCronologia);
}
