package bdi.azd.sistina.siaweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.dto.ContrattoRecordDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;

@Repository
public interface ContrattoRepo extends JpaRepository<Contratto,Long>  {

	Contratto findByCig(String codiceContratto);
	
	Contratto findByIdContratto(long idContratto);
	
	List<Contratto> findAllByStatoProcesso(StatoProcesso statoProcesso);

	List<Contratto> findAllByCigPadre(String cigContratto);

	@Query("SELECT c.cig, c.cigPadre, tCig.tipoCig, tCon.tipoContratto, c.descrizioneCrtt, imp.valoreImp" +
			" From Contratto c " +
			"INNER JOIN TipoCig tCig ON " +
			"c.tipoCig.idTipoCig = tCig.idTipoCig " +
			"INNER JOIN TipoContratto tCon ON " +
			"c.tipoContratto.idTpContratto = tCon.idTpContratto " +
			"INNER JOIN Importo imp ON " +
			"c.idContratto = imp.contratto.idContratto " +
			"INNER JOIN TipoImporto tImp ON " +
			"tImp.idTipImporto = imp.tipoImporto.idTipImporto " +
			"INNER JOIN Cronologia cro ON " +
			"cro.contratto.idContratto = c.idContratto " +
			"INNER JOIN TipoCronologia tCro ON " +
			"tCro.idTpCronologia = cro.tipoCronologia.idTpCronologia " +
			"WHERE " +
			"tCro.tipoCronologia = 'Data Prima scadenza' " +
			"AND cro.dtInizioEvento between :startDate AND :endDate")
    List<ContrattoRecordDTO> findAllContrattiByDate(@Param("startDate")Date start, @Param("endDate")Date endDate, Pageable pageable);
}
