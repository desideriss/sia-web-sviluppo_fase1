package bdi.azd.sistina.siaweb.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import bdi.azd.sistina.siaweb.auth.util.RuoliUtil;
import bdi.azd.sistina.siaweb.converter.DettaglioContrattoConverter;
import bdi.azd.sistina.siaweb.converter.RicercaContrattoConverter;
import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.CheckStoricoAttoreFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoCronologiaFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoImportiFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoStruttureFilter;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoFilterDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoRecordDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoScaduto;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoSummaryDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaAggiungiDataFilterDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.DateContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.StatoProcessoDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
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
import bdi.azd.sistina.siaweb.entity.StatoContratto;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.entity.VRicercacontratto;
import bdi.azd.sistina.siaweb.enums.CsvHeaders;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
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
import bdi.azd.sistina.siaweb.repository.TipoCigRepo;
import bdi.azd.sistina.siaweb.repository.TipoContrattoRepo;
import bdi.azd.sistina.siaweb.repository.TipoCronologiaRepo;
import bdi.azd.sistina.siaweb.repository.TipoSubappaltoRepo;
import bdi.azd.sistina.siaweb.repository.VRicercaContrattoRepo;
import bdi.azd.sistina.siaweb.repository.custom.ContrattoRepositoryCustom;
import bdi.azd.sistina.siaweb.service.ContrattoService;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import bdi.azd.sistina.siaweb.service.FornitoreService;
import bdi.azd.sistina.siaweb.util.ContrattoUtils;
import bdi.azd.sistina.siaweb.util.CsvContent;
import bdi.azd.sistina.siaweb.util.CsvGenerator;
import bdi.azd.sistina.siaweb.util.UserUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContrattoServiceImpl implements ContrattoService {

	final ContrattoUtils contrattoUtils;
	final AnagrafeStrutturaRepo anagrafeStrutturaRepo;
	final FornitoreContrattoRepo fornitoreContrattoRepo;
	final FornitoreService fornitoreService;
	final FornitoreRepo fornitoreRepo;
	final CronologiaRepo cronologiaRepo;
	final ImportoRepo importoRepo;
	final ContrattoStrutturaRepo contrattoStrutturaRepo;
	final AttoreRepo attoreRepo;
	final StatoProcessoRepo statoProcessoRepo;
	final RiferimentoRepo riferimentoRepo;
	final ContrattoRepo contrattoRepository;
	final TipoCigRepo tipoCigRepo;
	final TipoContrattoRepo tipoContrattoRepo;
	final StatoContrattoRepo statoContrattoRepo;
	final TipoSubappaltoRepo tipoSubappaltoRepo;
	final TipoCronologiaRepo tipoCronologiaRepo;
	final RuoloFornitoreRepo ruoloFornitoreRepo;
	final RuoloAttoreRepo ruoloAttoreRepo;
	final AttoreContrattoRepo attoreContrattoRepo;
	final DatiEssenzialiIService datiEssenzialiIService;
	final CsvGenerator csvGenerator;
	final DettaglioContrattoConverter dettaglioContrattoConverter;
	final DtoMapper dtoMapper;
	final ContrattoRepositoryCustom contrattoRepositoryCustom;
	final VRicercaContrattoRepo vRicercaContrattoRepo;
	final ProceduraRepo proceduraRepo;
	final IntegrazioneRepo integrazioneRepo;

	public ContrattoServiceImpl(ContrattoRepo contrattoRepository, TipoCigRepo tipoCigRepo, TipoContrattoRepo tipoContrattoRepo, StatoContrattoRepo statoContrattoRepo,
			TipoSubappaltoRepo tipoSubappaltoRepo, TipoCronologiaRepo tipoCronologiaRepo,
			RuoloFornitoreRepo ruoloFornitoreRepo, RuoloAttoreRepo ruoloAttoreRepo, AttoreContrattoRepo attoreContrattoRepo, DettaglioContrattoConverter dettaglioContrattoConverter,
			DatiEssenzialiIService datiEssenzialiIService,CsvGenerator csvGenerator, DtoMapper dtoMapper, ContrattoRepositoryCustom contrattoRepositoryCustom, VRicercaContrattoRepo vRicercaContrattoRepo,
			StatoProcessoRepo statoProcessoRepo, RiferimentoRepo riferimentoRepo, ProceduraRepo proceduraRepo, CronologiaRepo cronologiaRepo, ImportoRepo importoRepo,
			ContrattoStrutturaRepo contrattoStrutturaRepo, AttoreRepo attoreRepo, FornitoreRepo fornitoreRepo, FornitoreService fornitoreService, FornitoreContrattoRepo fornitoreContrattoRepo,IntegrazioneRepo integrazioneRepo, AnagrafeStrutturaRepo anagrafeStrutturaRepo, ContrattoUtils contrattoUtils) {

		this.fornitoreContrattoRepo = fornitoreContrattoRepo;
		this.contrattoRepository = contrattoRepository;
		this.tipoCigRepo = tipoCigRepo;
		this.tipoContrattoRepo = tipoContrattoRepo;
		this.statoContrattoRepo = statoContrattoRepo;
		this.tipoSubappaltoRepo = tipoSubappaltoRepo;
		this.tipoCronologiaRepo = tipoCronologiaRepo;
		this.ruoloFornitoreRepo = ruoloFornitoreRepo;
		this.ruoloAttoreRepo = ruoloAttoreRepo;
		this.attoreContrattoRepo = attoreContrattoRepo;
		this.dettaglioContrattoConverter = dettaglioContrattoConverter;
		this.datiEssenzialiIService=datiEssenzialiIService;
		this.csvGenerator = csvGenerator;
		this.dtoMapper = dtoMapper;
		this.contrattoRepositoryCustom = contrattoRepositoryCustom;
		this.vRicercaContrattoRepo = vRicercaContrattoRepo;
		this.statoProcessoRepo = statoProcessoRepo;
		this.riferimentoRepo = riferimentoRepo;
		this.proceduraRepo = proceduraRepo;
		this.cronologiaRepo = cronologiaRepo;
		this.importoRepo = importoRepo;
		this.contrattoStrutturaRepo = contrattoStrutturaRepo;
		this.attoreRepo = attoreRepo;
		this.fornitoreRepo = fornitoreRepo;
		this.fornitoreService = fornitoreService;
		this.integrazioneRepo = integrazioneRepo;
		this.anagrafeStrutturaRepo = anagrafeStrutturaRepo;
		this.contrattoUtils = contrattoUtils;
	}

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public ContrattoDTO getContratto(String codiceContratto) {
		Contratto contratto = contrattoRepository.findByCig(codiceContratto);
		
		List<AttoreContratto> attoreContratto=contratto.getAttoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
		attoreContratto.sort( (o1,o2)->Long.compare(o1.getRuoloAttoreBean().getIdRuoloAttore(),o2.getRuoloAttoreBean().getIdRuoloAttore()));
		contratto.setAttoreContrattos(attoreContratto);
		
		contratto.setFornitoreContrattos(contratto.getFornitoreContrattos().stream().filter(c->c.getDataFineVal()==null).sorted(Comparator.comparingLong(FornitoreContratto:: getIdFornitoreContratto)).collect(Collectors.toList()));
		List<Cronologia> cronologie=contratto.getCronologias().stream().filter(c->c.getDataFineVal()==null).sorted(Comparator.comparingLong(Cronologia:: getIdCronologia)).collect(Collectors.toList());
		contratto.setContrattoStrutturas(contratto.getContrattoStrutturas().stream().filter(c->c.getDataFineVal()==null).sorted(Comparator.comparing(ContrattoStruttura:: getDataAggior)).collect(Collectors.toList()));
		List<Importo>importi=contratto.getImportos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
		importi.sort( (o1,o2)->Long.compare(o1.getTipoImporto().getIdTipImporto(),o2.getTipoImporto().getIdTipImporto()));
//		contratto.setImportos(contratto.getImportos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
		contratto.setImportos(importi);
		
		cronologie.sort( (o1,o2)->Long.compare(o1.getTipoCronologia().getIdTpCronologia(),o2.getTipoCronologia().getIdTpCronologia()));
		contratto.setCronologias(cronologie);
		
		
		
		// Inserire qui il checkDatiEssenziali
		
		
		
//		forn.setIntegrazione(fornitoreCont.getIntegrazione()!=null && fornitoreCont.getIntegrazione().getTipoIntegrazione()!=null && fornitoreCont.getIntegrazione().getDescrizione()!=null? fornitoreCont.getIntegrazione().getTipoIntegrazione()+" "+fornitoreCont.getIntegrazione().getDescrizione():null);
		
		if(contratto.getIntegraziones()!=null && !contratto.getIntegraziones().isEmpty()) {
			for (Integrazione integr : contratto.getIntegraziones()) {
				integr.setCronologias(new ArrayList<Cronologia>() );
				integr.setImportos(new ArrayList<Importo>() );
				integr.setFornitoreContrattos(new ArrayList<FornitoreContratto>() );
			}
		}
		List<Integrazione> integraziones = contratto.getIntegraziones();
		integraziones.sort( (o1,o2)->Long.compare(o1.getIdIntegrazione(),o2.getIdIntegrazione()));
		contratto.setIntegraziones(integraziones);

		return dtoMapper.contrattoEntityToContrattoDTO(contratto);
	}


	@Override
	public Set<RicercaContrattoDTO> findContratti(ContrattoFilterDTO contrattoFilterDTO) {
		Set<RicercaContrattoDTO> result = new HashSet<>();
		try {
			List<Contratto> contrattos  = contrattoRepositoryCustom.findContratti(contrattoFilterDTO);
			
			String struttura=null;
			String ruolo=null;
			
			UserDTO userDTO = RuoliUtil.getLoggedUser();
			if (userDTO != null) {
				if (userDTO.getRuoli() != null && !userDTO.getRuoli().isEmpty()) {
					RuoloDTO ruoloDTO = userDTO.getRuoli().get(0);
					ruolo = ruoloDTO.getCodice();
					List<String> strutture = ruoloDTO.getStrutture();
					if (strutture != null && !strutture.isEmpty()) {
						struttura = strutture.get(0);
					}
				}
			}
			
			log.info("RUOLO utente: " + ruolo);
			log.info("STRUTTURA utente: " + struttura);
			
			
			
			for (Contratto contratto : contrattos) {
				contratto.setCronologias(contratto.getCronologias().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
				contratto.setAttoreContrattos(contratto.getAttoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
				contratto.setContrattoStrutturas(contratto.getContrattoStrutturas().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
				contratto.setFornitoreContrattos(contratto.getFornitoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
				contratto.setImportos(contratto.getImportos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList()));
				if ( match(ruolo,struttura, contratto)) {
						result.add(RicercaContrattoConverter.contrattoToRicercaContrattoDTO(contratto));
				}
			}
			
			log.info("contratti trovati :"+result.size());
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
		
		
//		if (contrattoFilterDTO != null) {
//			contrattos = contrattoRepositoryCustom.findContratti(contrattoFilterDTO);
//		} else {
//			contrattos = vRicercaContrattoRepo.findAll();
//		}
//
//		var cigs = contrattos.stream().map(VRicercacontratto::getCig).distinct().toArray();
//		List<RicercaContrattoDTO> newContrattos = new ArrayList<>();
//		for (Object cig : cigs) {
//			var contratto = contrattos.stream().filter(x -> x.getCig().equals(cig.toString())).findFirst();
//			if (contratto.isEmpty()) {
//				break;
//			}
//			RicercaContrattoDTO contrattoDTO = dtoMapper.VRicercaContrattoToRicercaContrattoDTO(contratto.get());
//			contrattoDTO.setRagioniSociali(contrattos.stream().filter(x -> x.getCig().equals(cig.toString())).map(VRicercacontratto::getRagioneSociale).collect(Collectors.toList()));
//			contrattoDTO.setStrutturas(contrattos.stream().filter(x -> x.getCig().equals(cig.toString())).map(VRicercacontratto::getStruttura).collect(Collectors.toList()));
//			newContrattos.add(contrattoDTO);
//		}


//		return newContrattos;
	}


	private boolean match(String ruolo,String struttura, Contratto contratto) {
		boolean match = false;
		if(ruolo.equalsIgnoreCase("SISTINA_VALIDATORE") || ruolo.equalsIgnoreCase("SISTINA_UTENTE")) {
		for (ContrattoStruttura contstrut : contratto.getContrattoStrutturas()) {
			if (struttura!=null && contstrut.getAnagrafeStruttura().getCodiceStruttura().equals(new BigDecimal(struttura))) {
				if(ruolo.equalsIgnoreCase("SISTINA_VALIDATORE")&& (contstrut.getRuoloStruttura().getIdRuoloStr()==1)) {
					match = true;
					break;
				}else if(ruolo.equalsIgnoreCase("SISTINA_UTENTE") && (contstrut.getRuoloStruttura().getIdRuoloStr()==2 || contstrut.getRuoloStruttura().getIdRuoloStr()==3 )) {
					match=true;
					break;
				}
			}
		}
		}else { //altri ruoli
			match=true;
		}
		return match;
	}

	@Override
	public List<RicercaContrattoDTO> getListaContratti() {
		List<VRicercacontratto> contrattos = vRicercaContrattoRepo.findAll();
		return dtoMapper.listVRicercaContrattoContrattoToListRicercaContrattoDTO(contrattos);
	}

	@Override
	public List<ContrattoSummaryDTO> getContrattiByStatoProcesso(StatoProcessoDTO statoProcessoDTO) {

		StatoProcesso statoProcesso = dtoMapper.statoProcessoDTOToStatoProcesso(statoProcessoDTO);
		List<Contratto> contrattoList = contrattoRepository.findAllByStatoProcesso(statoProcesso);

		return dettaglioContrattoConverter.toSummaryDTO(contrattoList);
	}



	@Override
	public String downloadContrattiByStatoProcesso(List<ContrattoSummaryDTO> contrattiList) {
		CsvContent csv = new CsvContent();
		csv.setHeader(CsvHeaders.CONTRATTI_BY_STATO_PROCESSO.getValue());
		for (ContrattoSummaryDTO con : contrattiList) {
			csv.setRows(new ArrayList<>());
			HashMap<Integer, String> values = new HashMap<>();
			values.put(0, con.getCig() != null ? con.getCig() : "");
			values.put(1, con.getTipoCig() != null ? con.getTipoCig().getTipoCig() : null);
			values.put(2, con.getCigPadre() != null ? con.getCigPadre() : null);
			values.put(3, con.getDescrizione() != null ? con.getDescrizione() : null);
			values.put(4, con.getTipoContratto() != null ? con.getTipoContratto().getDescrizioneTipoContratto() : null);
			values.put(5, con.getImportoMassimo() != null ? con.getImportoMassimo() + "" : null);
			csv.getRows().add(values);
		}
		return csvGenerator.generate(csv);
	}

	/**
	 * Nota: Per far capire a Hibernate che non deve inserire i Fornitori e gli Attori devo
	 *       togliere tutti i dati e lasciare solo gli id (Contratto, Fornitore, Attore)
	 *       
	 * Inoltre non è possibile utilizzare @Transactional perché se già esiste il Fornitore -> fornitoreService.saveFornitori(fornitoreDTO);
	 * in automatico viene eseguito il Rollback nonostante il try/catch, perché Hibernate colleziona prima tutte
	 * le query del metodo e poi le esegue tutte insieme alla fine.
	 */
	@Override
	@Transactional(transactionManager = "transactionManager", rollbackFor = {Exception.class})
	public ContrattoDTO insertContratto(ContrattoDTO contrattoDTO) {
		// Controllo se esiste già un contratto con lo stesso codice contratto (CIG)
		if(contrattoDTO.getCig() != null) {
			Contratto contratto = contrattoRepository.findByCig(contrattoDTO.getCig());
			if(contratto != null) {
				throw new DataIntegrityException("Contratto con CIG [" + contrattoDTO.getCig() + "] già presente nel database");
			}
		}
		
		this.pulisciNuovoContratto(contrattoDTO); // Pulisce i dati sporchi passati dal FE
		
		List<FornitoreContrattoDTO> fornitoreContrattoDTOs = contrattoDTO.getFornitoreContrattos();
		List<AttoreContrattoDTO> attoreContrattoDTOs = contrattoDTO.getAttoreContrattos();
		contrattoDTO.setFornitoreContrattos(null);
		contrattoDTO.setAttoreContrattos(null);
		ContrattoDTO newContratto = saveContratto(contrattoDTO);
		newContratto.setFornitoreContrattos(fornitoreContrattoDTOs);
		newContratto.setAttoreContrattos(attoreContrattoDTOs);
		
		// Salvo i Fornitori + Fornitore Contratto
		if(fornitoreContrattoDTOs != null) {
			ContrattoDTO newContrattoDTO = new ContrattoDTO();
			newContrattoDTO.setIdContratto(newContratto.getIdContratto());
			for(FornitoreContrattoDTO fornitoreContrattoDTO : fornitoreContrattoDTOs) {
				fornitoreContrattoDTO.setIdContratto(newContrattoDTO.getIdContratto());
				FornitoreDTO fornitoreDTO = fornitoreContrattoDTO.getFornitore();
				fornitoreDTO.setUseridAggior(UserUtil.getLoggedUserId());
				// Salvo il Fornitore se non è già presente nel Database
				FornitoreDTO fornitoreDTOr = null;
				List<Fornitore> fornitores = fornitoreRepo.findByPartitaIvaAndCodiceFiscaleAndRagioneSociale(fornitoreDTO.getPartitaIva(), fornitoreDTO.getCodiceFiscale(), fornitoreDTO.getRagioneSociale());
				if(fornitores == null || fornitores.isEmpty()) {
					try {
						fornitoreDTOr = fornitoreService.saveFornitori(fornitoreDTO);
					}catch(DataIntegrityException e) {
						// Do Nothing, il fornitore già esiste nel Database
						log.warn("Fornitore [PI: " + fornitoreDTO.getPartitaIva() + ", CF: " + fornitoreDTO.getCodiceFiscale() + "] già prensente nel Database.");
					}
				}				
				fornitoreContrattoDTO.setFornitore(null);
				FornitoreDTO newFornitoreDTO = new FornitoreDTO();
				if(fornitoreDTOr != null) {
					newFornitoreDTO.setIdFornitore(fornitoreDTOr.getIdFornitore());
				}else {
					if(fornitores != null && fornitores.size() == 1) {
						Fornitore fornitore = fornitores.get(0);			            	
						newFornitoreDTO.setIdFornitore(fornitore.getIdFornitore());
					}else {
						throw new DataIntegrityException("Trovati troppi Fornitori");
					}
				}
				fornitoreContrattoDTO.setFornitore(newFornitoreDTO);
			}
			List<FornitoreContratto> fornitoreContrattos = dtoMapper.listFornitoreContrattoDtoTofornitoreContratto(fornitoreContrattoDTOs);
			// Rimetto l'Id del Contratto perchè non mappato in FornitoreContrattoDTO
			Contratto contratto = new Contratto();
			contratto.setIdContratto(newContratto.getIdContratto());
			fornitoreContrattos.forEach(o -> o.setContratto(contratto));
			fornitoreContrattoRepo.saveAll(fornitoreContrattos);
		}
		
		
		// Salvo gli Attori + Attore Contratto
		if(attoreContrattoDTOs != null) {
			ContrattoDTO newContrattoDTO = new ContrattoDTO();
			newContrattoDTO.setIdContratto(newContratto.getIdContratto());
			for(AttoreContrattoDTO attoreContrattoDTO : attoreContrattoDTOs) {
				attoreContrattoDTO.setIdContratto(newContrattoDTO.getIdContratto());
				AttoreDTO attoreDTO = attoreContrattoDTO.getAttore();
				if(attoreDTO.getIdAttore()!=null && attoreDTO.getIdAttore()==1) {
					attoreDTO.setIdAttore(null);				
					}
				if(attoreDTO != null && attoreDTO.getIdAttore() == null) {
					Attore attore = attoreRepo.findByUserid(attoreDTO.getUserid());
					if(attore == null) { // Attore non presente, lo inserisco
						if(attoreDTO.getNominativo() == null || attoreDTO.getNominativo().isBlank()) {
							attoreDTO.setNominativo(attoreDTO.getNome() + " " + attoreDTO.getCognome());
						}
						Attore newAttore = dtoMapper.attoreDTOToAttore(attoreDTO);
						attoreRepo.save(newAttore);
						attoreDTO.setIdAttore(newAttore.getIdAttore());
					}else {
						attoreDTO.setIdAttore(attore.getIdAttore());
					}
				}
			}
			List<AttoreContratto> attoreContrattos = dtoMapper.attoreContrattoDTOToAttoreContratto(attoreContrattoDTOs);
			// Rimetto l'Id del Contratto perchè non mappato in FornitoreContrattoDTO
			Contratto contratto = new Contratto();
			contratto.setIdContratto(newContratto.getIdContratto());
			attoreContrattos.forEach(o -> o.setContratto(contratto));
			attoreContrattoRepo.saveAll(attoreContrattos);
		}

		return newContratto;
	}

	@Transactional(transactionManager = "transactionManager", rollbackFor = {Exception.class})
	public ContrattoDTO saveContratto(ContrattoDTO contrattoDTO) {
		Contratto contratto = dtoMapper.contrattoDTOToContrattoEntity(contrattoDTO);
		
		
if(contrattoDTO.getProceduraOrg()!=null && contrattoDTO.getProceduraOrg().getCodProcedura()!=null && !contrattoDTO.getProceduraOrg().getCodProcedura().equals("")) {
			
			if(contrattoDTO.getProceduraOrg().getDescrizione()==null || contrattoDTO.getProceduraOrg().getDescrizione().equals("")) {
				contrattoDTO.getProceduraOrg().setDescrizione("ND");
			}
			
			Procedura proceduraOrig = dtoMapper.proceduraDTOToProcedura(contrattoDTO.getProceduraOrg());
			
			if(contrattoDTO.getProceduraOrg().getCodProcedura()!=null && contrattoDTO.getProceduraOrg().getIdProcedura()!=null && contrattoDTO.getProceduraOrg().getIdProcedura()!=0) {
				//update
				Procedura proceduraDB=proceduraRepo.findById(contrattoDTO.getProceduraOrg().getIdProcedura()).orElse(null);
				
				if(proceduraDB!=null) {
					proceduraOrig=proceduraDB;
					proceduraOrig = dtoMapper.proceduraDTOToProcedura(contrattoDTO.getProceduraOrg());
					proceduraOrig.setIdProcedura(proceduraDB.getIdProcedura());
				}
				//inserimento
				proceduraOrig.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraOrig.setDataAggior(Timestamp.from(Instant.now()));
				proceduraOrig=proceduraRepo.save(proceduraOrig);
				contratto.setProceduraOrg(proceduraOrig);
			}
//			else {
//				contratto.setProceduraOrg(null);
//			}
			else {
				Procedura findByCodProcedura = proceduraRepo.findByCodProcedura(proceduraOrig.getCodProcedura());
				if(findByCodProcedura==null) {
				proceduraOrig.setUseridAggior(UserUtil.getLoggedUserId());
				proceduraOrig.setDataAggior(Timestamp.from(Instant.now()));
				proceduraOrig.setIdProcedura(0l);
				proceduraOrig=proceduraRepo.save(proceduraOrig);
				contratto.setProceduraOrg(proceduraOrig);
				}else {
					contratto.setProceduraOrg(findByCodProcedura);
				}
			}
		}else {
			contratto.setProceduraOrg(null);
		}
		
		
		if(contratto.getContrattoStrutturas() != null) {
			List<ContrattoStruttura> contrattoStrutturas = contratto.getContrattoStrutturas();
			for(ContrattoStruttura contrattoStruttura : contrattoStrutturas) {
				if(contrattoStruttura.getAnagrafeStruttura() != null && contrattoStruttura.getAnagrafeStruttura().getIdStruttura() != 0) {
					Optional<AnagrafeStruttura> anagrafeStrutturaOPT = anagrafeStrutturaRepo.findById(contrattoStruttura.getAnagrafeStruttura().getIdStruttura());
					if(anagrafeStrutturaOPT.isPresent()) {
						contrattoStruttura.setAnagrafeStruttura(anagrafeStrutturaOPT.get());
					}
				}
			}
		}
		
		contrattoUtils.setStatoProcesso(contratto);
		contratto.setStatoContratto(statoContrattoRepo.findById(Long.valueOf(1)).orElse(null));
		contrattoRepository.save(contratto);
		contrattoRepository.flush();
		long idContratto = contratto.getIdContratto();
		contrattoDTO.setIdContratto(idContratto);
		contrattoUtils.setStatoContratto(idContratto);
		return contrattoDTO;	
		}

	/**
	 * Pulisce il DTO dai dati sporchi passato dal Front End. 
	 */
	private void pulisciNuovoContratto(ContrattoDTO contrattoDTO) {
		/*
		 * ProceduraOrg: Campo Opzionale. Se valorizzato deve essere già presente nel Database.
		 * Il FE dovrebbe escludere l'oggetto dal json invece viene passato un'oggetto senza id con i campi vuoti.  
		 */
		if(contrattoDTO.getProceduraOrg() != null && contrattoDTO.getProceduraOrg().getIdProcedura()==null && contrattoDTO.getProceduraOrg().getCodProcedura()==null) {
			contrattoDTO.setProceduraOrg(null);
		}
		
		/* Q: Se non si compila la data inizio evento nella sezione importi, il sistema va in 500, ma la data non è obbligatoria
		 *    da inserire -> nell'analisi inoltre non è previsto nessun valore di default, quindi il campo:
		 *    DT_INIZIO_EVENTO della tabella Importo deve essere nullable.
		 * R: Inserire una SYSDATE come data di Default.
		 */
		List<ImportoDTO> importoDTOs = contrattoDTO.getImportos();
		if(importoDTOs != null) {
//			importoDTOs.forEach(o -> o.setDtInizioEvento(new Date()));
		}
		
		/* Q: Se non si compila la data validità nella sezione riferimenti, il sistema va in 500, ma la data non è obbligatoria
		 *    da inserire -> stesso discorso per la data inizio evento della sezione importi, in questo caso è il campo:
		 *    DATA_VAL della tabella riferimento.
		 * R: Inserire una SYSDATE come data di Default.
		 */
		List<RiferimentoDTO> riferimentoDTOs = contrattoDTO.getRiferimentos();
		if(riferimentoDTOs != null) {
//			riferimentoDTOs.forEach(o -> o.setDataVal(new Date()));
		}
	}

	@Override
	public ContrattoDTO findContrattoById(long id) {
		return dtoMapper.contrattoEntityToContrattoDTO(contrattoRepository.findByIdContratto(id));
	}

	@Override
	public ContrattoDTO findContrattoByCig(String cig) {
		return dtoMapper.contrattoEntityToContrattoDTO(contrattoRepository.findByCig(cig));
	}


	@Override
	public void deleteContratto(long id) {
		Contratto contratto = contrattoRepository.findByIdContratto(id);
//		if(contratto.getAttoreContrattos()!=null) {
//			attoreContrattoRepo.deleteAll(contratto.getAttoreContrattos());
//		}
		if (contratto != null) {
					contrattoRepository.delete(contratto);
		}
	}


	@Override
	public List<RicercaContrattoDTO> getRicercaCigPadre(String cigContratto) {
		// TODO Auto-generated method stub
		List<Contratto> dettContrattoList = contrattoRepository.findAllByCigPadre(cigContratto);
		List<RicercaContrattoDTO> ricConDTO = new ArrayList<RicercaContrattoDTO>();
		for (Contratto cont : dettContrattoList) {
			RicercaContrattoDTO dto = new RicercaContrattoDTO();
			dto.setCig(cont.getCig());
			dto.setCigPadre(cont.getCigPadre());
			ricConDTO.add(dto);
		}

		return ricConDTO;
	}

	@Override
	public List<String> getRupNominativoLike(String nominativo) {
		String nominativoNew=nominativo.replace("%20"," ").toUpperCase();
//		String nominativoNew = String.valueOf(nominativo.charAt(0)).toUpperCase() + nominativo.substring(1, nominativo.length());
		return attoreContrattoRepo.findRupNominativo(nominativoNew);

	}


	@Override
	public List<ContrattoScaduto> getContractByRangeDate(Pageable pageable) {

		List<ContrattoScaduto> contrattoScadutoList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1);
		Date nextYear = cal.getTime();

		List<ContrattoRecordDTO> contratti = contrattoRepository.findAllContrattiByDate(today, nextYear, pageable);
		if (!CollectionUtils.isEmpty(contratti)) {
			contrattoScadutoList = dtoMapper.toContrattoScaduto(contratti);
		}
		return contrattoScadutoList;
	}

	@Override
	public List<String> getListUserId(String userid) {
		String userIdNew = String.valueOf(userid.charAt(0)).toUpperCase() + userid.substring(1, userid.length());
		return attoreContrattoRepo.findUseridAggior(userIdNew);

	}

	@Override
	public String downloadContrattiInScadenza(List<ContrattoScaduto> contrattoScadutiList) {
		CsvContent csv = new CsvContent();
		csv.setHeader(CsvHeaders.CONTRATTI_IN_SCADENZA.getValue());
		for (ContrattoScaduto con : contrattoScadutiList) {
			csv.setRows(new ArrayList<>());
			HashMap<Integer, String> values = new HashMap<>();
			values.put(0, con.getCig() != null ? con.getCig() : "");
			values.put(1, con.getCigPadre() != null ? con.getCigPadre() : null);
			values.put(2, con.getTipoCig() != null ? con.getTipoCig() : null);
			values.put(3, con.getTipoContratto() != null ? con.getTipoContratto() : null);
			values.put(4, con.getDenominazione()!= null ? con.getDenominazione() : null);
			values.put(5, con.getImportoMassimo() != null ? con.getImportoMassimo() + "" : null);
			csv.getRows().add(values);
		}
		return csvGenerator.generate(csv);
	}


	@Override
	public List<Cronologia> aggiungiDateCronologiaContratto(CronologiaAggiungiDataFilterDTO contrattoIn) {
		//Recupero le cronologie attive per l'id contratto e l'id cronologia in input
		List<Cronologia> cronologie =  cronologiaRepo.searchByIdContratto(contrattoIn.getIdContratto(), contrattoIn.getIdTpCronologia());
		//update della cronologia con l'id contratto e l'id cronologia in input settando datafineval a sysdate-1
		Calendar cal = Calendar.getInstance();
		cal.setTime(contrattoIn.getDtInizioEvento());
		cal.add(Calendar.DATE, -1);
		contrattoIn.setDtFineEvento(cal.getTime());
		cronologiaRepo.updateByIdContratto(contrattoIn.getIdContratto(), contrattoIn.getIdTpCronologia(), contrattoIn.getDtFineEvento());
		//Mi salvo la nuova data/cronologia inserita 
		Cronologia cronIn = new Cronologia();
		Contratto contr = new Contratto();
		TipoCronologia tipoCron = new TipoCronologia();
		Integrazione integrazione = new Integrazione();
		if(cronologie != null && !cronologie.isEmpty()) {
			for (int i = 0; i < cronologie.size(); i++) {
				if(!contrattoIn.getFlagPresunta().isEmpty() && !contrattoIn.getFlagPresunta().equals("")) {
					cronIn.setFlagPresunta(cronologie.get(i).getFlagPresunta());
				}else {
					cronIn.setFlagPresunta(contrattoIn.getFlagPresunta());  
				}
				if(!contrattoIn.getMotivazione().isEmpty() && !contrattoIn.getMotivazione().equals("")) {
					cronIn.setMotivazione(cronologie.get(i).getMotivazione());
				}else {
					cronIn.setMotivazione(contrattoIn.getMotivazione());
				}
				contr.setIdContratto(contrattoIn.getIdContratto());
				cronIn.setContratto(contr);
				cronIn.setDataInizioVal(cronologie.get(i).getDataInizioVal());
				cronIn.setDtInizioEvento(contrattoIn.getDtInizioEvento());
				cronIn.setIntegrazione(cronIn.getIntegrazione());
				tipoCron.setIdTpCronologia(contrattoIn.getIdTpCronologia());
				cronIn.setTipoCronologia(tipoCron);
				cronIn.setUseridAggior(UserUtil.getLoggedUserId());
				cronIn.setDataAggior(((new Timestamp(System.currentTimeMillis()))));
				cronologiaRepo.save(cronIn);
			}
		}else {
			cronIn.setFlagPresunta(contrattoIn.getFlagPresunta());
			cronIn.setMotivazione(contrattoIn.getMotivazione());
			contr.setIdContratto(contrattoIn.getIdContratto());
			cronIn.setContratto(contr);
			cronIn.setDtInizioEvento(contrattoIn.getDtInizioEvento());
			tipoCron.setIdTpCronologia(contrattoIn.getIdTpCronologia());
			cronIn.setTipoCronologia(tipoCron);
			cronIn.setUseridAggior(UserUtil.getLoggedUserId());
			cronIn.setDataAggior(((new Timestamp(System.currentTimeMillis()))));
			cronIn.setDataInizioVal(((new Timestamp(System.currentTimeMillis()))));
			cronologiaRepo.save(cronIn);
		}
		//Prendo il contratto relativo all'idContratto in input
		Contratto contratto = contrattoRepository.findByIdContratto(contrattoIn.getIdContratto());
		//Prendere tutte le cronologie associate al contratto
		List<Cronologia> allCronologie = cronologiaRepo.findByContrattoIdContratto(contrattoIn.getIdContratto());
		aggiornaStati(contratto);
		return allCronologie;
	}

	public Contratto aggiornaStati(Contratto contrattoIn) {
		List<Cronologia>cronologie=contrattoIn.getCronologias().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
		List<Cronologia>cronologieAttivo= new ArrayList<>();
		var idTpCron2 = cronologie.stream().filter(c->c.getTipoCronologia().getIdTpCronologia()==2).findFirst();
		if (idTpCron2.isPresent() && !idTpCron2.isEmpty()) {
			cronologieAttivo.add(idTpCron2.get());
		}
		var idTpCron3 = cronologie.stream().filter(c->c.getTipoCronologia().getIdTpCronologia()==3).findFirst();
		if(idTpCron3.isPresent() && !idTpCron3.isEmpty() ) {
			cronologieAttivo.add(idTpCron3.get());
		}
		if(cronologie.size()==1 && cronologie.get(0).getTipoCronologia().getIdTpCronologia()==1) {
			var x = statoContrattoRepo.findById(1l);
			if(x.isPresent() && !x.isEmpty()) {
			StatoContratto stCon=x.get();
			contrattoIn.setStatoContratto(stCon);
			}
		}else if(cronologieAttivo.size()==2) {
			var x = statoContrattoRepo.findById(2l);
			if(x.isPresent() && !x.isEmpty()) {
			StatoContratto stCon=x.get();
			contrattoIn.setStatoContratto(stCon);
			}
		}
		if(cronologie.stream().filter(c->c.getTipoCronologia().getIdTpCronologia()==2).count()==0 ) {
			var x = statoProcessoRepo.findById(1l);
			if(x.isPresent() &&!x.isEmpty()) {
			StatoProcesso stProc= x.get();
			contrattoIn.setStatoProcesso(stProc);
			}
		}else if(datiEssenzialiIService.checkDatiEssenzialiI(contrattoIn).getCampiNonValidi()==null) {
			var x = statoProcessoRepo.findById(2l);
			if(x.isPresent() &&!x.isEmpty()) {
			StatoProcesso stProc= x.get();
			contrattoIn.setStatoProcesso(stProc);
			}
		}

		return contrattoIn;
	}
	
	@Override
	public DateContrattoDTO getDateContratto(long idContratto) {
		List<Cronologia> date = cronologiaRepo.findByIdContratto(idContratto);
		DateContrattoDTO dto = new DateContrattoDTO();
		for (Cronologia cron : date) {
			if(cron.getTipoCronologia().getIdTpCronologia()==1) {
				dto.setDataAggiudicazione(cron.getDtInizioEvento());
			}else if(cron.getTipoCronologia().getIdTpCronologia()==2) {
				dto.setDataStipula(cron.getDtInizioEvento());
			}else if(cron.getTipoCronologia().getIdTpCronologia()==3) {
				dto.setDataDecorrenza(cron.getDtInizioEvento());
				dto.setDecorrenzaPresunta(cron.getFlagPresunta());
			}else if(cron.getTipoCronologia().getIdTpCronologia()==4) {
				dto.setDataPrimaScadenza(cron.getDtInizioEvento());
			}else if(cron.getTipoCronologia().getIdTpCronologia()==5) {
				dto.setDataUltimaScadenza(cron.getDtInizioEvento());
			}
		}

		return dto;
	}
	
	@Override
	public String downloadContrattiByRicerca(List<RicercaContrattoDTO> contrattiList) {
		CsvContent csv = new CsvContent();
		if(RuoliUtil.checkRuolo("SISTINA_GESTORE") || RuoliUtil.checkRuolo("SISTINA_VISUALIZZATORE")) {
			csv.setHeader(CsvHeaders.CONTRATTI_RICERCA_COMPLETE.getValue());
			for (RicercaContrattoDTO con : contrattiList) {
				csv.setRows(new ArrayList<>());
				HashMap<Integer, String> values = new HashMap<>();
				values.put(0, con.getCig() != null ? con.getCig() : "");
				if(con.getStrutturas()!=null) {
					String values1="";
					for(int i=0;i<con.getStrutturas().size();i++) {
						if(i!=con.getStrutturas().size()-1) {
							values1=values1.concat(con.getStrutturas().get(i).getDenominazione()).concat(" - ");
						}else {
							values1=values1.concat(con.getStrutturas().get(i).getDenominazione());
						}
						values.put(1, values1);
					}
				}
				values.put(2, con.getDenominazione() != null ? con.getDenominazione() : null);
				if(con.getRagioniSociali()!=null) {
					String values3="";
					for(int i=0;i<con.getRagioniSociali().size();i++) {
						if(i!=con.getRagioniSociali().size()-1) {
							values3=values3.concat(con.getRagioniSociali().get(i)).concat(" - ");
						}else {
							values3=values3.concat(con.getRagioniSociali().get(i));
						}
						values.put(3, values3);
					}
				}
				values.put(4, con.getDataPrimaScadenza() != null ? con.getDataPrimaScadenza()+"" : null);
				values.put(5, con.getDataUltimaScadenza() != null ? con.getDataUltimaScadenza()+"" : null);
				values.put(6, con.getCodProcOrg() != null ? con.getCodProcOrg() : null);
				values.put(7, con.getCodProcOrg() != null ? con.getCodProcOrg() : null);
				csv.getRows().add(values);
			}
		}else {
			csv.setHeader(CsvHeaders.CONTRATTI_RICERCA_PARZIALE.getValue());
			for (RicercaContrattoDTO con : contrattiList) {
				csv.setRows(new ArrayList<>());
				HashMap<Integer, String> values = new HashMap<>();
				values.put(0, con.getCig() != null ? con.getCig() : "");
				if(con.getStrutturas()!=null) {
					String values1="";
					for(int i=0;i<con.getStrutturas().size();i++) {
						if(i!=con.getStrutturas().size()-1) {
							values1=values1.concat(con.getStrutturas().get(i).getDenominazione()).concat(" - ");
						}else {
							values1=values1.concat(con.getStrutturas().get(i).getDenominazione());
						}
						values.put(1, values1);
					}
				}
				values.put(2, con.getDenominazione() != null ? con.getDenominazione() : null);
				if(con.getRagioniSociali()!=null) {
					String values3="";
					for(int i=0;i<con.getRagioniSociali().size();i++) {
						if(i!=con.getRagioniSociali().size()-1) {
							values3=values3.concat(con.getRagioniSociali().get(i)).concat(" - ");
						}else {
							values3=values3.concat(con.getRagioniSociali().get(i));
						}
						values.put(3, values3);
					}
				}
				values.put(4, con.getDataPrimaScadenza() != null ? con.getDataPrimaScadenza()+"" : null);
				values.put(5, con.getDataUltimaScadenza() != null ? con.getDataUltimaScadenza()+"" : null);
				csv.getRows().add(values);
			}
		}
		return csvGenerator.generate(csv);
	}
	
	@Override
	public boolean getEsistenzaContratto(String cig) {
		Contratto con=contrattoRepository.findByCig(cig);
		if(con!=null) {
			return true;
		}else {
			return false;
		}
	}


	@Override
	public ContrattoStrutturaDTO getStoricoStruttura(CheckStoricoStruttureFilter filtroStorico) {
		List<ContrattoStruttura> contrattoStrList=contrattoStrutturaRepo.findStruttureChiuse(filtroStorico.getIdContratto(), filtroStorico.getIdRuoloStruttura());
		if(!contrattoStrList.isEmpty()) {
			return dtoMapper.toContrattoStruttura(contrattoStrList.get(0));
		}else {
			return null;
		}
		
	}


	@Override
	public AttoreContrattoDTO getStoricoAttore(CheckStoricoAttoreFilter filtroStorico) {
		List<AttoreContratto> contrattoAttList=null;
		
		if(filtroStorico.getCodiceStruttura()==999 && filtroStorico.getIdRuoloAttore()==3) {
			contrattoAttList=attoreContrattoRepo.findAttoreContrattoChiusi999(filtroStorico.getIdContratto(),filtroStorico.getIdRuoloAttore());
		}else {
			contrattoAttList=attoreContrattoRepo.findAttoreContrattoChiusi(filtroStorico.getIdContratto(),filtroStorico.getIdRuoloAttore());
		}
		
		if(!contrattoAttList.isEmpty()) {
			return dtoMapper.attoreContrattoToAttoreContrattoDTO(contrattoAttList.get(0));
		}else {
			return null;
		}
	}


	@Override
	public CronologiaDTO getStoricoCronologia(CheckStoricoCronologiaFilter filtroStorico) {
		List<Cronologia> cronologieDTOList=cronologiaRepo.findCronologieChiuse(filtroStorico.getIdContratto(), filtroStorico.getIdTipoCronologia());
		if(!cronologieDTOList.isEmpty()) {
			return dtoMapper.cronologiaToCronologiaDTO(cronologieDTOList.get(0));
		}else {
			return null;
		}
	}


	@Override
	public ImportoDTO getStoricoImporto(CheckStoricoImportiFilter filtroStorico) {
		List<Importo> importiDTOList=importoRepo.findImportiChiusi(filtroStorico.getIdContratto(), filtroStorico.getIdTipoImporto());
		if(!importiDTOList.isEmpty()) {
			return dtoMapper.importoToImportoDTO(importiDTOList.get(0));
		}else {
			return null;
		}
	}
}
