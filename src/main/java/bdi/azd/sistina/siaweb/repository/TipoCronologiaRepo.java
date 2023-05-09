package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.TipoCronologia;

@Repository
public interface TipoCronologiaRepo extends JpaRepository<TipoCronologia,Long> {

	
}
