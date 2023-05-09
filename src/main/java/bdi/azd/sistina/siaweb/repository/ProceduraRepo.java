package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Procedura;

@Repository
public interface ProceduraRepo extends JpaRepository<Procedura,Long>  {

	@Query("select p from Procedura p where p.codProcedura like :codiceProcedura%")
	List<Procedura> findByCodProceduraLike(String codiceProcedura);

	Procedura findByIdProcedura(long idProcedura);
	
	Procedura findByCodProcedura(String codiceProcedura);
	
}
