package bdi.azd.sistina.siaweb.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;

@Repository
public interface AnagrafeStrutturaRepo extends JpaRepository<AnagrafeStruttura,Long> ,JpaSpecificationExecutor<AnagrafeStruttura> {


	AnagrafeStruttura getByCodiceStruttura(BigDecimal bigDecimal);
	
	@Query("select a from AnagrafeStruttura a where upper(a.denominazione) like :descrizione order by a.denominazione asc")
	List<AnagrafeStruttura> findStrutture( String descrizione);
	
	@Query("select a from AnagrafeStruttura a where  a.flagValidante=:flagValidante and upper(a.denominazione) like :descrizione order by a.denominazione asc ")
	List<AnagrafeStruttura> findStrutturaValidante( BigDecimal flagValidante, String descrizione);
	
	
}
