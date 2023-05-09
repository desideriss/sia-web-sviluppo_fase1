package bdi.azd.sistina.siaweb.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import bdi.azd.sistina.siaweb.dto.ContrattoFilterDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
import bdi.azd.sistina.siaweb.entity.Attore;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.RuoloStruttura;
import bdi.azd.sistina.siaweb.entity.StatoContratto;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.util.DateUtil;

@Repository
public class ContrattoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Contratto> findContratti(ContrattoFilterDTO filter) {
		


		// recupero il criteriaBuilder dell'em
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contratto> query = criteriaBuilder.createQuery(Contratto.class);

		// settiamo la root ovvero la tabella sulla quale dobbiamo effettuare la query
		Root<Contratto> root = query.from(Contratto.class);
		

		// creiamo una lista di predicati ovvero di where condition che vengono aggiunte
		// solo se il parametro passato in input non è null
		List<Predicate> allFilter = new ArrayList<>();
		
		
		long sogliaDiEsclusione = 5000;
		long statoProcessoEscluso=5;
	    Join<Contratto,Importo> joinImporto = root.join("importos");
	    Join<Contratto,StatoProcesso> joinStatoProcesso = root.join("statoProcesso");
	    
		allFilter.add(criteriaBuilder.greaterThan(joinImporto.get("valoreImp"), sogliaDiEsclusione));
	    allFilter.add(criteriaBuilder.notEqual(joinStatoProcesso.get("idStProcesso"), statoProcessoEscluso));
		
		
		
		// Stato
	    //if (filter.getStatoId() != null) 
	      if (filter.getIdStContratto() != null && !filter.getIdStContratto().equals("0")) {
	    	  Join<Contratto,StatoContratto> joinStatoContratto = root.join("statoContratto");
	          allFilter.add(criteriaBuilder.equal(joinStatoContratto.get("idStContratto"), filter.getIdStContratto()));
	      }
	   // CIG
	    if (filter.getCig() != null && !filter.getCig().isEmpty()) {
	        allFilter.add( criteriaBuilder.equal(criteriaBuilder.upper(root.get("cig")), ((filter.getCig()).toUpperCase())));
	    }
      
	    //descrizione 
	    if (filter.getDescrizione() != null && !filter.getDescrizione().isEmpty()) {
	        allFilter.add( criteriaBuilder.like(criteriaBuilder.upper(root.get("descrizioneCrtt")), (("%"+filter.getDescrizione()+"%").toUpperCase())));
	    }
	    
	  
	    
	  //Data Stipula
      
