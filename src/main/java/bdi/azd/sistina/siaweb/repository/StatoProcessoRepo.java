package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.StatoProcesso;

@Repository
public interface StatoProcessoRepo extends JpaRepository<StatoProcesso, Long> {

	StatoProcesso findByIdStProcesso(long idStProcesso);

}
