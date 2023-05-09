package bdi.azd.sistina.siaweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.entity.Fornitore;

@Repository
public interface FornitoreRepo extends JpaRepository<Fornitore, Long>, JpaSpecificationExecutor<Fornitore> {
    Page<Fornitore> findAllByPartitaIvaAndCodiceFiscaleAndRagioneSociale(String partitaIva, String codiceFiscale,
                                                                         String ragioneSociale, Pageable paging);
    List<Fornitore> findByPartitaIvaAndCodiceFiscaleAndRagioneSociale(String partitaIva, String codiceFiscale,
                                                                      String ragioneSociale);
    Fornitore findByIdFornitore(long idFornitore);

    @Query("select f from Fornitore f where f.ragioneSociale like :ragsoc%")
    List<Fornitore> findFornitoreByRagioneSociale(@Param("ragsoc") String ragioneSociale);
    
    @Query(value="select * from Fornitore f where f.codice_Fiscale = :cf", nativeQuery = true)
   // @Query("select f from Fornitore f where f.codiceFiscale like :cf%")
    List<Fornitore> findFornitoreByCodiceFiscale(@Param("cf") String codiceFiscale);
    
    @Query(value="select * from Fornitore f where f.partita_Iva = :pi", nativeQuery = true)
     List<Fornitore> findFornitoreByPartitaIva(@Param("pi") String partitaIva);
    
    @Query("select f from Fornitore f where upper(f.ragioneSociale) like :ragioneSociale")
    List<Fornitore> findRagioneSocialeLike(String ragioneSociale);
    
    @Query("select f from Fornitore f where f.ragioneSociale is not null and upper(f.ragioneSociale) = :ragioneSociale")
    List<Fornitore> findRagioneSociale(String ragioneSociale);
    
    @Query("select f from Fornitore f where f.partitaIva is not null and upper(f.partitaIva) = :partitaIva")
    List<Fornitore> findPartitaIva(String partitaIva);
    
    @Query("select f from Fornitore f where f.codiceFiscale is not null and upper(f.codiceFiscale) = :codiceFiscale")
    List<Fornitore> findCodiceFiscale(String codiceFiscale);
}