      if(filter.getDataStipula() != null) {
    	  Join<Contratto,Cronologia> joinCronologia = root.join("cronologias");
    	  Join<Cronologia,TipoCronologia> joinTipoCronologia = joinCronologia.join("tipoCronologia");
    	  allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinCronologia.get("dtInizioEvento"), filter.getDataStipula()), criteriaBuilder.equal(joinTipoCronologia.get("idTpCronologia"), 2),
					 criteriaBuilder.isNull(joinCronologia.get("dataFineVal"))));
      }
	    
	    //Data Decorrenza
	    if(filter.getDataDecorrenza() != null) {
	    	Join<Contratto,Cronologia> joinCronologia = root.join("cronologias");
	  	  Join<Cronologia,TipoCronologia> joinTipoCronologia = joinCronologia.join("tipoCronologia");
	    	allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinCronologia.get("dtInizioEvento"), filter.getDataDecorrenza()), criteriaBuilder.equal(joinTipoCronologia.get("idTpCronologia"), 3),
					 criteriaBuilder.isNull(joinCronologia.get("dataFineVal"))));
	
	    } 
	    
	    //Data Prima Scadenza
      
      if(filter.getDataPrimaScadenza() != null) {
    	  Join<Contratto,Cronologia> joinCronologia = root.join("cronologias");
    	  Join<Cronologia,TipoCronologia> joinTipoCronologia = joinCronologia.join("tipoCronologia");
      	allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinCronologia.get("dtInizioEvento"), filter.getDataPrimaScadenza()), 
      					  					  criteriaBuilder.equal(joinTipoCronologia.get("idTpCronologia"), 4),
      										 criteriaBuilder.isNull(joinCronologia.get("dataFineVal"))));

      }
      
      //Data Ultima Scadenza
      
      if(filter.getDataUltimaScadenza() != null) {
    	  
    	  Join<Contratto,Cronologia> joinCronologia = root.join("cronologias");
    	  Join<Cronologia,TipoCronologia> joinTipoCronologia = joinCronologia.join("tipoCronologia");
      	allFilter.add(criteriaBuilder.and(criteriaBuilder.between(joinCronologia.get("dtInizioEvento"), DateUtil.set00_00(filter.getDataUltimaScadenza()), DateUtil.set23_59(filter.getDataUltimaScadenza())), 
      										  criteriaBuilder.equal(joinTipoCronologia.get("idTpCronologia"), 5),
      										 criteriaBuilder.isNull(joinCronologia.get("dataFineVal"))));

      }
	    
	  
	  
	  
    //Codice Procedura Origine 
    
	   if(filter.getCodiceProceduraOrigine() != null && !filter.getCodiceProceduraOrigine().isEmpty()) {
		   Join<Contratto,Procedura> joinProceduraOrig = root.join("proceduraOrg");
		   allFilter.add(criteriaBuilder.equal(criteriaBuilder.upper(joinProceduraOrig.get("codProcedura")), filter.getCodiceProceduraOrigine().toUpperCase()));
	   }
	    
	   //Codice Procedura Rinnovo 
	    
	   if(filter.getCodiceProceduraRinnovo() != null && !filter.getCodiceProceduraRinnovo().isEmpty()) {
		   Join<Contratto,Procedura> joinProceduraRin = root.join("proceduraRin");
		   allFilter.add(criteriaBuilder.equal(criteriaBuilder.upper(joinProceduraRin.get("codProcedura")), filter.getCodiceProceduraRinnovo().toUpperCase()));
	   }
	    
		  
		  
		// Ragione Sociale
		if (filter.getRagioneSocialeFornitore() != null && !filter.getRagioneSocialeFornitore().isEmpty()) {
			Join<Contratto,FornitoreContratto> joinFornitoreContratto = root.join("fornitoreContrattos");
			Join<FornitoreContratto,Fornitore> joinFornitore = joinFornitoreContratto.join("fornitore");
			allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinFornitore.get("ragioneSociale"), filter.getRagioneSocialeFornitore()),
					criteriaBuilder.isNull(joinFornitoreContratto.get("dataFineVal"))));
		}
		
		
		
		//RUP Nominativo
      if (filter.getRupNominativo() != null && !filter.getRupNominativo().isEmpty()) {
    	  Join<Contratto,AttoreContratto> joinAttoreContratto = root.join("attoreContrattos");
  		Join<AttoreContratto,Attore> joinAttore = joinAttoreContratto.join("attore");
  		Join<AttoreContratto,RuoloAttore> joinRuoloAttore = joinAttoreContratto.join("ruoloAttoreBean");
          allFilter.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(joinAttore.get("nominativo")), ((filter.getRupNominativo()+"%").toUpperCase())), 
					 criteriaBuilder.equal(joinRuoloAttore.get("idRuoloAttore"), 1),
					 criteriaBuilder.isNull(joinAttoreContratto.get("dataFineVal"))));
      }
      
      
      
      
      
   // Struttura Validante
      if (filter.getStrutturaValidante() != null && !filter.getStrutturaValidante().isEmpty()) {
    	  String strutValidante=filter.getStrutturaValidante().split("\\s+")[0];
    	  Join<Contratto,ContrattoStruttura> joinContrattoStruttura = root.join("contrattoStrutturas");
          Join<ContrattoStruttura,AnagrafeStruttura> joinAnagrafeStruttura = joinContrattoStruttura.join("anagrafeStruttura");
          Join<ContrattoStruttura,RuoloStruttura> joinRuoloStruttura = joinContrattoStruttura.join("ruoloStruttura");
    	  allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinAnagrafeStruttura.get("codiceStruttura"), (strutValidante)), 
      											   criteriaBuilder.equal(joinAnagrafeStruttura.get("flagValidante"), 1),
      											   criteriaBuilder.equal(joinRuoloStruttura.get("idRuoloStr"), 1),
      											 criteriaBuilder.isNull(joinContrattoStruttura.get("dataFineVal"))));
      	
      }
      
      // Struttura Responsabile
      if (filter.getStrutturaResponsabile() != null && !filter.getStrutturaResponsabile().isEmpty()) {
    	  String strutResponsabile=filter.getStrutturaResponsabile().split("\\s+")[0];
    	  Join<Contratto,ContrattoStruttura> joinContrattoStruttura = root.join("contrattoStrutturas");
          Join<ContrattoStruttura,AnagrafeStruttura> joinAnagrafeStruttura = joinContrattoStruttura.join("anagrafeStruttura");
          Join<ContrattoStruttura,RuoloStruttura> joinRuoloStruttura = joinContrattoStruttura.join("ruoloStruttura");
    	  allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinAnagrafeStruttura.get("codiceStruttura"), (strutResponsabile)), 
      											   criteriaBuilder.equal(joinRuoloStruttura.get("idRuoloStr"), 2),
        											 criteriaBuilder.isNull(joinContrattoStruttura.get("dataFineVal"))));
      	
      }
      
      // Struttura Destinataria
      if (filter.getStrutturaDestinataria() != null && !filter.getStrutturaDestinataria().isEmpty()) {
    	  String strutDestinataria=filter.getStrutturaDestinataria().split("\\s+")[0];
    	  Join<Contratto,ContrattoStruttura> joinContrattoStruttura = root.join("contrattoStrutturas");
          Join<ContrattoStruttura,AnagrafeStruttura> joinAnagrafeStruttura = joinContrattoStruttura.join("anagrafeStruttura");
          Join<ContrattoStruttura,RuoloStruttura> joinRuoloStruttura = joinContrattoStruttura.join("ruoloStruttura");
      	allFilter.add(criteriaBuilder.and(criteriaBuilder.equal(joinAnagrafeStruttura.get("codiceStruttura"), (strutDestinataria)), 
      											   criteriaBuilder.equal(joinRuoloStruttura.get("idRuoloStr"), 3),
        											 criteriaBuilder.isNull(joinContrattoStruttura.get("dataFineVal"))));
      	
      }
      	
   // CIG PADRE
    if (filter.getCigPadre() != null && !filter.getCigPadre().isEmpty()) {
        allFilter.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("cigPadre")), filter.getCigPadre().toUpperCase()));
    }
    
    // Comparto
    if (filter.getComparto() != null && !filter.getComparto().isEmpty()) {
        allFilter.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("comparto")), ((filter.getComparto()).toUpperCase())));
    }
		
    

	
		query.select(root).where(allFilter.toArray(new Predicate[] {}));
		

		// passa la query composta all'em che ritorna una lista di Fornitori
		return entityManager.createQuery(query).getResultList();

		
	}
	
	
	private Predicate[] PredicateFromListToArray(List<Predicate> list) {
		Predicate[] array = new Predicate[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	
	
	
//public List<VRicercacontratto> findContratti(ContrattoFilterDTO contrattoFilterDTO) {
//    	
//    	//recupero il criteriaBuilder dell'em
//    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//    	CriteriaQuery<VRicercacontratto> query = criteriaBuilder.createQuery(VRicercacontratto.class);
//    	
//    	//settiamo la root ovvero la tabella sulla quale dobbiamo effettuare la query
//    	Root<VRicercacontratto> root = query.from(VRicercacontratto.class);
//    		Predicate predicateForStatoId = null;
//            Predicate predicateForCigId = null;
//            Predicate predicateForRagSoc = null;
//            Predicate predicateForStr = null;
//            Predicate predicateForPrScad = null;
//            Predicate predicateForUlScad = null;
//            Predicate predicateCigPadre = null;
//            Predicate predicateDataEvento = null;
//            Predicate predicateCodiceProcedura = null;
//            Predicate predicateRupNominativo = null;
//            Predicate predicateComparto = null;
//            
//            // creiamo una lista di predicati ovvero di where condition che vengono aggiunte solo se il parametro passato in input non è null
//            List<Predicate> allFilter = new ArrayList<>();
//            List<Predicate> allFilterDate = new ArrayList<>();
//            List<Predicate> allFilterCodici = new ArrayList<>();
//            List<Predicate> allFilterStrutture = new ArrayList<>();
//            
//            // Stato
//            if (contrattoFilterDTO.getStatoId() != null) {
//            	predicateForStatoId = criteriaBuilder.equal(root.get("idStContratto"), contrattoFilterDTO.getStatoId());
//                allFilter.add(predicateForStatoId);
//            }
//            
//            // CIG
//            if (contrattoFilterDTO.getCig() != null && !contrattoFilterDTO.getCig().isEmpty()) {
//            	predicateForCigId = criteriaBuilder.equal(criteriaBuilder.upper(root.get("cig")), ((contrattoFilterDTO.getCig()).toUpperCase()));
//                allFilter.add(predicateForCigId);
//            }
//            
//            //Data Stipula
//            
//            if(contrattoFilterDTO.getDataStipula() != null) {
//            	allFilterDate.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dataInizioEvento"), contrattoFilterDTO.getDataStipula()), criteriaBuilder.equal(root.get("idTipCronol"), 2)));
//            }
//            
//            //Data Decorrenza
//            
//            if(contrattoFilterDTO.getDataDecorrenza() != null) {
//            	allFilterDate.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dataInizioEvento"), contrattoFilterDTO.getDataDecorrenza()), criteriaBuilder.equal(root.get("idTipCronol"), 3)));
//
//            }
//            
//            //Data Prima Scadenza
//            
//            if(contrattoFilterDTO.getDataPrimaScadenza() != null) {
//            	allFilterDate.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dataInizioEvento"), contrattoFilterDTO.getDataPrimaScadenza()), 
//            					  					  criteriaBuilder.equal(root.get("idTipCronol"), 4)));
//
//            }
//            
//            //Data Ultima Scadenza
//            
//            if(contrattoFilterDTO.getDataUltimaScadenza() != null) {
//            	allFilterDate.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dataInizioEvento"), contrattoFilterDTO.getDataUltimaScadenza()), 
//            										  criteriaBuilder.equal(root.get("idTipCronol"), 5)));
//
//            }
//            
//            //Data Evento
//            if(allFilterDate.size() > 0) {
//            	predicateDataEvento = criteriaBuilder.or(PredicateFromListToArray(allFilterDate));
//            	allFilter.add(predicateDataEvento);
//            }
//            
//            
//           //Codice Procedura Origine
//            
//           if(contrattoFilterDTO.getCodiceProceduraOrigine() != null && !contrattoFilterDTO.getCodiceProceduraOrigine().isEmpty()) {
//        	   allFilterCodici.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("codProcedura"), contrattoFilterDTO.getCodiceProceduraOrigine()),
//        			   								   criteriaBuilder.equal(root.get("idProcedura"), root.get("idProcOrg"))));
//           }
//            
//           //Codice Procedura Rinnovo
//            
//           if(contrattoFilterDTO.getCodiceProceduraRinnovo() != null && !contrattoFilterDTO.getCodiceProceduraRinnovo().isEmpty()) {
//        	   allFilterCodici.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("codProcedura"), contrattoFilterDTO.getCodiceProceduraRinnovo()),
//        			   								   criteriaBuilder.equal(root.get("idProcedura"), root.get("idProcRin"))));
//           }
//            
//           //Codici procedure 
//            
//           if(allFilterCodici.size() > 0) {
//        	   predicateCodiceProcedura = criteriaBuilder.or(PredicateFromListToArray(allFilterCodici));
//            	allFilter.add(predicateCodiceProcedura);
//            }
//            
//            // Ragione Sociale
//            if (contrattoFilterDTO.getRagioneSocialeFornitore() != null && !contrattoFilterDTO.getRagioneSocialeFornitore().isEmpty()) {
//            	predicateForRagSoc = criteriaBuilder.like(criteriaBuilder.upper(root.get("ragioneSociale")), ((contrattoFilterDTO.getRagioneSocialeFornitore()+"%").toUpperCase()));
//                allFilter.add(predicateForRagSoc);
//            }
//            
//            //RUP Nominativo
//            if (contrattoFilterDTO.getRupNominativo() != null && !contrattoFilterDTO.getRupNominativo().isEmpty()) {
//            	predicateRupNominativo = criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("nominativo")), ((contrattoFilterDTO.getRupNominativo()+"%").toUpperCase())), 
//            												 criteriaBuilder.equal(root.get("ruoloAttore"), 1)) ;
//                allFilter.add(predicateRupNominativo);
//            }
//            
//            // Struttura Validante
//            if (contrattoFilterDTO.getStrutturaValidante() != null && !contrattoFilterDTO.getStrutturaValidante().isEmpty()) {
//            	allFilterStrutture.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("struttura")), ((contrattoFilterDTO.getStrutturaValidante()).toUpperCase())), 
//            											   criteriaBuilder.equal(root.get("flagValidante"), 1),
//            											   criteriaBuilder.equal(root.get("idRuoloStr"), 1)));
//            	
//            }
//            
//            // Struttura Responsabile
//            if (contrattoFilterDTO.getStrutturaResponsabile() != null && !contrattoFilterDTO.getStrutturaResponsabile().isEmpty()) {
//            	allFilterStrutture.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("struttura")), ((contrattoFilterDTO.getStrutturaResponsabile()).toUpperCase())), 
//            											   criteriaBuilder.equal(root.get("idRuoloStr"), 2)));
//            	
//            }
//            
//            // Struttura Destinataria
//            if (contrattoFilterDTO.getStrutturaDestinataria() != null && !contrattoFilterDTO.getStrutturaDestinataria().isEmpty()) {
//            	allFilterStrutture.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.upper(root.get("struttura")), ((contrattoFilterDTO.getStrutturaDestinataria()).toUpperCase())), 
//            											   criteriaBuilder.equal(root.get("idRuoloStr"), 3)));
//            	
//            }
//            
//            // Strutture
//            if(allFilterStrutture.size() > 0) {
//	        	   predicateForStr = criteriaBuilder.or(PredicateFromListToArray(allFilterStrutture));
//	            	allFilter.add(predicateForStr);
//	            }
//            
//            // CIG PADRE
//            if (contrattoFilterDTO.getCigPadre() != null && !contrattoFilterDTO.getCigPadre().isEmpty()) {
//            	predicateCigPadre = criteriaBuilder.equal(root.get("cigPadre"), contrattoFilterDTO.getCigPadre());
//                allFilter.add(predicateCigPadre);
//            }
//            
//            // Comparto
//            if (contrattoFilterDTO.getComparto() != null && !contrattoFilterDTO.getComparto().isEmpty()) {
//            	predicateComparto = criteriaBuilder.equal(criteriaBuilder.upper(root.get("comparto")), ((contrattoFilterDTO.getComparto()).toUpperCase()));
//                allFilter.add(predicateComparto);
//            }
//            
//            //componiamo la query: select * from (root=v_ricercaContratto) where (filtri inseriti in lista)
//            query.distinct(true).select(root).where(allFilter.toArray(new Predicate[] {}));
//
//            
//            //passa la query composta all'em
//			  
//			  return entityManager.createQuery(query).getResultList();
//       
//	}
//	
//	private Predicate[] PredicateFromListToArray(List<Predicate> list) {
//		Predicate[] array = new Predicate[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			array[i] = list.get(i);
//		}
//		
//		return array;
//	}
	
	
}
