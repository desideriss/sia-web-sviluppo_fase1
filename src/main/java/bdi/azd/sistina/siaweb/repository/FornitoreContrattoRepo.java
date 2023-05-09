package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.FornitoreContratto;

@Repository
public interface FornitoreContrattoRepo extends JpaRepository<FornitoreContratto, Long>, JpaSpecificationExecutor<FornitoreContratto> {

//	@Query("select count(f) from FornitoreContratto fc, Fornitore f " +
//			"where fc.id.idFornitore=f.idFornitore and f.idFornitore=:idFornitore")
	@Query("select count(fc.fornitore) from FornitoreContratto fc " +
			"where fc.fornitore.idFornitore=:idFornitore")
	Integer getCountFornitoreContrattoByPiva(long idFornitore);

	@Query("select fc from FornitoreContratto fc where fc.fornitore.idFornitore=:idFornitore")
	FornitoreContratto findByIdFornitore(long idFornitore);
	
	FornitoreContratto findByIdFornitoreContratto(long idFornitoreContratto);
	
	@Query("select a.idFornitoreContratto from FornitoreContratto a where a.contratto.idContratto=:idContratto and a.dataFineVal is null")
	List<Long> findIdFornitoreContrattoByContrattoIdContrattoAndDataFineVal(long idContratto);
	
	
	@Query("select a from FornitoreContratto a where a.contratto.idContratto=:idContratto and a.dataFineVal is null")
	List<FornitoreContratto> findFornitoriContrattiAperti(long idContratto);
	
	@Query("select a from FornitoreContratto a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and upper(a.fornitore.ragioneSociale)=:ragSoc")
	List<FornitoreContratto> findFornitoriContrattoByIdContrattoAndRagSoc(long idContratto, String ragSoc);



}
