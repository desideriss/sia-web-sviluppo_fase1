package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.TipoRiferimento;

@Repository
public interface TipoRiferimentoRepo extends JpaRepository<TipoRiferimento,Long> {

	
}
