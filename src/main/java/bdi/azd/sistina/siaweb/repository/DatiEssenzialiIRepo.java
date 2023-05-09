package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.DatiEssenzialiI;

@Repository
public interface DatiEssenzialiIRepo extends JpaRepository<DatiEssenzialiI, Long> {

	List<DatiEssenzialiI> findByTabellaDato(String tabellaDato);
	
}
