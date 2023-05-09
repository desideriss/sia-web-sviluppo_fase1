package bdi.azd.sistina.siaweb.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bdi.azd.sistina.siaweb.dto.CheckValidazioneIDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.StatoContratto;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.StatoContrattoRepo;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContrattoUtils {
	
	@Autowired
	DatiEssenzialiIService datiEssenzialiIService;
	@Autowired
	private StatoContrattoRepo statoContrattoRepo;
	@Autowired
	private ContrattoRepo contrattoRepository;
	
	
	/**
	 * Imposto la Stato di un Contratto
	 * 
	 * • Aggiudicato -> se presente SOLO la data di Aggiudicazione e nessun altra data in Cronologia.
	 *                 (se la DT_INIZIO_EVENTO <> null and ID_TIP_CRONOL = 1 e basta!!)
	 * • Attivo -> se valorizzate le date di stipula e/o decorrenza
     *             Un contratto è ATTIVO quando dt_inizio_evento <> null e ID_TIP_CONOL in (1,2,3)
     *
	 * @param contratto
	 */
	public void setStatoContrattoOLD(Contratto contratto,Boolean aggiudicato) {
	    List<Cronologia> cronologias = contratto.getCronologias().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
	  //data riattivazione
	    for(Cronologia cronologia: cronologias) {
	    	TipoCronologia tipoCronologia=cronologia.getTipoCronologia();
	    	if(tipoCronologia!=null && cronologia.getDtInizioEvento()!=null && tipoCronologia.getIdTpCronologia()==7) {
	    		Optional<Cronologia> cronUltimaSc=cronologias.stream().filter(c->c.getDataFineVal()==null && c.getTipoCronologia().getIdTpCronologia()==5).findFirst();
	    		if(!cronUltimaSc.isEmpty() && cronologia.getDtInizioEvento().before(cronUltimaSc.get().getDtInizioEvento())) {
	    			Optional<Cronologia> cronDtSosp= cronologias.stream().filter(c->c.getDataFineVal()==null && c.getTipoCronologia().getIdTpCronologia()==6).findFirst();
	    			if(!cronDtSosp.isEmpty() && cronDtSosp.get().getIdCronologia()<cronologia.getIdCronologia()) {
	    				Calendar cal = Calendar.getInstance();
	    				cal.setTime(cronologia.getDtInizioEvento());
	    				cal.add(Calendar.DATE, -1);
	    				cronDtSosp.get().setDataFineVal(new Timestamp(cal.getTimeInMillis()));
	    				cronDtSosp.get().setDtFineEvento(cal.getTime());
	    			} 
	    			if(!cronDtSosp.isEmpty() && cronDtSosp.get().getDataFineVal()!=null) {
	    				StatoContratto statoContratto= new StatoContratto();
	    				statoContratto.setIdStContratto(2);
	    				contratto.setStatoContratto(statoContratto);
	    				return;
	    			}
	    		}
	    	}
	    }
	    //data sospensione
	    for(Cronologia cronologia: cronologias) {
	    	TipoCronologia tipoCronologia=cronologia.getTipoCronologia();
	    	if(tipoCronologia!=null && cronologia.getDtInizioEvento()!=null && tipoCronologia.getIdTpCronologia()==6 && !cronologia.getDataInizioVal().after(Calendar.getInstance().getTime())) {
	    		StatoContratto statoContratto= new StatoContratto();
	    		statoContratto.setIdStContratto(3);
	    		contratto.setStatoContratto(statoContratto);
	    		Optional<Cronologia> cronDtRiatt= cronologias.stream().filter(c->c.getDataFineVal()==null && c.getTipoCronologia().getIdTpCronologia()==7).findFirst();
    			if(!cronDtRiatt.isEmpty()) {
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(cronologia.getDtInizioEvento());
    				cal.add(Calendar.DATE, -1);
    				cronDtRiatt.get().setDataFineVal(new Timestamp(cal.getTimeInMillis()));
    				cronDtRiatt.get().setDtFineEvento(cal.getTime());
    			}
	    		return;
	    	}
	    }
	    
	    // Controllo per lo Stato 1 -> Aggiudicato
	    if(cronologias.size() == 1 && (aggiudicato==null ||!aggiudicato)) {
	    	Cronologia cronologia = cronologias.get(0);
	    	TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
	    	if(cronologia.getDtInizioEvento() != null && tipoCronologia.getIdTpCronologia() == 1) {
	    		StatoContratto statoContratto = new StatoContratto();
	    		statoContratto.setIdStContratto(1); // Aggiudicato
	    		contratto.setStatoContratto(statoContratto);
	    		return;
	    	}
	    }
	    
	    // Controllo per lo Stato 2 -> Attivo
	    for(Cronologia cronologia: cronologias) {
	    	/* TipoCronologia
				1	Data Aggiudicazione
				2	Data Stipula
				3	Decorrenza 
	    	 */
	    	TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
	    	if((aggiudicato!=null&&aggiudicato)||(tipoCronologia != null && cronologia.getDtInizioEvento() != null && (tipoCronologia.getIdTpCronologia() == 1 || tipoCronologia.getIdTpCronologia() == 2 || tipoCronologia.getIdTpCronologia() == 3))) {
	    		StatoContratto statoContratto = new StatoContratto();
	    		statoContratto.setIdStContratto(2); // Attivo
	    		contratto.setStatoContratto(statoContratto);
	    		break;
	    	}
	    }
	    
	}
	
	
	
	
	
	
	
	public void setStatoContratto(Long idContratto) {
		
		Contratto contratto=contrattoRepository.findByIdContratto(idContratto);
		
		
	    List<Cronologia> cronologias = contratto.getCronologias().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
	  
	    
	    // Controllo per lo Stato 2 -> Attivo
	    for(Cronologia cronologia: cronologias) {
	    	/* TipoCronologia
				1	Data Aggiudicazione
				2	Data Stipula
				3	Decorrenza 
	    	 */
	    	TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
	    	if((tipoCronologia != null && cronologia.getDtInizioEvento() != null && (tipoCronologia.getIdTpCronologia() == 1 || tipoCronologia.getIdTpCronologia() == 2 || tipoCronologia.getIdTpCronologia() == 3))) {
	    		contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(2)).orElse(null));//attivo
	    		break;
	    	}
	    }
	    
	    
	    cronologias.sort((p1, p2) -> p1.getDtInizioEvento().compareTo(p2.getDtInizioEvento()));
	    
	    Date dataScadenza=null;
		for (Cronologia cron : cronologias) {
			if(cron.getTipoCronologia().getIdTpCronologia()==5) {//data scadenza
				dataScadenza=cron.getDtInizioEvento();
				break;
			}
		}
		
		Date dataRiattivazione=null;
		for (Cronologia cron : cronologias) {
			if(cron.getTipoCronologia().getIdTpCronologia()==7) {//data riattivazione
				contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(2)).orElse(null)); 
				dataRiattivazione=cron.getDtInizioEvento();
				if(dataRiattivazione!=null && dataScadenza!=null && dataRiattivazione.before(dataScadenza)) {
					contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(2)).orElse(null)); //attivo
					break;
				}
				if(dataRiattivazione!=null && dataScadenza!=null && dataRiattivazione.after(dataScadenza)) {
					contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(3)).orElse(null)); //sospeso
					break;
				}
				
			}
		}
		
		Date dataSospensione=null;
		for (Cronologia cron : cronologias) {
			if(cron.getTipoCronologia().getIdTpCronologia()==6) {
			//se tipo_data=sospensione(6) e valore_evento <= data odierna imposta stato contratto a sospeso
			if(cron.getDtInizioEvento().before(new Date()) ) {
				dataSospensione=cron.getDtInizioEvento();
				if (dataRiattivazione != null && dataScadenza!= null && dataRiattivazione.before(dataScadenza)) {//minore di data ultima scadenza
					contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(2)).orElse(null)); // attivo
				} else {
					contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(3)).orElse(null)); // sospeso
				}
			}
			//se presente data riattivazione chiudo data sospensione
			if(dataRiattivazione!=null && dataSospensione!=null) {
				cron.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataRiattivazione));
				cron.setDataFineVal(DateUtil.getClosedDate());
			}
			
			}
		}
		
	    //se presente datariatti chiudo data sospensione
		if(dataRiattivazione!=null && dataSospensione!=null) {
			
		}
	   
		
	 // Controllo per lo Stato 1 -> Aggiudicato
	    if(cronologias.size() == 1 ) {
	    	Cronologia cronologia = cronologias.get(0);
	    	TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
	    	if(cronologia.getDtInizioEvento() != null && tipoCronologia.getIdTpCronologia() == 1) {
	    		contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(1)).orElse(null)); // Aggiudicato
	    		return;
	    	}
	    }
	    
		
		contrattoRepository.save(contratto);
	}
	

	/**
     * Impostazione dello Stato Processo
     * 
     * •	Da stipulare -> se manca la data di stipula (è presente solo la data di aggiudicazione)
     * •	Da validare  -> mancano ancora da valorizzare alcuni dei campi essenziali per la validazione di I livello
     * •	Da validare 2^livello -> il contratto ha passato la validazione di I livello e risponde ad alcune caratteristiche particolari per le quali il contratto necessita una validazione di 2° livello
     * •	Validato.
     * 
     * Stato Processo:
     * 1	Da Stipulare
     * 2	Da Validare
     * 3	Da Validare 2^Livello
     * 4	Validato
     * 5	Da escludere
     * 
	 * @param contratto
	 */
	public void setStatoProcesso(Contratto contratto) {
        StatoProcesso statoProcesso = new StatoProcesso();
        
        List<Cronologia> cronologias = contratto.getCronologias();
        // Controllo Stato Processo: Da Stipulare
        boolean foundStipula = false;
        for(Cronologia cronologia: cronologias) {
        	/* TipoCronologia
				2	Data Stipula
        	 */
        	TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
        	if(tipoCronologia != null && tipoCronologia.getIdTpCronologia() == 2 && cronologia.getDtInizioEvento() != null) { // Trovata la Data di Stipula, NON è da Stipulare
        		foundStipula = true;
        	}
        }
        
        // Controllo Validazione di Primo Livello
        CheckValidazioneIDTO checkValidazioneIDTO = this.datiEssenzialiIService.checkDatiEssenzialiI(contratto);
        if(checkValidazioneIDTO.isValido()) {
        	statoProcesso.setIdStProcesso(3); // Da Validare 2^Livello
//        	statoProcesso.setIdStProcesso(4); // Valido (Fase 1?)
        }else {
        	statoProcesso.setIdStProcesso(2); // Da Validare
        }
        if(!foundStipula) { // Data di Stipula non trovata, Contratto da Stipulare
        	statoProcesso.setIdStProcesso(1); // Da Stipulare
        }
        contratto.setStatoProcesso(statoProcesso);
	}

}
