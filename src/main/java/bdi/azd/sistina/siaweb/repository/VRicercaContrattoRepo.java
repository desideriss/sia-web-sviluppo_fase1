package bdi.azd.sistina.siaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.VRicercacontratto;

@Repository
public interface VRicercaContrattoRepo extends JpaRepository<VRicercacontratto, String>, JpaSpecificationExecutor<VRicercacontratto> {
    
}
