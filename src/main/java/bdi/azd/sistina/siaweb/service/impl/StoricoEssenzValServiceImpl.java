package bdi.azd.sistina.siaweb.service.impl;

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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.StoricoEssenzValDTO;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.DatiEssenzialiI;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.StoricoEssenzVal;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.AttoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.CronologiaRepo;
import bdi.azd.sistina.siaweb.repository.DatiEssenzialiIRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreRepo;
import bdi.azd.sistina.siaweb.repository.ImportoRepo;
import bdi.azd.sistina.siaweb.repository.RiferimentoRepo;
import bdi.azd.sistina.siaweb.repository.StoricoEssenzValRepo;
import bdi.azd.sistina.siaweb.service.StoricoEssenzValService;
import bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils;
import bdi.azd.sistina.siaweb.util.UserUtil;
import bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils.DatoConfronto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoricoEssenzValServiceImpl implements StoricoEssenzValService {
	
	@Autowired
	DatiEssenzialiIRepo datiEssenzialiIRepo;
	
	@Autowired
	StoricoEssenzValRepo storicoEssenzValRepo;
	
	@Autowired
    ContrattoRepo contrattoRepo;
	
	@Autowired
    FornitoreRepo fornitoreRepo;
	
	@Autowired
    FornitoreContrattoRepo fornitoreContrattoRepo;
	
	@Autowired
    ContrattoStrutturaRepo contrattoStrutturaRepo;
	
	@Autowired
    AttoreContrattoRepo attoreContrattoRepo;
	
	@Autowired
    RiferimentoRepo riferimentoRepo;
	
	@Autowired
    CronologiaRepo cronologiaRepo;
	
	@Autowired
    ImportoRepo importoRepo;
	
	@Autowired
	DtoMapper dtoMapper;
	
	/**
	 * Controlla se i field old e new sono differenti.
	 * 
	 * Casi:
	 * (null, null) -> false
	 * (null, "") -> true
	 * ("", null) -> true
	 * ("", "") -> false
	 * ("A", "B") -> true
	 * ("B", "A") -> true
	 * ("A", "A") -> false
	 * 
	 * @param oldValue
	 * @param newValue
	 * @return true se sono diversi, altrimenti false
	 */
    public boolean checkField(String oldValue, String newValue) {
    	return !((oldValue == null && newValue == null) || (oldValue != null && newValue != null && oldValue.trim().equals(newValue.trim())));
    }
    
    @Override
    public List<StoricoEssenzValDTO> getStoricoEssenzVal(Long idContratto) {
    	List<StoricoEssenzVal>storicoList=storicoEssenzValRepo.findByContrattoIdContratto(idContratto);
    	return dtoMapper.storicoEssenzValToStoricoEssenzValDTO(storicoList);
    }
    
    @Override
    public void deleteStoricoEssenzVal(Long idContratto) {
    	List<StoricoEssenzVal> storicoEssenzVals = dtoMapper.storicoEssenzValDTOStoricoEssenzVal(this.getStoricoEssenzVal(idContratto));
    	storicoEssenzValRepo.deleteAll(storicoEssenzVals);
    }
    
    @Override
    public void newStoricoEssenzVal(Long idContratto, Long idDatiEssenzialiI, String oldValue, String newValue) {
    	Contratto contratto = new Contratto();
    	contratto.setIdContratto(idContratto);
    	DatiEssenzialiI datiEssenzialiI = new DatiEssenzialiI();
    	datiEssenzialiI.setIdEssenzialiI(idDatiEssenzialiI);
    	StoricoEssenzVal storicoEssenzVal = new StoricoEssenzVal();
    	storicoEssenzVal.setContratto(contratto);
    	storicoEssenzVal.setDataAggior((new Timestamp(System.currentTimeMillis())));
    	storicoEssenzVal.setDatiEssenzialiI(datiEssenzialiI);
    	storicoEssenzVal.setMotivazione(null);
    	storicoEssenzVal.setStatoAccettaz("N");
    	storicoEssenzVal.setUseridAggior(UserUtil.getLoggedUserId());
    	storicoEssenzVal.setValoreNew(newValue);
    	storicoEssenzVal.setValoreOld(oldValue);
    	storicoEssenzValRepo.save(storicoEssenzVal);
    }

    @Override
    public void updateStoricoEssenzVal(StoricoEssenzValDTO storicoEssenzValDTO) {
    	Optional<StoricoEssenzVal> storicoEssenzValOPT = storicoEssenzValRepo.findById(storicoEssenzValDTO.getIdContrattoEssenz());
    	StoricoEssenzVal storicoEssenzVal = null;
    	if(storicoEssenzValOPT.isPresent()) {
    		storicoEssenzVal = storicoEssenzValOPT.get();
    	}else {
    		throw new DataIntegrityException("StoricoEssenzVal con ID [" + storicoEssenzValDTO.getIdContrattoEssenz() + "] non trovato.");
    	}
    	storicoEssenzVal.setMotivazione(storicoEssenzValDTO.getMotivazione());
    	storicoEssenzVal.setStatoAccettaz(storicoEssenzValDTO.getStatoAccettaz());
    	storicoEssenzVal.setDataAggior(Timestamp.from(Instant.now()));
    	storicoEssenzVal.setUseridAggior(UserUtil.getLoggedUserId());
    	storicoEssenzValRepo.save(storicoEssenzVal);
    }
    
    @Override
    public void checkStoricoEssenzVal(ContrattoDTO contrattoDTO) {
    	long idContratto = contrattoDTO.getIdContratto();
    	this.checkContrattoStoricoEssenzVal(contrattoDTO);
    	//this.checkAttoreContrattoStoricoEssenzVal(null, null); // Deve essere eseguito all'interno di Modifica Attore Contratto
    	//this.checkFornitoreStoricoEssenzVal(null, null); // Deve essere inserito ...
    	this.checkFornitoreContrattoStoricoEssenzVal(idContratto, contrattoDTO.getFornitoreContrattos());
    	this.checkContrattoStrutturaStoricoEssenzVal(idContratto, contrattoDTO.getContrattoStrutturas());
    	this.checkImportoStoricoEssenzVal(idContratto, contrattoDTO.getImportos());
    	this.checkCronologiaStoricoEssenzVal(idContratto, contrattoDTO.getCronologias());
    	this.checkRiferimentoStoricoEssenzVal(idContratto, contrattoDTO.getRiferimentos());
    }

    @Override
    public void checkFornitoreStoricoEssenzVal(Long idContratto, FornitoreDTO fornitoreDTO) {
    	Fornitore fornitore = fornitoreRepo.findByIdFornitore(fornitoreDTO.getIdFornitore());
        if(fornitore == null) {
            throw new DataIntegrityException("Fornitore con ID [" + fornitoreDTO.getIdFornitore() + "] non presente nel database");
        }
        List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
			if(CONDIZIONE_RICERCA_FORNITORE_PARTITA_IVA.equals(condizioneRicerca)) {
				boolean update = this.checkField(fornitore.getPartitaIva(), fornitoreDTO.getPartitaIva());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, fornitore.getPartitaIva(), fornitoreDTO.getPartitaIva());
				}
			}else if(CONDIZIONE_RICERCA_FORNITORE_CODICE_FISCALE.equals(condizioneRicerca)) {
				boolean update = this.checkField(fornitore.getCodiceFiscale(), fornitoreDTO.getCodiceFiscale());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, fornitore.getCodiceFiscale(), fornitoreDTO.getCodiceFiscale());
				}
			}else if(CONDIZIONE_RICERCA_FORNITORE_RAGIONE_SOCIALE.equals(condizioneRicerca)) {
				boolean update = this.checkField(fornitore.getRagioneSociale(), fornitoreDTO.getRagioneSociale());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, fornitore.getRagioneSociale(), fornitoreDTO.getRagioneSociale());
				}
			}
    	}
    }

	/**
	 * NOTA: Gli Attori saranno sempre un RUP, obbligatorio, ed eventulmente un secondo Attore (DEC/DL) opzionale.
	 * Controlli in caso di RUP (Ruolo Attore = 1)
	 * - Controllare se è cambiato l'Id
	 * Controlli in caso di DEC/DL (Ruolo Attore = 2 o 3)
	 * - Controllare se è stato Modificato -> se è cambiato l'Id
	 */
    @Override
    public void checkAttoreContrattoStoricoEssenzVal(AttoreContratto oldAT, AttoreContratto newAT) {
    	if(oldAT == null || newAT == null || oldAT.getAttore() == null || newAT.getAttore() == null) { // Confronto non necessario
    		return;
    	}
    	if(oldAT.getAttore().getIdAttore() != newAT.getAttore().getIdAttore()) {
    		String idAttoreContrattoDb = Long.toString(oldAT.getAttore().getIdAttore());
			String idAttoreContrattoIn = Long.toString(newAT.getAttore().getIdAttore());
			if(newAT.getRuoloAttoreBean().getIdRuoloAttore() == 1) { // RUP
				DatiEssenzialiI datiEssenzialisI = datiEssenzialiIRepo.findById(15L).orElse(null);
				if(datiEssenzialisI != null) { // DEVE essere presente nel Database!
					DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenzialisI.getDatoConfronto().trim());
					if(datoConfronto == DatoConfronto.S)
						this.newStoricoEssenzVal(oldAT.getContratto().getIdContratto(), datiEssenzialisI.getIdEssenzialiI(), idAttoreContrattoDb, idAttoreContrattoIn);
				}
			}else { // DEC/DL
				DatiEssenzialiI datiEssenzialisI = datiEssenzialiIRepo.findById(28L).orElse(null);
				if(datiEssenzialisI != null) { // DEVE essere presente nel Database!
					DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenzialisI.getDatoConfronto().trim());
					if(datoConfronto == DatoConfronto.S)
						this.newStoricoEssenzVal(oldAT.getContratto().getIdContratto(), datiEssenzialisI.getIdEssenzialiI(), idAttoreContrattoDb, idAttoreContrattoIn);
				}
			}
    	}
    }
        
    @Override
    public void checkContrattoStoricoEssenzVal(ContrattoDTO contrattoDTO) {
    	Contratto contratto = contrattoRepo.findByCig(contrattoDTO.getCig());
        if(contratto == null) {
            throw new DataIntegrityException("Contratto con CIG [" + contrattoDTO.getCig() + "] non presente nel database");
        }
    	long idContratto = contratto.getIdContratto();
    	List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_CONTRATTO);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
			if(CONDIZIONE_RICERCA_CONTRATTO_DESCRIZIONE_CRTT.equals(condizioneRicerca)) {
				boolean update = this.checkField(contratto.getDescrizioneCrtt(), contrattoDTO.getDescrizioneCrtt());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, contratto.getDescrizioneCrtt(), contrattoDTO.getDescrizioneCrtt());
				}
			}else if(CONDIZIONE_RICERCA_CONTRATTO_ID_TP_CONTRATTO.equals(condizioneRicerca)) {
				String idTipoContrattoDb = (contratto.getTipoContratto() != null)? Long.toString(contratto.getTipoContratto().getIdTpContratto()) : null;
				String idTipoContrattoIn = (contrattoDTO.getTipoContratto() != null)? Long.toString(contrattoDTO.getTipoContratto().getIdTpContratto()) : null;
				boolean update = this.checkField(idTipoContrattoDb, idTipoContrattoIn);
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, idTipoContrattoDb, idTipoContrattoIn);
				}
			}else if(CONDIZIONE_RICERCA_CONTRATTO_CIG_PADRE.equals(condizioneRicerca)) {
				boolean update = this.checkField(contratto.getCigPadre(), contrattoDTO.getCigPadre());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, contratto.getCigPadre(), contrattoDTO.getCigPadre());
				}    				
			}else if(CONDIZIONE_RICERCA_CONTRATTO_CIG.equals(condizioneRicerca)) {
				boolean update = this.checkField(contratto.getCig(), contrattoDTO.getCig());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, contratto.getCig(), contrattoDTO.getCig());
				}    				    				
			}else if(CONDIZIONE_RICERCA_CONTRATTO_CATEGORIA_MERCEOLOGICA.equals(condizioneRicerca)) {
				// Dato NON presente in ModificaContratto
			}else if(CONDIZIONE_RICERCA_CONTRATTO_MOTIVO_COLL.equals(condizioneRicerca)) {
				// Dato NON presente in ModificaContratto
			}else if(CONDIZIONE_RICERCA_CONTRATTO_NOTE_STATO.equals(condizioneRicerca)) {
				boolean update = this.checkField(contratto.getNoteStato(), contrattoDTO.getNoteStato());
				if(update) {
					this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, contratto.getNoteStato(), contrattoDTO.getNoteStato());
				}
			}
    	}
    }

	@Override
	public void checkFornitoreContrattoStoricoEssenzVal(Long idContratto, List<FornitoreContrattoDTO> fornitoreContrattoModificaDTOs) {
		if(fornitoreContrattoModificaDTOs == null) {
			return;
		}
    	List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE_CONTRATTO);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
    		for(FornitoreContrattoDTO fornitoreContrattoModificaDTO : fornitoreContrattoModificaDTOs) {
    			Optional<FornitoreContratto> fornitoreContrattoOPT = fornitoreContrattoRepo.findById(fornitoreContrattoModificaDTO.getIdFornitoreContratto());
    			if(fornitoreContrattoOPT.isPresent()) {
    				FornitoreContratto fornitoreContratto = fornitoreContrattoOPT.get();
					if(CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_TIPO_FORNITORE.equals(condizioneRicerca)) {
						String tipologiaFornitoreDb = Long.toString(fornitoreContratto.getTipologiaFornitore().getIdTpFornitore());
						String tipologiaFornitoreIn = Long.toString(fornitoreContrattoModificaDTO.getTipologiaFornitore().getIdTpFornitore());
						boolean update = this.checkField(tipologiaFornitoreDb, tipologiaFornitoreIn);
						if(update) {
							this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, tipologiaFornitoreDb, tipologiaFornitoreIn);
						}
					}else if(CONDIZIONE_RICERCA_FORNITORE_CONTRATTO_RUOLO_FORNITORE.equals(condizioneRicerca)) {
						String ruoloFornitoreDb = Long.toString(fornitoreContratto.getRuoloFornitore().getIdRlFornitore());
						String ruoloFornitoreIn = Long.toString(fornitoreContrattoModificaDTO.getRuoloFornitore().getIdRlFornitore());
						boolean update = this.checkField(ruoloFornitoreDb, ruoloFornitoreIn);
						if(update) {
							this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, ruoloFornitoreDb, ruoloFornitoreIn);
						}
					}
    			}
    		}
    	}
	}

	@Override
	public void checkContrattoStrutturaStoricoEssenzVal(Long idContratto, List<ContrattoStrutturaDTO> contrattoStrutturaModificaDTOs) {
/* Dato NON presente in ModificaContratto
		if(contrattoStrutturaModificaDTOs == null) {
			return;
		}
		List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_CONTRATTO_STRUTTURA);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
    		if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
				Long idStrutturaDE = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
				for(ContrattoStrutturaModificaDTO contrattoStrutturaDTO : contrattoStrutturaModificaDTOs) {
					ContrattoStruttura contrattoStruttura = contrattoStrutturaRepo.findByIdContrattoStruttura(contrattoStrutturaDTO.getIdContrattoStrutture());
					if(contrattoStruttura != null && contrattoStruttura.getAnagrafeStruttura().getIdStruttura() == idStrutturaDE) {
    					// Dato NON presente in ModificaContratto
					}
				}
    		}
    	}
*/
	}

	@Override
	public void checkCronologiaStoricoEssenzVal(Long idContratto, List<CronologiaDTO> cronologiaDTOs) {
		if(cronologiaDTOs == null) {
			return;
		}
        List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_CRONOLOGIA);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
    		if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
				Long idTipoCronologiaDE = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
				for(CronologiaDTO cronologiaDTO : cronologiaDTOs) {
					if(cronologiaDTO.getTipoCronologia().getIdTpCronologia() == idTipoCronologiaDE) {
						List<Cronologia> cronologias = cronologiaRepo.searchByIdContratto(idContratto, cronologiaDTO.getTipoCronologia().getIdTpCronologia());
						if(cronologias != null && cronologias.size() == 1) {
							Cronologia cronologia = cronologias.get(0);
	    					String dtInizioEventoDb = (cronologia.getDtInizioEvento() != null)? new SimpleDateFormat("dd/MM/yyyy").format(cronologia.getDtInizioEvento()) : null;
							String dtInizioEventoIn = (cronologiaDTO.getDtInizioEvento() != null)? new SimpleDateFormat("dd/MM/yyyy").format(cronologiaDTO.getDtInizioEvento()) : null;
	    	    			boolean update = this.checkField(dtInizioEventoDb, dtInizioEventoIn);
	    					if(update) {
	    						this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, dtInizioEventoDb, dtInizioEventoIn);
	    					}
						}
					}
				}
    		}
    	}
	}

	@Override
	public void checkImportoStoricoEssenzVal(Long idContratto, List<ImportoDTO> importoModificaDTOs) {
		if(importoModificaDTOs == null) {
			return;
		}
        List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_IMPORTO);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
    		for(ImportoDTO importoModificaDTO : importoModificaDTOs) {
    			if(importoModificaDTO.getIdImporto()!=null ) {
    			Importo importo = importoRepo.findById(importoModificaDTO.getIdImporto()).orElse(null);
    			if(importo!=null) {
    			TipoImporto tipoImporto = importo.getTipoImporto();
				if(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP) != -1) {
    				Long idTipoImporto = Long.parseLong(condizioneRicerca.substring(condizioneRicerca.indexOf(CONDIZIONE_RICERCA_VALORE_SEP)+1));
					if(tipoImporto != null && tipoImporto.getIdTipImporto() == idTipoImporto) {
						String valoreImpDb = (importo.getValoreImp() != null)? importo.getValoreImp().toPlainString() : null;
						String valoreImpIn = (importoModificaDTO.getValoreImp() != null)? importoModificaDTO.getValoreImp().toPlainString() : null;
						boolean update = this.checkField(valoreImpDb, valoreImpIn);
						if(update) {
							this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, valoreImpDb, valoreImpIn);
						}
					}
				}
    			}
    		}
    		}
    	}
	}

	@Override
	public void checkRiferimentoStoricoEssenzVal(Long idContratto, List<RiferimentoDTO> riferimentoDTOs) {
		if(riferimentoDTOs == null) {
			return;
		}
        List<DatiEssenzialiI> datiEssenzialisI = datiEssenzialiIRepo.findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_RIFERIMENTO);
    	for(DatiEssenzialiI datiEssenziali_I : datiEssenzialisI) {
    		long idDatiEssenzialiI   = datiEssenziali_I.getIdEssenzialiI();
    		String condizioneRicerca = datiEssenziali_I.getCondizioneRicerca().trim();
    		DatoConfronto datoConfronto = DatoConfronto.valueOf(datiEssenziali_I.getDatoConfronto().trim());
    		if(datoConfronto == DatoConfronto.N) {
    			continue;
    		}
    		for(RiferimentoDTO riferimentoDTO : riferimentoDTOs) {
	    		if(CONDIZIONE_RICERCA_RIFERIMENTO_LINK.equals(condizioneRicerca)) {
	    			Riferimento riferimento = riferimentoRepo.findByIdRiferimento(riferimentoDTO.getIdRiferimento());
	    			if(riferimento != null) {
		    			boolean update = this.checkField(riferimento.getLink(), riferimentoDTO.getLink());
						if(update) {
							this.newStoricoEssenzVal(idContratto, idDatiEssenzialiI, riferimento.getLink(), riferimentoDTO.getLink());
						}
	    			}
				}
    		}
    	}
	}
	
}
