package bdi.azd.sistina.siaweb.service.impl;

import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_ATTORE_CONTRATTO_DEC_DL_PRESENTE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_ATTORE_CONTRATTO_ID_ATTORE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_COLONNA_SEP;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_CATEGORIA_MERCEOLOGICA;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_CIG;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_CIG_PADRE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_DESCRIZIONE_CRTT;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_ID_TP_CONTRATTO;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_MOTIVO_COLL;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_CONTRATTO_NOTE_STATO;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_FORNITORE_CODICE_FISCALE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_RUOLO_FORNITORE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_TIPO_FORNITORE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_FORNITORE_PARTITA_IVA;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_FORNITORE_RAGIONE_SOCIALE;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_RIFERIMENTO_LINK;
import static bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.CONDIZIONE_RICERCA_VALORE_SEP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.CampoNonValidoDTO;
import bdi.azd.sistina.siaweb.dto.CheckValidazioneIDTO;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.DatiEssenzialiI;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.RuoloStruttura;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.exception.ResourceNotFoundException;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.DatiEssenzialiIRepo;
import bdi.azd.sistina.siaweb.repository.StatoProcessoRepo;
import bdi.azd.sistina.siaweb.repository.StoricoEssenzValRepo;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils;
import bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.FlagEssenziale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatiEssenzialiIServiceImpl implements DatiEssenzialiIService {

	@Autowired
	StatoProcessoRepo statoProcessoRepo;
	
	@Autowired
	ContrattoRepo contrattoRepo;
	
	@Autowired
	DatiEssenzialiIRepo datiEssenzialiIRepo;
	
	@Autowired
	StoricoEssenzValRepo storicoEssenzValRepo;
	
	public DatiEssenzialiIServiceImpl(DatiEssenzialiIRepo datiEssenzialiIRepo, StoricoEssenzValRepo storicoEssenzValRepo) {
		this.datiEssenzialiIRepo = datiEssenzialiIRepo;
		this.storicoEssenzValRepo = storicoEssenzValRepo;
	}

    @Override
    public CheckValidazioneIDTO checkDatiEssenzialiI(long idContratto) {
    	Contratto contratto = contrattoRepo.findByIdContratto(idContratto);
    	if(contratto != null)
    		return this.checkDatiEssenzialiI(contratto);
    	throw new ResourceNotFoundException("Contratto non trovato con ID [" + idContratto + "]");
    }
    
    /**
     * Controllo di Validazione di Primo Livello
     * 
     * NOTA: E' possibile fare una sola if con () e/o && || con un solo Body di Exception ma penso sia più leggibile così
     */
    public CheckValidazioneIDTO checkDatiEssenzialiI(Contratto contratto) {
    	if(contratto == null) {
    		throw new ResourceNotFoundException("Contratto non valido");
    	}
    	
    	CheckValidazioneIDTO checkValidazioneIDTO = new CheckValidazioneIDTO();
    	checkValidazioneIDTO.setValido(false);
    	checkValidazioneIDTO.setStoricoEssenzValido(true);

    	long count = storicoEssenzValRepo.countByContrattoIdContratto(contratto.getIdContratto());
    	if(count > 0) {
    		checkValidazioneIDTO.setStoricoEssenzValido(false);
    		return checkValidazioneIDTO;
    	}

    	List<CampoNonValidoDTO> campoNonValidoDTOs = new ArrayList<>();
    	checkValidazioneIDTO.setCampiNonValidi(campoNonValidoDTOs);
    	List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findAll();
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		String dato              = datiEssenziali_I.getDato().trim();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		String tabellaDato       = datiEssenziali_I.getTabellaDato().trim();
    		String areaDato          = datiEssenziali_I.getAreaDato().trim();;
    		FlagEssenziale flagEssenziale = FlagEssenziale.valueOf(datiEssenziali_I.getFlagEssenziale().trim());
    		if(flagEssenziale == FlagEssenziale.N) {
    			continue;
    		}
    		if(DatiEssenzialiIUtils.TABELLA_DATO_ATTORE_CONTRATTO.equals(tabellaDato)) {
    			List<AttoreContratto> attoreContrattos = contratto.getAttoreContrattos();
    			if(attoreContrattos != null) {
	    			for(AttoreContratto attoreContratto : attoreContrattos) {
	        			if(CONDIZIONE_RICERCA_ATTORE_CONTRATTO_DEC_DL_PRESENTE.equals(condizioneRicerca) && (attoreContratto.getDecDlPresente() == null || attoreContratto.getDecDlPresente().isBlank())) {
	        				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
	        			}else if(CONDIZIONE_RICERCA_ATTORE_CONTRATTO_ID_ATTORE.equals(condizioneRicerca) && attoreContratto.getAttore() == null) {
	        				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
	        			}else if(condizioneRicerca.startsWith(CONDIZIONE_RICERCA_ATTORE_CONTRATTO_ID_ATTORE) && condizioneRicerca.contains(CONDIZIONE_RICERCA_COLONNA_SEP)) {
	        				if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
		        				Long idRuoloAttore = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
		        				RuoloAttore ruoloAttore = attoreContratto.getRuoloAttoreBean();
		        				if(ruoloAttore != null && ruoloAttore.getIdRuoloAttore() == idRuoloAttore && attoreContratto.getAttore() == null) {
		        					campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		        				}
	        				}else {
	        					//throw new ValidazioneContrattoException(this.getConditionException(tabellaDato, condizioneRicerca));
	        				}
	        			}else {
	        				//throw new ValidazioneContrattoException(this.getNotImplementedException(tabellaDato, condizioneRicerca));
	        			}
	    			}
    			}else {
    				//throw new ValidazioneContrattoException(this.getMandatoryException(dato));
    			}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_CONTRATTO.equals(tabellaDato)) {
    			if(CONDIZIONE_RICERCA_CONTRATTO_DESCRIZIONE_CRTT.equals(condizioneRicerca) && (contratto.getDescrizioneCrtt() == null || contratto.getDescrizioneCrtt().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_ID_TP_CONTRATTO.equals(condizioneRicerca) && contratto.getTipoContratto() == null) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_CIG_PADRE.equals(condizioneRicerca) && (contratto.getCigPadre() == null || contratto.getCigPadre().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_CIG.equals(condizioneRicerca) && (contratto.getCig() == null || contratto.getCig().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_CATEGORIA_MERCEOLOGICA.equals(condizioneRicerca) && (contratto.getCatMerceologica() == null || contratto.getCatMerceologica().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_MOTIVO_COLL.equals(condizioneRicerca) && (contratto.getMotivazioneCollegamento() == null || contratto.getMotivazioneCollegamento().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else if(CONDIZIONE_RICERCA_CONTRATTO_NOTE_STATO.equals(condizioneRicerca) && (contratto.getNoteStato() == null || contratto.getNoteStato().isBlank())) {
    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
    			}else {
    				//throw new ValidazioneContrattoException(this.getNotImplementedException(tabellaDato, condizioneRicerca));
    			}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_CONTRATTO_STRUTTURA.equals(tabellaDato)) {
				List<ContrattoStruttura> contrattoStrutturas = contratto.getContrattoStrutturas();
				for(ContrattoStruttura contrattoStruttura : contrattoStrutturas) {
					RuoloStruttura ruoloStruttura = contrattoStruttura.getRuoloStruttura();
					if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
	    				Long idRuoloStruttura = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
        				if(ruoloStruttura != null && ruoloStruttura.getIdRuoloStr() == idRuoloStruttura && contrattoStruttura.getAnagrafeStruttura() == null) {
        					campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
        				}
					}else {
						//throw new ValidazioneContrattoException(this.getConditionException(tabellaDato, condizioneRicerca));
					}
				}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_CRONOLOGIA.equals(tabellaDato)) {
    			List<Cronologia> cronologias = contratto.getCronologias();
    			for(Cronologia cronologia : cronologias) {
    				TipoCronologia tipoCronologia = cronologia.getTipoCronologia();
    				if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
	    				Long idTipoCronologia = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
	    				if(tipoCronologia != null && tipoCronologia.getIdTpCronologia() == idTipoCronologia && cronologia.getDtInizioEvento() == null) {
        					campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
        				}
					}else {
						//throw new ValidazioneContrattoException(this.getConditionException(tabellaDato, condizioneRicerca));
					}
    			}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE.equals(tabellaDato) || DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE_CONTRATTO.equals(tabellaDato)) {
    			List<FornitoreContratto> fornitoreContrattos = contratto.getFornitoreContrattos();
    			if(fornitoreContrattos != null) {
	    			for(FornitoreContratto fornitoreContratto : fornitoreContrattos) {
	    				Fornitore fornitore = fornitoreContratto.getFornitore();
		    			if(CONDIZIONE_RICERCA_FORNITORE_PARTITA_IVA.equals(condizioneRicerca) && (fornitore.getPartitaIva() == null || fornitore.getPartitaIva().isBlank())) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else if(CONDIZIONE_RICERCA_FORNITORE_CODICE_FISCALE.equals(condizioneRicerca) && (fornitore.getCodiceFiscale() == null || fornitore.getCodiceFiscale().isBlank())) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else if(CONDIZIONE_RICERCA_FORNITORE_RAGIONE_SOCIALE.equals(condizioneRicerca) && (fornitore.getRagioneSociale() == null || fornitore.getRagioneSociale().isBlank())) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else if(CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_TIPO_FORNITORE.equals(condizioneRicerca) && fornitoreContratto.getTipologiaFornitore() == null) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else if(CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_RUOLO_FORNITORE.equals(condizioneRicerca) && fornitoreContratto.getRuoloFornitore() == null) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else {
		    				//throw new ValidazioneContrattoException(this.getNotImplementedException(tabellaDato, condizioneRicerca));
		    			}
	    			}
    			}else {
    				//throw new ValidazioneContrattoException(this.getMandatoryException(dato));
    			}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_IMPORTO.equals(tabellaDato)) {
    			List<Importo> importos = contratto.getImportos();
    			if(importos != null) {
    				for(Importo importo : importos) {
    					TipoImporto tipoImporto = importo.getTipoImporto();
    					if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
    	    				Long idTipoImporto = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
        					if(tipoImporto != null && tipoImporto.getIdTipImporto() == idTipoImporto && (importo.getValoreImp() == null || importo.getValoreImp().equals(BigDecimal.ZERO))) {
        						campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
        					}
    					}else {
    						//throw new ValidazioneContrattoException(this.getConditionException(tabellaDato, condizioneRicerca));
    					}
    				}
    			}else {
    				//throw new ValidazioneContrattoException(this.getMandatoryException(dato));
    			}
    		}else if(DatiEssenzialiIUtils.TABELLA_DATO_RIFERIMENTO.equals(tabellaDato)) {
    			List<Riferimento> riferimentos = contratto.getRiferimentos();
    			if(riferimentos != null) {
	    			for(Riferimento riferimento : riferimentos) {
		    			if(CONDIZIONE_RICERCA_RIFERIMENTO_LINK.equals(condizioneRicerca) && (riferimento.getLink() == null || riferimento.getLink().isBlank())) {
		    				campoNonValidoDTOs.add(new CampoNonValidoDTO(dato, areaDato));
		    			}else {
		    				//throw new ValidazioneContrattoException(this.getNotImplementedException(tabellaDato, condizioneRicerca));
		    			}
	    			}
    			}else {
    				//throw new ValidazioneContrattoException(this.getMandatoryException(dato));
    			}
    		}else {
    			//throw new ValidazioneContrattoException("Tabella Dato e Condizione Ricerca non implementata: " + tabellaDato + "." + condizioneRicerca);
    		}
    	}
    	
    	// Imposta lo stato di Validazione su OK (Valido)
    	if(checkValidazioneIDTO.getCampiNonValidi() != null && checkValidazioneIDTO.getCampiNonValidi().isEmpty()) {
    		checkValidazioneIDTO.setValido(true);
    		checkValidazioneIDTO.setCampiNonValidi(null);
    	}
    	
    	return checkValidazioneIDTO;
    }

    private String getConditionException(String tabellaDato, String condizioneRicerca) {
    	return "Condizione Ricerca non implementata: " + tabellaDato + "." + condizioneRicerca;
    }
    
    private String getNotImplementedException(String tabellaDato, String condizioneRicerca) {
    	return "Condizione Ricerca non implementata: " + tabellaDato + "." + condizioneRicerca;
    }
    
    private String getMandatoryException(String dato) {
    	return "Il campo '" + dato + "' è obligatorio.";
    }
    
    /**
     * Validazione Contratto di Primo Livello
     */
    @Override
    public CheckValidazioneIDTO validaContrattoI(long idContratto) {
    	CheckValidazioneIDTO isValid = this.checkDatiEssenzialiI(idContratto);
    	if(isValid.isValido()) {
    		Contratto contratto = contrattoRepo.findByIdContratto(idContratto);
        	if(contratto == null)
        		throw new ResourceNotFoundException("Contratto non valido");
        	// Modifica lo stato del processo di validazione del contratto.
        	StatoProcesso statoProcesso = statoProcessoRepo.findByIdStProcesso(3); // Da Validare 2^Livello
    		contratto.setStatoProcesso(statoProcesso);
    		contrattoRepo.save(contratto);
    	}
    	return isValid;
    }
    
    /**
     * Validazione Contratto Fase 1
     */
    @Override
    public void validaContratto(long idContratto) {
        Contratto contratto = contrattoRepo.findByIdContratto(idContratto);
        if(contratto == null) {
            throw new ResourceNotFoundException("Contratto non trovato con ID [" + idContratto + "]");
        }
    	// Modifica lo stato del processo di validazione del contratto.
    	StatoProcesso statoProcesso = statoProcessoRepo.findByIdStProcesso(4); // Valido
		contratto.setStatoProcesso(statoProcesso);
		contrattoRepo.save(contratto);
    }

}
