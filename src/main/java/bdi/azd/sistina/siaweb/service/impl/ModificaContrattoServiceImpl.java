package bdi.azd.sistina.siaweb.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bdi.azd.sistina.siaweb.constants.ErrorMessage;
import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaIntegrazioneDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoIntegrazioneDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.ImportoIntegrazioneDTO;
import bdi.azd.sistina.siaweb.dto.IntegrazioneFullDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.TipoRiferimentoDTO;
import bdi.azd.sistina.siaweb.entity.Attore;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Integrazione;
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoContratto;
import bdi.azd.sistina.siaweb.entity.TipoRiferimento;
import bdi.azd.sistina.siaweb.entity.TipoSubappalto;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.exception.ResourceNotFoundException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.AnagrafeStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.AttoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.AttoreRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.CronologiaRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreRepo;
import bdi.azd.sistina.siaweb.repository.ImportoRepo;
import bdi.azd.sistina.siaweb.repository.IntegrazioneRepo;
import bdi.azd.sistina.siaweb.repository.ProceduraRepo;
import bdi.azd.sistina.siaweb.repository.RiferimentoRepo;
import bdi.azd.sistina.siaweb.repository.RuoloAttoreRepo;
import bdi.azd.sistina.siaweb.repository.RuoloFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.StatoContrattoRepo;
import bdi.azd.sistina.siaweb.repository.StatoProcessoRepo;
import bdi.azd.sistina.siaweb.repository.TipoContrattoRepo;
import bdi.azd.sistina.siaweb.repository.TipoFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.TipoRiferimentoRepo;
import bdi.azd.sistina.siaweb.repository.TipoSubappaltoRepo;
import bdi.azd.sistina.siaweb.service.ModificaContrattoService;
import bdi.azd.sistina.siaweb.service.StoricoEssenzValService;
import bdi.azd.sistina.siaweb.util.ContrattoUtils;
import bdi.azd.sistina.siaweb.util.DateUtil;
import bdi.azd.sistina.siaweb.util.UserUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModificaContrattoServiceImpl implements ModificaContrattoService {

	@Autowired
	public CronologiaRepo cronologiaRepo;
	@Autowired
	public ImportoRepo importoRepo;
	@Autowired
	public ContrattoStrutturaRepo contrattoStrutturaRepo;
	@Autowired
	public AttoreRepo attoreRepo;
	@Autowired
	public RiferimentoRepo riferimentoRepo;
	@Autowired
	public ContrattoRepo contrattoRepository;
	@Autowired
	public AttoreContrattoRepo attoreContrattoRepo;
	@Autowired
	public DtoMapper dtoMapper;
	@Autowired
	public ProceduraRepo proceduraRepo;
	@Autowired
	public TipoContrattoRepo tipoContrattoRepo;
	@Autowired
	public TipoSubappaltoRepo tipoSubappaltoRepo;
	@Autowired
	public AnagrafeStrutturaRepo anagrafeStruttureRepo;   
	@Autowired
	public StatoProcessoRepo statoProcessoRepo;
	@Autowired
	public FornitoreContrattoRepo fornitoreContrattoRepository;
	@Autowired
	public TipoFornitoreRepo tipoFornitoreRepo;
	@Autowired
	public RuoloFornitoreRepo ruoloFornitoreRepo;
	@Autowired
	public IntegrazioneRepo integrazioneRepo;
	@Autowired
	public FornitoreRepo fornitoreRepo;
	@Autowired
	public RuoloAttoreRepo ruoloAttoreRepo;
	@Autowired
	public TipoRiferimentoRepo tipoRiferimentoRepo;
	@Autowired
	StoricoEssenzValService storicoEssenzValUtils;
	@Autowired
	StatoContrattoRepo statoContrattoRepo;
	@Autowired
	public ContrattoUtils contrattoUtils;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	public FornitoreServiceImpl fornitoreServiceImpl;

	/**
	 * Modifica lo stato del processo di validazione del contratto.
	 * 
	 * 1	Da Stipulare
	 * 2	Da Validare
	 * 3	Da Validare 2^Livello
	 * 4	Validato
	 * 5	Da escludere
	 * 
	 * @param contratto
	 * @param idStProcesso
	 * @param save
	 */
	private void setStatoProcesso(Contratto contratto, long idStProcesso, boolean save) {
		StatoProcesso statoProcesso = statoProcessoRepo.findByIdStProcesso(idStProcesso);
		contratto.setStatoProcesso(statoProcesso);
		if(save) {
			contrattoRepository.save(contratto);
		}
	}

	@Transactional
	@Override
	public void modificaContratto(ContrattoDTO contrattoIn) {
		/*
    	// Controlla e Salva le modifiche Essenziali
    	storicoEssenzValUtils.checkStoricoEssenzVal(contrattoIn);
    	if(true) {
    		throw new RuntimeException("lore");
    	}
		 */

		modificaDatiGeneraliContratto(contrattoIn);
		if(contrattoIn.getRiferimentos()!=null) {
			modificaRiferimentoContratto(contrattoIn.getRiferimentos(), contrattoIn.getIdContratto());
		}
		modificaProceduraContratto(contrattoIn);
		if(contrattoIn.getCronologias()!=null) {
			modificaDateCronologiaContratto(contrattoIn.getCronologias(), contrattoIn.getIdContratto());
		}
		if(contrattoIn.getImportos()!=null) {
			modificaImportoContratto(contrattoIn,contrattoIn.getIdContratto());
		}
		if(contrattoIn.getContrattoStrutturas()!=null) {
			modificaContrattoStruttura(contrattoIn.getContrattoStrutturas(),contrattoIn.getIdContratto());
		}
		if(contrattoIn.getAttoreContrattos()!=null) {
			modificaAttoreContratto(contrattoIn.getAttoreContrattos(),contrattoIn.getIdContratto());
		}
		if(contrattoIn.getFornitoreContrattos()!=null) {
			modificaFornitoreContratto(contrattoIn.getFornitoreContrattos(),contrattoIn.getIdContratto());
		}
		if(contrattoIn.getIntegraziones()!=null) {
			modificaIntegrazione(contrattoIn.getIntegraziones(), contrattoIn.getIdContratto());
		}
		
		contrattoRepository.flush();
		
		
		contrattoUtils.setStatoContratto(contrattoIn.getIdContratto());
		// Controlla e Salva le modifiche Essenziali
		storicoEssenzValUtils.checkStoricoEssenzVal(contrattoIn);
		//		setStatoProcesso(con,)
	}

	
	@Override
	public Contratto modificaDatiGeneraliContratto(ContrattoDTO contrattoIn) {
		Contratto contratto = contrattoRepository.findByCig(contrattoIn.getCig());
		if (contratto == null) {
			throw new ResourceNotFoundException("Contratto non trovato con CIG [" + contrattoIn.getCig() + "]");
		}
		contratto.setCigPadre(contrattoIn.getCigPadre());
		contratto.setMotivazioneCollegamento(contrattoIn.getMotivazioneCollegamento()!=null?contrattoIn.getMotivazioneCollegamento():null);
		contratto.setDescrizioneCrtt(contrattoIn.getDescrizioneCrtt());
		if(contrattoIn.getProceduraOrg()!=null && (contrattoIn.getProceduraOrg().getCodProcedura()==null ||contrattoIn.getProceduraOrg().getCodProcedura().equals(""))) {
			contratto.setProceduraOrg(null);
			contrattoIn.setProceduraOrg(null);
		}
		if(contrattoIn.getProceduraRin()!=null && (contrattoIn.getProceduraRin().getCodProcedura()==null || contrattoIn.getProceduraRin().getCodProcedura().equals("")) ){
			contratto.setProceduraRin(null);
			contrattoIn.setProceduraRin(null);
		}
		Optional <TipoContratto>optTipo=tipoContrattoRepo.findById(contrattoIn.getTipoContratto().getIdTpContratto());
		contratto.setTipoContratto(optTipo.isPresent()?optTipo.get():null);

		if(contrattoIn.getTipoSubappalto()!=null) {
			Optional <TipoSubappalto> optSub= tipoSubappaltoRepo.findById(contrattoIn.getTipoSubappalto().getIdTipoSub());
			contratto.setTipoSubappalto(optSub.isPresent()?optSub.get():null);
		}
		
		contrattoIn.getCronologias().sort((p1, p2) -> p1.getDtInizioEvento().compareTo(p2.getDtInizioEvento()));
		
		
		contratto.setNoteStato(contrattoIn.getNoteStato());
		contratto.setComparto(contrattoIn.getComparto());
		contratto.setCatMerceologica(contrattoIn.getCatMerceologica());
		contratto.setRicorrente(contrattoIn.getRicorrente());
		contratto.setUseridAggior(UserUtil.getLoggedUserId());
//		contrattoRepository.save(contratto);
		return contrattoRepository.save(contratto);
	}

	@Override
	public void modificaRiferimentoContratto(List<RiferimentoDTO> riferimentoDTO, long idContratto) {
		
		List<Long> idDB= riferimentoRepo.findIdRiferimentoByContrattoIdContratto(idContratto);
		
		for(RiferimentoDTO rif: riferimentoDTO) {
			idDB.remove(rif.getIdRiferimento());
			Riferimento riferimento = riferimentoRepo.findByIdRiferimento(rif.getIdRiferimento());
			if(riferimento!=null) {
				riferimento.setCodRiferimento(rif.getCodRiferimento());
				riferimento.setDataAggior(Timestamp.from(Instant.now()));
				riferimento.setDataVal(rif.getDataVal());
				riferimento.setDescrizione(rif.getDescrizione());
				riferimento.setLink(rif.getLink());
				riferimento.setUseridAggior(rif.getUseridAggior());
				TipoRiferimentoDTO tipoRiferimentoDTO = rif.getTipoRiferimento();
				if(tipoRiferimentoDTO!=null) {
				TipoRiferimento tipoRiferimento = new TipoRiferimento();
				tipoRiferimento.setIdTpRiferimento(tipoRiferimentoDTO.getIdTpRiferimento());
				riferimento.setTipoRiferimento(tipoRiferimento);
				}
				riferimento.setUseridAggior(UserUtil.getLoggedUserId());
				riferimentoRepo.save(riferimento);
			}else {
				Riferimento riferimentoNew= new Riferimento();
				riferimentoNew.setCodRiferimento(rif.getCodRiferimento());
				riferimentoNew.setDataAggior(null);
				riferimentoNew.setDataVal(rif.getDataVal());
				riferimentoNew.setDescrizione(rif.getDescrizione());
				riferimentoNew.setLink(rif.getLink());
				riferimentoNew.setUseridAggior(UserUtil.getLoggedUserId());
				if(rif.getTipoRiferimento()!=null) {
				TipoRiferimento tipoRiferimento= tipoRiferimentoRepo.findById(rif.getTipoRiferimento().getIdTpRiferimento()).orElse(null);
				riferimentoNew.setTipoRiferimento(tipoRiferimento);
				}
				riferimentoNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
				riferimentoRepo.save(riferimentoNew);
			}
		}
		if( !idDB.isEmpty()) {
			for (Long elem : idDB) {
				Riferimento rif = riferimentoRepo.findByIdRiferimento(elem);
				if (rif != null)
					riferimentoRepo.delete(rif);
			}
		}
	}

	


	
	@Override
	public void modificaDateCronologiaContratto(List<CronologiaDTO> cronologiaDTO,long  idContratto) {
		List<Long> idDB=cronologiaRepo.findIdCronologiaByContrattoIdContrattoAndDataFineVal(idContratto);
		for(CronologiaDTO cron:cronologiaDTO) {
			
			List<Cronologia> cronApertaList=cronologiaRepo.findCronologiaAperta(idContratto, cron.getTipoCronologia().getIdTpCronologia());
			Cronologia cronologiaNew1 = dtoMapper.cronologiaDTOToCronologia(cron);
			if(!cronApertaList.isEmpty()){
				idDB.remove(cronApertaList.get(0).getIdCronologia());
				if (cronApertaList.get(0).getIdCronologia() == cron.getIdCronologia()) {
					Cronologia cronAperta = cronApertaList.get(0);
					cronAperta.equals(cronologiaNew1);
					if (!DateUtil.equalsOnlyDate(cronAperta.getDtInizioEvento(),cronologiaNew1.getDtInizioEvento()) || !cronAperta.equals(cronologiaNew1)) {
						// è stata modificata, deve essere storicizzata
						storicizzaCronologia(cron.getDtInizioEvento(), cronAperta);

						cronologiaNew1.setIdCronologia(0);
						cronologiaNew1.setContratto(cronAperta.getContratto());
						cronologiaNew1 = cronologiaRepo.save(cronologiaNew1);
					} else {
						// non ci sono modifiche, non fare nulla
					}
				} else {// se gli id sono diversi
						// chiudi attuale
					Cronologia cronAperta = cronApertaList.get(0);
					storicizzaCronologia(new Date(), cronAperta);
					// e riapri vecchia
					Cronologia cronStorica = cronologiaRepo.findById(cronologiaNew1.getIdCronologia()).orElse(null);
					if (cronStorica!=null && !DateUtil.equalsOnlyDate(cronStorica.getDtInizioEvento(),cronologiaNew1.getDtInizioEvento()) && !cronStorica.equals(cronologiaNew1)) {
						cronologiaNew1.setIdCronologia(0);
					}
					cronologiaNew1.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
					cronologiaNew1.setContratto(cronAperta.getContratto());
					cronologiaNew1.setDataFineVal(null);
					cronologiaNew1 = cronologiaRepo.save(cronologiaNew1);
					
				}
			}else {
				Cronologia cronologia = cronologiaRepo.findByIdCronologia(cronologiaNew1.getIdCronologia());
				if (cronologia != null
						&& (!DateUtil.equalsOnlyDate(cronologia.getDtInizioEvento(), cronologiaNew1.getDtInizioEvento())
								|| !cronologia.equals(cronologiaNew1))) {
					cronologiaNew1.setIdCronologia(0);
				}
				cronologiaNew1.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				cronologiaNew1.setContratto(contrattoRepository.findByIdContratto(idContratto));
				cronologiaNew1.setDataFineVal(null);
				cronologiaNew1 = cronologiaRepo.save(cronologiaNew1);
			}
		}
		if(!idDB.isEmpty()) {
			for (Long elem : idDB) { //cancellazione
				Cronologia i = cronologiaRepo.findByIdCronologia(elem);
//				if(i.getTipoCronologia().getIdTpCronologia()==7) {
//					List<Cronologia> cronSosp=cronologiaRepo.findCronologieChiuse(idContratto, idContratto);
//					if(!cronSosp.isEmpty()) {
//						Cronologia cron=cronSosp.get(0);
//						cron.setDataFineVal(null);
//						cronologiaRepo.save(cron);
//					}
//				}
				storicizzaCronologia(new Date(), i);
			}
		}
	}
	
	
	
	private Importo storicizzaImporto(Date dataInizioEvento, Importo importo) {
		if(dataInizioEvento!=null) {
			importo.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataInizioEvento));
		}
		importo.setDataFineVal(DateUtil.getClosedDate());
		importo.setDataAggior(Timestamp.from(Instant.now()));
		importo.setUseridAggior(UserUtil.getLoggedUserId());
		return importoRepo.save(importo);
	}
	
	private Cronologia storicizzaCronologia(Date dataInizioEvento, Cronologia cronologia) {
		if(dataInizioEvento!=null) {
			cronologia.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataInizioEvento));
		}
		cronologia.setDataFineVal(DateUtil.getClosedDate());
		cronologia.setDataAggior(Timestamp.from(Instant.now()));
		cronologia.setUseridAggior(UserUtil.getLoggedUserId());
		return cronologiaRepo.save(cronologia);
	}


	@Override
	public void modificaImportoContratto(ContrattoDTO contrattoIn,long idContratto) {
		List<Long> idDB=importoRepo.findIdImportoByContrattoIdContrattoAndDataFineVal(idContratto);
		for(ImportoDTO imp:contrattoIn.getImportos()) {
			
			List<Importo> importoApertoList=importoRepo.findImportoAperto(idContratto, imp.getTipoImporto().getIdTipImporto());
			Importo importoNew1 = dtoMapper.importoDTOToImporto(imp);
			if(!importoApertoList.isEmpty()){
				idDB.remove(importoApertoList.get(0).getIdImporto());
				if (importoApertoList.get(0).getIdImporto().longValue() == imp.getIdImporto().longValue()) {
					Importo impAperto = importoApertoList.get(0);
					if (!DateUtil.equalsOnlyDate(impAperto.getDtInizioEvento(), importoNew1.getDtInizioEvento()) || !impAperto.equals(importoNew1)) {
						// è stata modificata, deve essere storicizzata
						storicizzaImporto(importoNew1.getDtInizioEvento(), impAperto);

						importoNew1.setIdImporto(0l);
						importoNew1.setDataFineVal(null);
						importoNew1.setContratto(impAperto.getContratto());
						importoNew1 = importoRepo.save(importoNew1);
					} else {
						// non ci sono modifiche, non fare nulla
					}
				} else {// se gli id sono diversi
						// chiudi attuale
					Importo impAperto = importoApertoList.get(0);
					storicizzaImporto(new Date(), impAperto);
					// e riapri vecchia
					Importo impStorico = importoRepo.findById(importoNew1.getIdImporto()).orElse(null);
					if (impStorico!=null) {
					if ( !DateUtil.equalsOnlyDate(impAperto.getDtInizioEvento(), importoNew1.getDtInizioEvento()) || !impStorico.equals(importoNew1)) {
						importoNew1.setIdImporto(0l);
					}}
					importoNew1.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
					importoNew1.setContratto(impAperto.getContratto());
					importoNew1.setDataFineVal(null);
					importoNew1 = importoRepo.save(importoNew1);
					
				}
			}else {
				importoNew1.setIdImporto(0l);
				importoNew1.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				importoNew1.setContratto(contrattoRepository.findByIdContratto(idContratto));
				importoNew1.setDataFineVal(null);
				importoNew1 = importoRepo.save(importoNew1);
			}
		}
		if(!idDB.isEmpty()) {
			for (Long elem : idDB) { //cancellazione
				Importo i = importoRepo.findByIdImporto(elem);
				storicizzaImporto(new Date(), i);
			}
		}
	}

	

	

	@Override
	public void modificaProceduraContratto(ContrattoDTO contrattoIn) {
		Procedura proceduraOrig = dtoMapper.proceduraDTOToProcedura(contrattoIn.getProceduraOrg());
		Procedura proceduraRinn = dtoMapper.proceduraDTOToProcedura(contrattoIn.getProceduraRin());
		
		if(contrattoIn.getProceduraOrg()!=null) {
			
			if(contrattoIn.getProceduraOrg().getIdProcedura()!=null && contrattoIn.getProceduraOrg().getIdProcedura()!=0) {
				//update
				Procedura proceduraDB=proceduraRepo.findById(contrattoIn.getProceduraOrg().getIdProcedura()).orElse(null);
				
				
				if(proceduraDB!=null) {
					proceduraOrig=proceduraDB;
					proceduraOrig = dtoMapper.proceduraDTOToProcedura(contrattoIn.getProceduraOrg());
					proceduraOrig.setIdProcedura(proceduraDB.getIdProcedura());
				}
				//inserimento
				proceduraOrig.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraOrig.setDataAggior(Timestamp.from(Instant.now()));
				proceduraOrig=proceduraRepo.save(proceduraOrig);
			}else {
				if(contrattoIn.getProceduraOrg().getIdProcedura()==null && contrattoIn.getProceduraOrg().getCodProcedura()!=null) {
					//controllo se esiste gia
					Procedura procEx=proceduraRepo.findByCodProcedura(contrattoIn.getProceduraOrg().getCodProcedura());
						if(procEx!=null) {//esiste
							proceduraOrig=procEx;
							proceduraOrig = dtoMapper.proceduraDTOToProcedura(contrattoIn.getProceduraOrg());
							proceduraOrig.setIdProcedura(procEx.getIdProcedura());	
					}
				}else {
				
				proceduraOrig.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraOrig.setDataAggior(Timestamp.from(Instant.now()));
				proceduraOrig=proceduraRepo.save(proceduraOrig);
				}
			}
			
		}else {
			proceduraOrig=null;
		}
		if(contrattoIn.getProceduraRin()!=null) {
			if(contrattoIn.getProceduraRin().getIdProcedura()!=null && contrattoIn.getProceduraRin().getIdProcedura()!=0) {
				//update
				Procedura proceduraDB=proceduraRepo.findById(contrattoIn.getProceduraRin().getIdProcedura()).orElse(null);
				if(proceduraDB!=null) {
					proceduraRinn=proceduraDB;
					proceduraRinn = dtoMapper.proceduraDTOToProcedura(contrattoIn.getProceduraRin());
					proceduraRinn.setIdProcedura(proceduraDB.getIdProcedura());
				}
				//inserimento
				proceduraRinn.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraRinn.setDataAggior(Timestamp.from(Instant.now()));
				proceduraRinn=proceduraRepo.save(proceduraRinn);
			}else {
				proceduraRinn.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraRinn.setDataAggior(Timestamp.from(Instant.now()));
				proceduraRinn=proceduraRepo.save(proceduraRinn);
			}
		}else {
			proceduraRinn=null;
		}
		
			
			Contratto contratto = contrattoRepository.findByCig(contrattoIn.getCig());
			// Modifica dello Stato del processo di Validazione del Contratto
			this.setStatoProcesso(contratto, 2, false); // Da Validare
			if (contrattoIn.getProceduraOrg()!=null) {
				contratto.setProceduraOrg(proceduraOrig);
			}
			if (contrattoIn.getProceduraRin()!=null) {
				contratto.setProceduraRin(proceduraRinn);
			}
			contratto.setUseridAggior(UserUtil.getLoggedUserId());
			contratto.setDataAggior(Timestamp.from(Instant.now()));
			contrattoRepository.save(contratto);
			
	}
	
	private ContrattoStruttura storicizzaStruttura(Date dataInizioEvento, ContrattoStruttura struttAperta) {
		struttAperta.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataInizioEvento));
		struttAperta.setDataFineVal(DateUtil.getClosedDate());
		struttAperta.setDataAggior(Timestamp.from(Instant.now()));
		struttAperta.setUseridAggior(UserUtil.getLoggedUserId());
		return contrattoStrutturaRepo.save(struttAperta);
	}

	/**
	 * @param contrattoStrutturaModifificaDTO
	 * @return
	 */
	@Override
	public void modificaContrattoStruttura(List<ContrattoStrutturaDTO> contrattoStrutturaDTO, long idContratto) {
		
		
		//recupero tutti i contratto struttura attivi dal db (serve per eliminazione strutt dest)
		List<Long> idDB=contrattoStrutturaRepo.findIdContrattoStrutturaByContrattoIdContrattoAndDataFineVal(idContratto, 3);
		
		for(ContrattoStrutturaDTO conStr:contrattoStrutturaDTO) {
		
		switch ((int)conStr.getRuoloStruttura().getIdRuoloStr()) {
		case 3: //DESTINATARIA
			
			//contr strutt recuperato dal db con quell'id
			ContrattoStruttura contrattoStruttura =  contrattoStrutturaRepo.findByIdContrattoStruttura(conStr.getIdContrattoStruttura());
			//nuovo contr strutt passato in input
			ContrattoStruttura contrattoStrutturaNew = dtoMapper.toContrattoStrutturaDto(conStr);
			contrattoStrutturaNew.setDataAggior(Timestamp.from(Instant.now()));
			contrattoStrutturaNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
			contrattoStrutturaNew.setUseridAggior(UserUtil.getLoggedUserId());
			
			//se  esiste sul db  prima chiudo il vecchio 
			if(contrattoStruttura!=null) { // esiste
				idDB.remove(conStr.getIdContrattoStruttura()); //per eliminazione
				if(contrattoStrutturaNew.getDtFineEvento()==null) {
					
					
					 
					if(! DateUtil.equalsOnlyDate(contrattoStruttura.getDtInizioEvento(), contrattoStrutturaNew.getDtInizioEvento()) || contrattoStruttura.getAnagrafeStruttura().getIdStruttura()!=contrattoStrutturaNew.getAnagrafeStruttura().getIdStruttura()) {
							//è stata modificata, deve essere storicizzata
						storicizzaStruttura(contrattoStrutturaNew.getDtInizioEvento(), contrattoStruttura);
						contrattoStrutturaNew.setIdContrattoStruttura(0);
						contrattoStrutturaNew.setContratto(contrattoStruttura.getContratto());
						contrattoStrutturaNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
						contrattoStrutturaNew.setDataFineVal(null);
						contrattoStrutturaNew=contrattoStrutturaRepo.save(contrattoStrutturaNew);
					}else {
						//non ci sono modifiche, non fare nulla tranne se cambiano la data fine evento
						if(!DateUtil.equalsOnlyDate(contrattoStruttura.getDtFineEvento(), contrattoStrutturaNew.getDtFineEvento())) {
							contrattoStrutturaNew.setContratto(contrattoStruttura.getContratto());
							contrattoStrutturaNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
							contrattoStrutturaNew.setDataFineVal(null);
							contrattoStrutturaNew=contrattoStrutturaRepo.save(contrattoStrutturaNew);
						}
					}
				}else {
					//è stata chiusa la storicizziamo
					contrattoStrutturaNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
					storicizzaStruttura(DateUtil.inizioEventoPiuUno(contrattoStrutturaNew.getDtFineEvento()), contrattoStrutturaNew);
					
				}
				
			}else {//inserimento
				contrattoStrutturaNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
				contrattoStrutturaNew.setIdContrattoStruttura(0);
				contrattoStrutturaNew=contrattoStrutturaRepo.save(contrattoStrutturaNew);
			}
			
			
	
			break;
		default: //VALIDANTE o RESPONSABILE
			ContrattoStruttura struttAperta=contrattoStrutturaRepo.findStrutturaAperta(idContratto, conStr.getRuoloStruttura().getIdRuoloStr()).get(0);
			ContrattoStruttura contrattoStrutturaNew1 = dtoMapper.toContrattoStrutturaDto(conStr);
			if(struttAperta.getIdContrattoStruttura()==conStr.getIdContrattoStruttura()) {
				if(!DateUtil.equalsOnlyDate(conStr.getDtInizioEvento(),struttAperta.getDtInizioEvento()) || conStr.getAnagrafeStruttura().getIdStruttura().longValue()!=struttAperta.getAnagrafeStruttura().getIdStruttura()) {
					//è stata modificata, deve essere storicizzata
					storicizzaStruttura(conStr.getDtInizioEvento(), struttAperta);
					
					contrattoStrutturaNew1.setIdContrattoStruttura(0);
					contrattoStrutturaNew1.setContratto(struttAperta.getContratto());
					contrattoStrutturaNew1.setDataFineVal(null);
					contrattoStrutturaNew1=contrattoStrutturaRepo.save(contrattoStrutturaNew1);
				}else {
					//non ci sono modifiche, non fare nulla
				}
			}else {
				//chiudi attuale 
				storicizzaStruttura(new Date(), struttAperta);
				//e riapri vecchia		
				ContrattoStruttura strStorica=contrattoStrutturaRepo.findById(contrattoStrutturaNew1.getIdContrattoStruttura()).orElse(null);
				if(strStorica!=null) {
					if(!DateUtil.equalsOnlyDate(strStorica.getDtInizioEvento(),contrattoStrutturaNew1.getDtInizioEvento()) || strStorica.getAnagrafeStruttura().getIdStruttura()!=contrattoStrutturaNew1.getAnagrafeStruttura().getIdStruttura()){
						contrattoStrutturaNew1.setIdContrattoStruttura(0);
					}
				}
				contrattoStrutturaNew1.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				contrattoStrutturaNew1.setContratto(struttAperta.getContratto());
				contrattoStrutturaNew1.setDataFineVal(null);
				contrattoStrutturaNew1=contrattoStrutturaRepo.save(contrattoStrutturaNew1);
			}
		}
		
		}
		
		
		
		//eliminazione su strutt dest
		if(!idDB.isEmpty()) {
			for (Long elem : idDB) { //cancellazione
				ContrattoStruttura i = contrattoStrutturaRepo.findByIdContrattoStruttura(elem);
				storicizzaStruttura(new Date(), i);
			}
		}
		

	}
	private FornitoreContratto storicizzaFornitoreContratto(Date dataInizioEvento, FornitoreContratto fornitoreContratto) {
		fornitoreContratto.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataInizioEvento));
		fornitoreContratto.setDataFineVal(DateUtil.getClosedDate());
		fornitoreContratto.setDataAggior(Timestamp.from(Instant.now()));
		fornitoreContratto.setUseridAggior(UserUtil.getLoggedUserId());
		return fornitoreContrattoRepository.save(fornitoreContratto);
	}
	private AttoreContratto storicizzaAttoreContratto(Date dataInizioEvento, AttoreContratto attcontrAperto) {
		attcontrAperto.setDtFineEvento(DateUtil.inizioEventoMenoUno(dataInizioEvento));
		attcontrAperto.setDataFineVal(DateUtil.getClosedDate());
		attcontrAperto.setDataAggior(Timestamp.from(Instant.now()));
		attcontrAperto.setUseridAggior(UserUtil.getLoggedUserId());
		return attoreContrattoRepo.save(attcontrAperto);
	}

	
	
	public void modificaAttoreContratto(List<AttoreContrattoDTO> attoreContrattoDTO, long idContratto) {
		
		List<Long> idDB= attoreContrattoRepo.findIdAttoreContrattoByContrattoAndDataFineVal(idContratto);
		
		
		
		for(AttoreContrattoDTO attContr:attoreContrattoDTO) {
			
			Attore attore = inserisciAttoreSeNonEsiste(attContr);
			
			
			
			List<AttoreContratto> attcontrApertoList=attoreContrattoRepo.findAttoreContrattoAperto(idContratto, attContr.getRuoloAttoreBean().getIdRuoloAttore());
			AttoreContratto attoreContrattoNew = dtoMapper.attoreContrattoDTOToAttoreContratto(attContr);
			
			if(attoreContrattoNew.getAttore().getUserid().equals("9999999")) {
				attoreContrattoNew.setCodServAtt(new BigDecimal(999));
				attoreContrattoNew.setRuoloAttoreBean(ruoloAttoreRepo.findById(3l).orElse(null));
				attoreContrattoNew.setDenominServ("ESTERNO");
				attoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
				attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				attoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
			}
			
			if(attcontrApertoList!=null && !attcontrApertoList.isEmpty()) {
				idDB.remove(attContr.getIdAttoreContratto());
				AttoreContratto attcontrAperto = attcontrApertoList.get(0);
				if(attContr.getIdAttoreContratto()==0|| attcontrAperto.getIdAttoreContratto()==attContr.getIdAttoreContratto()) {
					if(!DateUtil.equalsOnlyDate(attContr.getDtInizioEvento(),attcontrAperto.getDtInizioEvento()) || !attContr.getAttore().getNominativo().equals(attcontrAperto.getAttore().getNominativo())) {
						//è stata modificata, deve essere storicizzata
						storicizzaAttoreContratto(attContr.getDtInizioEvento(), attcontrAperto);
						attoreContrattoNew.setIdAttoreContratto(0);
						attoreContrattoNew.setContratto(attcontrAperto.getContratto());
						attoreContrattoNew.setAttore(attore);
						attoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
						attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
						attoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
						attoreContrattoNew=attoreContrattoRepo.save(attoreContrattoNew);
					}else {
						//non ci sono modifiche, non fare nulla
					}
				}else {
					//riattivazione
					//chiudi attuale 
					storicizzaAttoreContratto(new Date(), attcontrAperto);
					//e riapri vecchia		
					AttoreContratto attStorica=attoreContrattoRepo.findById(attoreContrattoNew.getIdAttoreContratto()).orElse(null);
					if(attStorica!=null) {
					if(!DateUtil.equalsOnlyDate(attoreContrattoNew.getDtInizioEvento(),attStorica.getDtInizioEvento()) || attoreContrattoNew.getAttore().getIdAttore()!=attStorica.getAttore().getIdAttore()) {
						attoreContrattoNew.setIdAttoreContratto(0);
					}
					}
					attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
					attoreContrattoNew.setContratto(attcontrAperto.getContratto());
					attoreContrattoNew.setDataFineVal(null);
					attoreContrattoNew.setAttore(attore);
					attoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
					attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
					attoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
					attoreContrattoNew=attoreContrattoRepo.save(attoreContrattoNew);
				}
			}else {
				//se non esiste lo creo
				attoreContrattoNew.setIdAttoreContratto(0);
				attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				attoreContrattoNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
				attoreContrattoNew.setDataFineVal(null);
				attoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
				attoreContrattoNew.setDataInizioVal(new Timestamp(System.currentTimeMillis()));
				attoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
				attoreContrattoNew.setAttore(attore);
				attoreContrattoNew=attoreContrattoRepo.save(attoreContrattoNew);
				
			}
		}
			
		//eliminazione 
				if(!idDB.isEmpty()) {
					for (Long elem : idDB) { //cancellazione
						AttoreContratto i = attoreContrattoRepo.findByIdAttoreContratto(elem);
						storicizzaAttoreContratto(new Date(), i);
					}
				}

	}

	private Attore inserisciAttoreSeNonEsiste(AttoreContrattoDTO attContr) {
		Attore attore =null;
		//controllo: se non mi viene passato l'id dell'attore significa è stato modificato qualcosa
		if(attContr.getAttore().getIdAttore()==null) {
			//controllo se quello userid è gia registrato nei nostri sistemi
			attore = attoreRepo.findByUserid(attContr.getAttore().getUserid());
			//se non esiste lo creo
			if(attore ==null) {
				//se non esiste lo aggiungo nuovo
				attore = new Attore();
				attore.setUserid(attContr.getAttore().getUserid());
			}else {
				
			}
			
			attore.setNominativo(attContr.getAttore().getNominativo());
			attore.setNome(attContr.getAttore().getNome());
			attore.setCognome(attContr.getAttore().getCognome());
			if (!attContr.getAttore().getEmail().isEmpty()) {
				attore.setEmail(attContr.getAttore().getEmail());
			}
			attore=attoreRepo.save(attore);
		}else {
			//se mi passano l'id lo recupero dal db
			if(attContr.getAttore().getIdAttore()==1) {
				attore=attoreRepo.findByUserid("9999999");
			}else {
				attore=attoreRepo.findByIdAttore(attContr.getAttore().getIdAttore());
			}
		}
		return attore;
	}
	
	private Fornitore inserisciFornitoreSeNonEsiste(FornitoreDTO forn) {
		Fornitore fornitore =null;
		//controllo: se non mi viene passato l'id del fornitore significa che è da creare
		if(forn.getIdFornitore()==null || forn.getIdFornitore().equals(Long.valueOf(0))) {
			fornitore=dtoMapper.fornitoreDtoTofornitore(fornitoreServiceImpl.saveFornitori(forn));
		}else {
			//gia esiste. lo ritorno
			fornitore=fornitoreRepo.findByIdFornitore( forn.getIdFornitore());
		}
		return fornitore;
	}









	@Override
	public void modificaFornitoreContratto(List<FornitoreContrattoDTO> fornitoreContrattoIn, long idContratto) {
		
		List<Long> idDB=fornitoreContrattoRepository.findIdFornitoreContrattoByContrattoIdContrattoAndDataFineVal(idContratto);
		for(FornitoreContrattoDTO forn:fornitoreContrattoIn) {
			
			// recuperato dal db con quell'id
			FornitoreContratto fornitoreContratto =  fornitoreContrattoRepository.findByIdFornitoreContratto(forn.getIdFornitoreContratto());
			FornitoreContratto fornitoreContrattoNew = dtoMapper.fornitoreContrattoDtoTofornitoreContratto(forn);
			
			//inserire fornitore se non esiste
			Fornitore fornitoreDb = inserisciFornitoreSeNonEsiste(forn.getFornitore());
			
			//se  esiste sul db  prima chiudo il vecchio 
			if(fornitoreContratto!=null) { // esiste
				idDB.remove(forn.getIdFornitoreContratto()); //per eliminazione
				if(fornitoreContrattoNew.getDtFineEvento()==null) {
					
					 
					if(! DateUtil.equalsOnlyDate(fornitoreContratto.getDtInizioEvento(), fornitoreContrattoNew.getDtInizioEvento())) {
							//è stata modificata, deve essere storicizzata
						storicizzaFornitoreContratto(fornitoreContrattoNew.getDtInizioEvento(), fornitoreContratto);
						

						fornitoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
						fornitoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
						fornitoreContrattoNew.setFornitore(fornitoreDb);
						fornitoreContrattoNew.setIdFornitoreContratto(0);
						fornitoreContrattoNew.setContratto(fornitoreContratto.getContratto());
						
						fornitoreContrattoNew=fornitoreContrattoRepository.save(fornitoreContrattoNew);
					}else {
						if(fornitoreContratto.getTipologiaFornitore().getIdTpFornitore()!=fornitoreContrattoNew.getTipologiaFornitore().getIdTpFornitore() || fornitoreContratto.getRuoloFornitore().getIdRlFornitore()!=fornitoreContrattoNew.getRuoloFornitore().getIdRlFornitore()) {
							fornitoreContrattoNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
							fornitoreContrattoNew.setDataAggior(new Timestamp(System.currentTimeMillis()));
							fornitoreContrattoNew=fornitoreContrattoRepository.save(fornitoreContrattoNew);
						}
					}
				}else {
					
					fornitoreContrattoNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
					storicizzaFornitoreContratto(DateUtil.inizioEventoPiuUno(fornitoreContrattoNew.getDtFineEvento()), fornitoreContrattoNew);
					
				}
				
			}else {//inserimento
				
				fornitoreContrattoNew.setDataAggior(Timestamp.from(Instant.now()));
				fornitoreContrattoNew.setUseridAggior(UserUtil.getLoggedUserId());
				fornitoreContrattoNew.setFornitore(fornitoreDb);
				fornitoreContrattoNew.setContratto(contrattoRepository.findByIdContratto(idContratto));
				fornitoreContrattoNew.setIdFornitoreContratto(0);
				fornitoreContrattoNew=fornitoreContrattoRepository.save(fornitoreContrattoNew);
			}
		}
		if(!idDB.isEmpty()) {
			for (Long elem : idDB) { //cancellazione
				FornitoreContratto i = fornitoreContrattoRepository.findByIdFornitoreContratto(elem);
				storicizzaFornitoreContratto(new Date(), i);
			}
		}
	}



	/**
	 * Metodo per il salvataggio di un integrazione. 
	 * Verifica che uno tra cf e partita iva sia valorizzato 
	 * Verifica che pmi sia valorizzato 
	 * Imposta la data aggiornamento con la data odierna 
	 */

	@Override
	public void saveDatiIntegrazioni(IntegrazioneFullDTO integrazioneDTO, Integrazione integrazione) {


		//faccio un controllo sulle cronologie se non esistono gli aggiungo al db
		if(integrazioneDTO.getCronologias()!=null && !integrazioneDTO.getCronologias().isEmpty()) {
			for(CronologiaIntegrazioneDTO cron:integrazioneDTO.getCronologias()) {
				if(cron.getTipoCronologia()!=null) {
					Cronologia newCrono = new Cronologia();
					Contratto contratto= contrattoRepository.findByIdContratto(integrazione.getContrattoCigOrigine().getIdContratto());
					newCrono.setContratto(contratto);
					newCrono.setTipoCronologia(dtoMapper.tipoCronologiaDTOToTipoCronologia(cron.getTipoCronologia()));
					newCrono.setDtInizioEvento(cron.getDtInizioEvento());
					newCrono.setFlagPresunta(cron.getFlagPresunta());
					newCrono.setMotivazione(cron.getMotivazione());
					newCrono.setIntegrazione(integrazione);
					newCrono.setDataInizioVal(cron.getDataInizioVal());
					newCrono.setDataAggior(Timestamp.from(Instant.now()));
					newCrono.setUseridAggior(UserUtil.getLoggedUserId());
					if(cron.getTipoCronologia().getIdTpCronologia()!=1) {
						List<Cronologia> cronologie=cronologiaRepo.findCronologiaAperta(contratto.getIdContratto(), cron.getTipoCronologia().getIdTpCronologia());
						if(!cronologie.isEmpty()) {
							Cronologia cronologia=cronologie.get(0);
							storicizzaCronologia(cron.getDtInizioEvento(), cronologia);
						}
					}
					cronologiaRepo.save(newCrono);
				}
			}	
		}

		//faccio un controllo sui fornitori contratti se non esistono gli aggiungo al db
		if(integrazioneDTO.getFornitoreContrattos()!=null && !integrazioneDTO.getFornitoreContrattos().isEmpty()) {
			for(FornitoreContrattoIntegrazioneDTO fc:integrazioneDTO.getFornitoreContrattos()) {
				if(fc.getTipologiaFornitore()!=null) {
					
					//controllo se il fornitore che voglio inserire non sia gia presente nella lista dei fornitori relativi al contratto
					List<FornitoreContratto> forncont=fornitoreContrattoRepository.findFornitoriContrattoByIdContrattoAndRagSoc(integrazione.getContrattoCigOrigine().getIdContratto(),fc.getFornitore().getRagioneSociale().toUpperCase());
					if(!forncont.isEmpty()) {
						throw new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT);
					}
					
				//inserire fornitore se non esiste
					Fornitore fornitore = inserisciFornitoreSeNonEsiste(fc.getFornitore());
				
				
				//inserisco il nuovo fornitoreContratto
					FornitoreContratto newFornitore= new FornitoreContratto();
					Contratto contratto= contrattoRepository.findByIdContratto(integrazione.getContrattoCigOrigine().getIdContratto());
					newFornitore.setTipologiaFornitore(dtoMapper.tipoFornitoreDTOToTipoFornitore( fc.getTipologiaFornitore()));
					newFornitore.setRuoloFornitore(dtoMapper.ruoloFornitoreDTOToRuoloFornitore( fc.getRuoloFornitore()));
					newFornitore.setDataInizioVal(Timestamp.from(Instant.now()));
					newFornitore.setContratto(contratto);
					newFornitore.setDataAggior(Timestamp.from(Instant.now()));
					newFornitore.setDtInizioEvento(fc.getDtInizioEvento());
					newFornitore.setIntegrazione(integrazione);
					newFornitore.setUseridAggior(UserUtil.getLoggedUserId());
					newFornitore.setFornitore(fornitore);
				
					fornitoreContrattoRepository.save(newFornitore);
				}

			}	
		}

		//faccio un controllo sugli importi se non esistono gli aggiungo al db
		if(integrazioneDTO.getImportos()!=null && !integrazioneDTO.getImportos().isEmpty()) {
			for(ImportoIntegrazioneDTO imp:integrazioneDTO.getImportos()) {
				if(imp.getTipoImporto()!=null) {
					Importo impNew = new Importo();
					Contratto contratto= contrattoRepository.findByIdContratto(integrazione.getContrattoCigOrigine().getIdContratto());
					impNew.setDataInizioVal(Timestamp.from(Instant.now()));
					impNew.setDataAggior(Timestamp.from(Instant.now()));
					impNew.setDtInizioEvento(imp.getDtInizioEvento());
					impNew.setContratto(contratto);
					impNew.setTipoImporto(dtoMapper.tipoImportoDTOToListTipoImporto( imp.getTipoImporto()));
					impNew.setValoreImp(imp.getValoreImp());
					impNew.setUseridAggior(UserUtil.getLoggedUserId());
					impNew.setIntegrazione(integrazione);
					if(impNew.getTipoImporto().getIdTipImporto()!=1) {
						List<Importo> importi=importoRepo.findImportoAperto(contratto.getIdContratto(), impNew.getTipoImporto().getIdTipImporto());
						if(!importi.isEmpty()) {
							Importo importo=importi.get(0);
							storicizzaImporto(impNew.getDtInizioEvento(), importo);
						}
					}
					importoRepo.save(impNew);
				}
			}	
		}

	}



	@Override
	public void modificaIntegrazione(List<IntegrazioneFullDTO> integrazioneList,long idContratto) {
		
		List<Long> idDB= integrazioneRepo.findIdIntegrazioneByContrattoCigOrigine(idContratto);
		
		for(IntegrazioneFullDTO integrazioneModificaDTO:integrazioneList) {
			
			if(integrazioneModificaDTO.getTipoIntegrazione()!=null) {
				
				if (integrazioneModificaDTO.getFornitoreContrattos() != null
						&& !integrazioneModificaDTO.getFornitoreContrattos().isEmpty()
						&& integrazioneModificaDTO.getFornitoreContrattos().get(0).getDtInizioEvento() == null) {
					integrazioneModificaDTO.setFornitoreContrattos(null);
				}
				
			
			idDB.remove(integrazioneModificaDTO.getIdIntegrazione());
			Integrazione integrazione = new Integrazione();
			if(integrazioneModificaDTO.getIdIntegrazione()!=null) {
			 integrazione = integrazioneRepo.findById(integrazioneModificaDTO.getIdIntegrazione()).orElse(new Integrazione());
			}
			
			//Recupero la procedura
			Procedura proc =null;
			if(integrazioneModificaDTO.getProcedura()!=null && integrazioneModificaDTO.getProcedura().getCodProcedura()!=null && !integrazioneModificaDTO.getProcedura().getCodProcedura().equals("")) {
				if(integrazioneModificaDTO.getProcedura().getIdProcedura()!=null) {
					if(integrazioneModificaDTO.getProcedura().getCodProcedura()!=null) {
					 proc = proceduraRepo.findByCodProcedura(integrazioneModificaDTO.getProcedura().getCodProcedura());
					}
				}else {
					proc=proceduraRepo.findByCodProcedura(integrazioneModificaDTO.getProcedura().getCodProcedura());
					if(proc==null) {
						throw new ResourceNotContentException(ErrorMessage.PROCEDURA_NOT_FOUND);
					}
//					proc=new Procedura();
//					proc.setCodProcedura(integrazioneModificaDTO.getProcedura().getCodProcedura());
//					proc=proceduraRepo.save(proc);
				}
			}
			if(proc!=null) {
				integrazione.setProcedura(proc);
			}else {
				integrazione.setProcedura(null);
			}
			integrazione.setProcedura(proc);
			integrazione.setUseridAggior(UserUtil.getLoggedUserId());
			integrazione.setMotivazione(integrazioneModificaDTO.getMotivazione());
			integrazione.setDescrizione(integrazioneModificaDTO.getDescrizione());
			integrazione.setDataAggior(Timestamp.from(Instant.now()));
			integrazione.setCigGenerato(integrazioneModificaDTO.getCigGenerato());
			integrazione.setContrattoCigOrigine(contrattoRepository.findByIdContratto(idContratto));
			integrazione.setTipoIntegrazione(dtoMapper.tipoIntegrazioneDTOToTipoIntegrazione(integrazioneModificaDTO.getTipoIntegrazione()));
			integrazioneRepo.save(integrazione);
			
			//salva importo,cronologia, fornitori
			saveDatiIntegrazioni(integrazioneModificaDTO, integrazione);
		}
		}
		
		
		
		
		
		if( !idDB.isEmpty()) {
			for (Long elem : idDB) {
				Integrazione integrazione = integrazioneRepo.findByIdIntegrazione(elem);
				if (integrazione != null) {
					if(integrazione.getCronologias()!=null &&!integrazione.getCronologias().isEmpty()) {
						for(Cronologia c:integrazione.getCronologias()) {
							c.setIntegrazione(null);
							cronologiaRepo.save(c);
							cronologiaRepo.delete(c);
						}
					}
					integrazioneRepo.delete(integrazione);
				}
					
			}
		}
	}




}
