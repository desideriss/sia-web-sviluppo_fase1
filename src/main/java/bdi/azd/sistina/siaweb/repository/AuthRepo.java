package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.JWTToken;

@Repository
public interface AuthRepo extends JpaRepository<JWTToken, String> {
	
	JWTToken findByJwtToken(String jwtToken);

}
