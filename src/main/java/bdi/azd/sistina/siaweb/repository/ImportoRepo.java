package bdi.azd.sistina.siaweb.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Importo;

@Repository
public interface ImportoRepo extends JpaRepository<Importo, Long> {

	Importo findByContrattoIdContrattoAndTipoImportoIdTipImportoAndDataFineVal(long idContratto, long idTipImporto,
			Timestamp dataFineVal);
	
	Importo findByIdImporto(Long idImporto);
	
	@Query("select a.idImporto from Importo a where a.contratto.idContratto=:idContratto and a.dataFineVal is null")
	List<Long> findIdImportoByContrattoIdContrattoAndDataFineVal(long idContratto);
	
	@Query("select c from Importo c where  c.contratto.idContratto=:idContratto and c.integrazione.idIntegrazione is null and c.tipoImporto.idTipImporto=:idTipoImporto and c.dataFineVal is not null order by c.dataFineVal desc nulls last")
	List<Importo> findImportiChiusi(long idContratto, long idTipoImporto);
	
	@Query("select a from Importo a where a.contratto.idContratto=:idContratto and a.dataFineVal is null and a.tipoImporto.idTipImporto=:tipoImporto")
	List<Importo> findImportoAperto(long idContratto,long tipoImporto);
}
