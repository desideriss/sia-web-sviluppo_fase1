package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Attore;

@Repository
public interface AttoreRepo extends JpaRepository<Attore, Long> {

	Attore findByIdAttore(long l);

	Attore findByUserid(String userId);
	
}
