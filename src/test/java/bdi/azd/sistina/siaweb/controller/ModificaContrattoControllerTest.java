package bdi.azd.sistina.siaweb.controller;
//package bdi.azd.sia.siaweb.controller;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import bdi.azd.sia.siaweb.converter.DettaglioContrattoConverter;
//import bdi.azd.sia.siaweb.dto.ContrattoDTO;
//import bdi.azd.sia.siaweb.dto.CronologiaDTO;
//import bdi.azd.sia.siaweb.dto.FornitoreContrattoModificaDTO;
//import bdi.azd.sia.siaweb.dto.ImportoDTO;
//import bdi.azd.sia.siaweb.dto.IntegrazioneDTO;
//import bdi.azd.sia.siaweb.dto.ModificaContrattoDTO;
//import bdi.azd.sia.siaweb.dto.RiferimentoDTO;
//import bdi.azd.sia.siaweb.dto.TipoImportoDTO;
//import bdi.azd.sia.siaweb.dto.TipoRiferimentoDTO;
//import bdi.azd.sia.siaweb.entity.Contratto;
//import bdi.azd.sia.siaweb.entity.Cronologia;
//import bdi.azd.sia.siaweb.entity.Fornitore;
//import bdi.azd.sia.siaweb.entity.Importo;
//import bdi.azd.sia.siaweb.entity.Integrazione;
//import bdi.azd.sia.siaweb.entity.Procedura;
//import bdi.azd.sia.siaweb.entity.Riferimento;
//import bdi.azd.sia.siaweb.entity.TipoCronologia;
//import bdi.azd.sia.siaweb.entity.TipoImporto;
//import bdi.azd.sia.siaweb.exception.ResourceNotFoundException;
//import bdi.azd.sia.siaweb.mapper.DtoMapper;
//import bdi.azd.sia.siaweb.repository.AttoreContrattoRepo;
//import bdi.azd.sia.siaweb.repository.AttoreRepo;
//import bdi.azd.sia.siaweb.repository.ContrattoRepo;
//import bdi.azd.sia.siaweb.repository.ContrattoStrutturaRepo;
//import bdi.azd.sia.siaweb.repository.CronologiaRepo;
//import bdi.azd.sia.siaweb.repository.FornitoreContrattoRepo;
//import bdi.azd.sia.siaweb.repository.FornitoreRepo;
//import bdi.azd.sia.siaweb.repository.ImportoRepo;
//import bdi.azd.sia.siaweb.repository.IntegrazioneRepo;
//import bdi.azd.sia.siaweb.repository.ProceduraRepo;
//import bdi.azd.sia.siaweb.repository.RiferimentoRepo;
//import bdi.azd.sia.siaweb.repository.RuoloAttoreRepo;
//import bdi.azd.sia.siaweb.repository.RuoloFornitoreRepo;
//import bdi.azd.sia.siaweb.repository.StatoContrattoRepo;
//import bdi.azd.sia.siaweb.repository.StatoProcessoRepo;
//import bdi.azd.sia.siaweb.repository.TipoCigRepo;
//import bdi.azd.sia.siaweb.repository.TipoContrattoRepo;
//import bdi.azd.sia.siaweb.repository.TipoCronologiaRepo;
//import bdi.azd.sia.siaweb.repository.TipoSubappaltoRepo;
//import bdi.azd.sia.siaweb.repository.custom.ContrattoRepositoryCustom;
//import bdi.azd.sia.siaweb.service.ContrattoService;
//import bdi.azd.sia.siaweb.service.FornitoreService;
//import bdi.azd.sia.siaweb.service.ModificaContrattoService;
//import bdi.azd.sia.siaweb.service.impl.ContrattoServiceImpl;
//import bdi.azd.sia.siaweb.service.impl.FornitoreServiceImpl;
//
//
//@TestInstance(Lifecycle.PER_CLASS)
//class ModificaContrattoControllerTest {
//
//	protected MockMvc mvc;
//
//	@Mock
//	private FornitoreContrattoRepo fornitoreContrattoRepo;	
//	@Mock
//	private ContrattoController contrattoController;    
//	@Mock
//	private ContrattoService contrattoService;
//	@Mock
//	private FornitoreService fornitoreService;
//	@Mock
//	private FornitoreRepo fornitoreRepo;
//	@Mock
//	private CronologiaRepo cronologiaRepo;
//	@Mock
//	private ImportoRepo importoRepo;
//	@Mock
//	private ContrattoStrutturaRepo contrattoStrutturaRepo;
//	@Mock
//	private AttoreRepo attoreRepo;
//	@Mock
//	private RiferimentoRepo riferimentoRepo;
//	@Mock
//	private StatoProcessoRepo statoProcessoRepo;
//	@Mock
//	private StatoContrattoRepo statoContrattoRepo;
//	@Mock
//	private ContrattoRepo contrattoRepo;
//	@Mock
//	private TipoCigRepo tipoCigRepo;
//	@Mock
//	private TipoContrattoRepo tipoContrattoRepo;
//	@Mock
//	private TipoSubappaltoRepo tipoSubappaltoRepo;
//	@Mock
//	private TipoCronologiaRepo tipoCronologiaRepo;
//	@Mock
//	private RuoloFornitoreRepo ruoloFornitoreRepo;
//	@Mock
//	private RuoloAttoreRepo ruoloAttoreRepo;
//	@Mock
//	private AttoreContrattoRepo attoreContrattoRepo;
//	@Mock
//	public IntegrazioneRepo integrazioneRepo;
//	@Mock
//	private ContrattoServiceImpl contrattoServiceImpl;
//	@Mock
//	private FornitoreServiceImpl fornitoreServiceImpl;
//	@Mock
//	private ContrattoRepositoryCustom contrattoRepositoryCustom;
//	@Mock
//	private DtoMapper dtoMapper;
//	@Mock
//	private DettaglioContrattoConverter dettaglioContrattoConverter;
//	@Mock
//	private ProceduraRepo proceduraRepo;
//	@Mock
//	private ModificaContrattoController modificaContrattoController;
//	@Mock
//	private ModificaContrattoService modificaContrattoService;
//
//	@BeforeAll
//	void setUp() { 
//		MockitoAnnotations.openMocks(this);
//		fornitoreService    = new FornitoreServiceImpl(fornitoreRepo, dtoMapper, null);
//		contrattoService    = new ContrattoServiceImpl(contrattoRepo, tipoCigRepo, tipoContrattoRepo, statoContrattoRepo, tipoSubappaltoRepo, tipoCronologiaRepo, ruoloFornitoreRepo, ruoloAttoreRepo,attoreContrattoRepo, dettaglioContrattoConverter, null, null, dtoMapper, contrattoRepositoryCustom, null, statoProcessoRepo, riferimentoRepo, proceduraRepo, cronologiaRepo, importoRepo, contrattoStrutturaRepo, attoreRepo, fornitoreRepo, fornitoreService, fornitoreContrattoRepo,integrazioneRepo);
//		contrattoController = new ContrattoController(contrattoService, null, null, null, null);
//	}
//
//	@Test
//	void testModificaDatiGeneraliContrattoOk() {
//		boolean esito = false;
//
//		ContrattoDTO contrattoIn = new ContrattoDTO();
//		contrattoIn.setCig("11111");
//		contrattoIn.setDescrizioneCrtt("Descrizione");
//		contrattoIn.setCigPadre("NEW");
//		contrattoIn.setNoteStato("Note");
//
//		Contratto contratto = new Contratto();
//		contratto.setIdContratto(22L);
//		contratto.setCig("11111");
//		contratto.setCigPadre("1341");
//
//		doReturn(contratto).when(contrattoRepo).findByCig("11111");
//
//		modificaContrattoController.modificaDatiGeneraliContratto(contrattoIn);
//
//		if(contratto.getCigPadre().equals("1341")) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
//
//	@Test
//	void testModificaDatiGeneraliContrattoExNotFound() {
//		boolean esito = false;
//
//		ContrattoDTO contrattoIn = new ContrattoDTO();
//		contrattoIn.setCig("11111");
//		contrattoIn.setDescrizioneCrtt("Descrizione");
//		contrattoIn.setCigPadre("NEW");
//		contrattoIn.setNoteStato("Note");
//
//		Contratto contratto = new Contratto();
//		contratto.setIdContratto(22L);
//		contratto.setCig("11111");
//		contratto.setCigPadre("1341");
//
//		doThrow(new ResourceNotFoundException()).when(modificaContrattoService).modificaDatiGeneraliContratto(Mockito.any());
//
//		modificaContrattoController.modificaDatiGeneraliContratto(contrattoIn);
//	}
//
//
//	@Test
//	void testModificaRiferimentoContrattoOk() {
//		boolean esito = false;
//		List<RiferimentoDTO>riferimenti= new ArrayList<>();
//		RiferimentoDTO riferimentoDTO = new RiferimentoDTO();
//		riferimentoDTO.setIdRiferimento(3);
//		riferimentoDTO.setCodRiferimento("43");
//		riferimentoDTO.setDataVal(new Date());
//		riferimentoDTO.setDescrizione("Descrizione Riferimento 1");
//		riferimentoDTO.setLink("https://www.google.it");
//		riferimentoDTO.setUseridAggior("UnitTest");
////		riferimentoDTO.setContratto(new ContrattoDTO());
//		TipoRiferimentoDTO tipoRiferimentoDTO = new TipoRiferimentoDTO();
//		tipoRiferimentoDTO.setIdTpRiferimento(99);
//		riferimentoDTO.setTipoRiferimento(tipoRiferimentoDTO);
//		riferimenti.add(riferimentoDTO);
//
//		Riferimento riferimento = new Riferimento();
//		riferimento.setIdRiferimento(3);
//		riferimento.setCodRiferimento("43");
//
//		doReturn(riferimento).when(riferimentoRepo).findByIdRiferimento(3);
//
//		modificaContrattoController.modificaRiferimentoContratto(riferimenti);
//
//		if(riferimento.getCodRiferimento().equals("43")) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
//
//	//	@Test
//	//	void testModificaIdProceduraContrattoOk() {
//	//		boolean esito = false;
//	//
//	//		ProgrammazioneDettaglioDTO programm = new ProgrammazioneDettaglioDTO();
//	//		programm.setProceduraOrigine("AA AA");
//	//		programm.setProceduraRinnovo("BB BB");
//	//
//	//		DettaglioContrattoDTO dettaglioContrattoDTO = new DettaglioContrattoDTO();
//	//		dettaglioContrattoDTO.setCig("11111");
//	//		dettaglioContrattoDTO.setProgrammazione(programm);
//	//
//	//		Procedura procedura = new Procedura();
//	//		procedura.setCodProcedura("AA");
//	//		procedura.setDescrizione("AA");
//	//		procedura.setDataAggior(Timestamp.from(Instant.now()));
//	//		procedura.setUseridAggior("SISTINA");;
//	//
//	//		Contratto contratto = new Contratto();
//	//		contratto.setProceduraOrg(procedura);
//	//		contratto.setProceduraRin(procedura);
//	//
//	//		doReturn(contratto).when(contrattoRepo).findByCig(dettaglioContrattoDTO.getCig());
//	//		doReturn(procedura).when(proceduraRepo).save(procedura);
//	//		doReturn(contratto).when(contrattoRepo).save(contratto);
//	//
//	//		ResponseEntity<Void> response = modificaContrattoController.modificaIdProceduraContratto(dettaglioContrattoDTO);
//	//
//	//		if (response.getStatusCodeValue() == 200) {
//	//			esito = true;
//	//		}
//	//
//	//		assertTrue(esito);
//	//	}
//
//
//	@Test
//	void modificaDateCronologiaContrattoOK() {
//		boolean esito = false;
//		List<CronologiaDTO> cronologiaDTO = new ArrayList<>();
//		CronologiaDTO cron1 = new CronologiaDTO();
//		cron1.setMotivazione("Test");
//		cron1.setFlagPresunta("S");
//		cronologiaDTO.add(cron1);
//
//		List<Cronologia> output = new ArrayList<>();
//		Cronologia cron2 = new Cronologia();
//		Contratto contratto = new Contratto();
//		contratto.setIdContratto(12L);
//		Integrazione integrazione = new Integrazione();
//		integrazione.setIdIntegrazione(1L);
//		TipoCronologia tipo = new TipoCronologia();
//		tipo.setIdTpCronologia(1L);
//		cron2.setContratto(contratto);
//		cron2.setIntegrazione(integrazione);
//		cron2.setUseridAggior("user");
//		cron2.setTipoCronologia(tipo);
//		output.add(cron2);
//
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setServerName("www.example.com");
//		request.setRequestURI("/foo");
//		request.setQueryString("param1=value1&param");
//		request.setRemoteUser("user");
//		request.setRemoteAddr("0.0.0.0.1");
//
//		ContrattoDTO contrattoDto = new ContrattoDTO();
//		contrattoDto.setIdContratto(1L);
//
//		doReturn(output).when(cronologiaRepo).searchByIdContratto(1,1);
//		doReturn(output).when(dtoMapper).listCronologiaDTOToListCronologia(Mockito.any());
//		doReturn(output).when(cronologiaRepo).saveAll(output);
//
//		modificaContrattoController.modificaDateContratto(contrattoDto, request);
//	}
//
//
//	//		@Test
//	//		void testModificaIdProceduraContrattoOk() {
//	//			boolean esito = false;
//	//	
//	//			ProgrammazioneDettaglioDTO programm = new ProgrammazioneDettaglioDTO();
//	//			programm.setProceduraOrigine("AA AA");
//	//			programm.setProceduraRinnovo("BB BB");
//	//	
//	//			DettaglioContrattoDTO dettaglioContrattoDTO = new DettaglioContrattoDTO();
//	//			dettaglioContrattoDTO.setCig("11111");
//	//			dettaglioContrattoDTO.setProgrammazione(programm);
//	//	
//	//			Procedura procedura = new Procedura();
//	//			procedura.setCodProcedura("AA");
//	//			procedura.setDescrizione("AA");
//	//			procedura.setDataAggior(Timestamp.from(Instant.now()));
//	//			procedura.setUseridAggior("SISTINA");;
//	//	
//	//			Contratto contratto = new Contratto();
//	//			contratto.setProceduraOrg(procedura);
//	//			contratto.setProceduraRin(procedura);
//	//	
//	//			doReturn(null).when(contrattoRepo).findByCig(dettaglioContrattoDTO.getCig());
//	//			doReturn(procedura).when(proceduraRepo).save(procedura);
//	//			doReturn(contratto).when(contrattoRepo).save(contratto);
//	//	
//	//			ResponseEntity<Void> response = modificaContrattoController.modificaIdProceduraContratto(dettaglioContrattoDTO);
//	//	
//	//			if (response.getStatusCodeValue() == 204) {
//	//				esito = true;
//	//			}
//	//	
//	//			assertTrue(esito);
//	//		}
//
//
//	@Test
//	void modificaImportoContrattoOK() {
//		boolean esito = false;
//
//		long idContratto = 2;
//		List<ImportoDTO> listImpDTO = new ArrayList<ImportoDTO>();
//		TipoImportoDTO tipImpDTO = new TipoImportoDTO();
//		tipImpDTO.setIdTipImporto(2);
//
//		ImportoDTO impDTO = new ImportoDTO();
//		impDTO.setTipoImporto(tipImpDTO);
//		impDTO.setValoreImp(new BigDecimal(35));
//		listImpDTO.add(impDTO);
//
//		ContrattoDTO contrattoDto = new ContrattoDTO();
//		contrattoDto.setIdContratto(1L);
//
//
//		TipoImporto tipImp = new TipoImporto();
//		tipImp.setIdTipImporto(1);
//
//		Contratto c = new Contratto();
//		c.setIdContratto(idContratto);
//		Importo imp = new Importo();;
//		imp.setTipoImporto(tipImp);
//		imp.setValoreImp(new BigDecimal(300));;
//		imp.setDataAggior(Timestamp.from(Instant.now()));
//		imp.setDataInizioVal(Timestamp.from(Instant.now()));	
//		imp.setUseridAggior("SISTINA");
//		imp.setDtInizioEvento(new Date());
//		imp.setContratto(c);
//
//		doReturn(imp).when(importoRepo).findByContrattoIdContrattoAndTipoImportoIdTipImportoAndDataFineVal(idContratto, tipImpDTO.getIdTipImporto(), null);
//
//		ResponseEntity<Void> response = modificaContrattoController.modificaImportoContratto(contrattoDto);
//
//
//	}
//
//	@Test
//	void testInsertFornitoriOk() {
//		boolean esito = false;
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		IntegrazioneDTO integrazioneDTO = new IntegrazioneDTO();
//		Integrazione integrazione = new Integrazione();
//		integrazione.setDescrizione("prova");
//		integrazione.setMotivazione("prova");
//		integrazione.setUseridAggior("user");
//		doReturn(integrazione).when(integrazioneRepo).save(integrazione);
//
//		doReturn(integrazione).when(dtoMapper).integrazioneDtoTointegrazione(integrazioneDTO);
//		doReturn(integrazioneDTO).when(dtoMapper).integrazioneTointegrazioneDto(integrazione);
//
//		ResponseEntity<IntegrazioneDTO> integrazioneSalvataDTO = modificaContrattoController.insertIntegrazione(integrazioneDTO, request);
//
//		if (integrazioneSalvataDTO.getBody().getDescrizione().equalsIgnoreCase(integrazione.getDescrizione())
//				&& integrazioneSalvataDTO.getBody().getMotivazione().equalsIgnoreCase(integrazione.getMotivazione())) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
//
//
//	@Test
//	void modificaIntegrazioneContrattoOK() {
//		boolean esito = false;
//		Procedura proc = new Procedura();
//		proc.setIdProcedura(2);
//		
//		IntegrazioneDTO integrazioneModificaDTO = new IntegrazioneDTO();
//		List<IntegrazioneDTO>intList=new ArrayList<>();
//		Integrazione integrazione = new Integrazione();
//		integrazione.setMotivazione(integrazioneModificaDTO.getMotivazione());
//		integrazione.setDescrizione(integrazioneModificaDTO.getDescrizione());
//		integrazione.setProcedura(proc);
//		intList.add(integrazioneModificaDTO);
//
//		doReturn(proc).when(proceduraRepo).findByCodProcedura(integrazioneModificaDTO.getProcedura().getCodProcedura());
//
//		doReturn(integrazione).when(integrazioneRepo).findByIdIntegrazione(integrazioneModificaDTO.getIdIntegrazione());
//
//		ResponseEntity<Void> response = modificaContrattoController.modificaIntegrazioneContratto(intList);
//
//
//	}
//
//
//
//
//	@Test
//	void testDeleteIntegrazioneOk() {
//		boolean esito = false;
//		MockHttpServletRequest request = new MockHttpServletRequest();
//
//		Integrazione integrazione = new Integrazione();
//		integrazione.setDescrizione("prova");
//		integrazione.setMotivazione("prova");
//		integrazione.setUseridAggior("user");
//
//
//		IntegrazioneDTO integrazioneDTO = new IntegrazioneDTO();
//		integrazioneDTO.setDescrizione("prova");
//		integrazioneDTO.setMotivazione("prova");
//		integrazioneDTO.setUseridAggior("user");
//		doReturn(integrazioneDTO).when(integrazioneRepo).findByIdIntegrazione(1);
//
//		modificaContrattoController.deleteIntegrazione((long) 1, request);
//
//		if (integrazione.getIdIntegrazione() == integrazioneDTO.getIdIntegrazione()) {
//			contrattoController.deleteContratto(integrazioneDTO.getIdIntegrazione(), request);
//			contrattoService.deleteContratto(integrazioneDTO.getIdIntegrazione());
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
//
//
//@Test 
//void modificaDatiGeneraliContrattoOK(){
//	ContrattoDTO contrattoIn = new ContrattoDTO();
//	contrattoIn.setIdContratto(1L);
//	modificaContrattoController.modificaDatiGeneraliContratto(contrattoIn);
//	
//}
//
//
//@Test
//void modificaRiferimentoContrattoOk() {
//	List<RiferimentoDTO> riferimentoDTO =  new ArrayList<>();
//	RiferimentoDTO riferimento = new RiferimentoDTO();
//	riferimento.setCodRiferimento("");
//	ContrattoDTO contr = new ContrattoDTO();
//	contr.setIdContratto(1L);
//	riferimento.setIdRiferimento(1L);
//	riferimentoDTO.add(riferimento);
//	modificaContrattoController.modificaRiferimentoContratto(riferimentoDTO);
//	
//}
//
//@Test 
//void ModificaFornitoreContrattoOk() {
//	List<FornitoreContrattoModificaDTO> fornitoreContrattoIn = new ArrayList<>();
//	FornitoreContrattoModificaDTO fornitore = new FornitoreContrattoModificaDTO();
//	fornitore.setIdFornitoreContratto(1L);
//	fornitore.setRuoloFornitore(1L);
//	fornitore.setTipologiaFornitore(1L);
//	fornitoreContrattoIn.add(fornitore);
//	
//	Fornitore forn = new Fornitore();
//	forn.setIdFornitore(1L);
//	forn.setCodiceFiscale("tttrrr56p41h501e");
//	forn.setPartitaIva("");
//	forn.setPmi("S");
//	doReturn(forn).when(fornitoreRepo).findByIdFornitore(1L);
//}
//}
