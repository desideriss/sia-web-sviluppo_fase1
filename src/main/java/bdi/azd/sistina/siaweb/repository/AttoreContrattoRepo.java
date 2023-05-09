package bdi.azd.sistina.siaweb.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;

@Repository
public interface AttoreContrattoRepo extends JpaRepository<AttoreContratto, Long> {

	@Query("select distinct ac.attore.nominativo from AttoreContratto ac where upper(ac.attore.nominativo) like %:nominativo% and ac.ruoloAttoreBean.idRuoloAttore=1")
	List<String> findRupNominativo(String nominativo);

	AttoreContratto findByIdAttoreContratto(long idAttoreContratto);
	
	@Query("select ac.attore.userid from AttoreContratto ac where ac.attore.userid like :userIdNew% and ac.ruoloAttoreBean.idRuoloAttore=1")
	List<String> findUseridAggior(String userIdNew);

	AttoreContratto findByIdAttoreContrattoAndDataFineVal(long idAttoreContratto, Timestamp dataFineVal);
	
	List<AttoreContratto> findByContrattoIdContratto(long idContratto);
	
	@Query("select a.idAttoreContratto from AttoreContratto a where a.contratto.idContratto=:idContratto and a.dataFineVal is null")
	List<Long> findIdAttoreContrattoByContrattoAndDataFineVal(long idContratto);
	
	@Query("select c from AttoreContratto c where c.contratto.idContratto=:idContratto and c.ruoloAttoreBean.idRuoloAttore=:idRuolo and c.dataFineVal is not null and c.codServAtt<>999 order by c.dataFineVal desc nulls last")
	List<AttoreContratto> findAttoreContrattoChiusi(long idContratto,long idRuolo);
	
	@Query("select a from AttoreContratto a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and a.ruoloAttoreBean.idRuoloAttore=:idRuolo ")
	List<AttoreContratto> findAttoreContrattoAperto(long idContratto, long idRuolo);
	
	@Query("select c from AttoreContratto c where c.contratto.idContratto=:idContratto and c.ruoloAttoreBean.idRuoloAttore=:idRuolo and c.dataFineVal is not null and c.codServAtt=999 order by c.dataFineVal desc nulls last")
	List<AttoreContratto> findAttoreContrattoChiusi999(long idContratto,long idRuolo);
	
}
