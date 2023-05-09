package bdi.azd.sistina.siaweb.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.dto.FornitoreFilterDTO;
import bdi.azd.sistina.siaweb.entity.Fornitore;

@Repository
public class FornitoreRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Fornitore> findFornitori(FornitoreFilterDTO fornitoreFilter) {

		// recupero il criteriaBuilder dell'em
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Fornitore> query = criteriaBuilder.createQuery(Fornitore.class);

		// settiamo la root ovvero la tabella sulla quale dobbiamo effettuare la query
		Root<Fornitore> root = query.from(Fornitore.class);

		Predicate predicateForCodFisc = null;
		Predicate predicateForRagSoc = null;
		Predicate predicateForPIva = null;

		// creiamo una lista di predicati ovvero di where condition che vengono aggiunte
		// solo se il parametro passato in input non è null
		List<Predicate> allFilter = new ArrayList<>();
		// Ragione Sociale
		if (fornitoreFilter.getRagioneSociale() != null && !fornitoreFilter.getRagioneSociale().isEmpty()) {
			predicateForRagSoc = criteriaBuilder.like(criteriaBuilder.upper(root.get("ragioneSociale")),
					"%"+fornitoreFilter.getRagioneSociale() + "%");
			allFilter.add(predicateForRagSoc);
		}
		// Partita Iva
		if (fornitoreFilter.getPartitaIva() != null && !fornitoreFilter.getPartitaIva().isEmpty()) {
			predicateForPIva = criteriaBuilder.equal(root.get("partitaIva"), fornitoreFilter.getPartitaIva());
			allFilter.add(predicateForPIva);
		}
		// Codice Fiscale
		if (fornitoreFilter.getCodiceFiscale() != null && !fornitoreFilter.getCodiceFiscale().isEmpty()) {
			predicateForCodFisc = criteriaBuilder.equal(root.get("codiceFiscale"), fornitoreFilter.getCodiceFiscale());
			allFilter.add(predicateForCodFisc);
		}

		// componiamo la query: select * from (root=Fornitore) where (filtri inseriti in
		// lista)
		query.select(root).where(allFilter.toArray(new Predicate[] {}));
		
		query.orderBy(criteriaBuilder.asc(root.get("ragioneSociale")));

		// passa la query composta all'em che ritorna una lista di Fornitori
		return entityManager.createQuery(query).getResultList();

	}

}
