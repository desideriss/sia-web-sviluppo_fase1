package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.RuoloFornitore;

@Repository
public interface TipoRuoloFornitoreRepo extends JpaRepository<RuoloFornitore, String>, JpaSpecificationExecutor<RuoloFornitore> {

}
