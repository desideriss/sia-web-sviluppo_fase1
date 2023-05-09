package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import bdi.azd.sistina.siaweb.controller.AnagrafeStrutturaController;
import bdi.azd.sistina.siaweb.controller.ContrattoController;
import bdi.azd.sistina.siaweb.converter.DettaglioContrattoConverter;
import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoFilterDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoSummaryDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.DettaglioContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.ProceduraDTO;
import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.StatoProcessoDTO;
import bdi.azd.sistina.siaweb.dto.TipoCigDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoContrattoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoRiferimentoDTO;
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
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.RuoloFornitore;
import bdi.azd.sistina.siaweb.entity.RuoloStruttura;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.TipoContratto;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.entity.TipologiaFornitore;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.ldap.LDAPService;
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
import bdi.azd.sistina.siaweb.repository.custom.ContrattoRepositoryCustom;
import bdi.azd.sistina.siaweb.repository.custom.FornitoreRepositoryCustom;
import bdi.azd.sistina.siaweb.service.AnagrafeStrutturaService;
import bdi.azd.sistina.siaweb.service.ContrattoService;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import bdi.azd.sistina.siaweb.service.FornitoreContrattoService;
import bdi.azd.sistina.siaweb.service.FornitoreService;
import bdi.azd.sistina.siaweb.service.ModificaContrattoService;
import bdi.azd.sistina.siaweb.service.StoricoEssenzValService;
import bdi.azd.sistina.siaweb.service.impl.AnagrafeStrutturaServiceImpl;
import bdi.azd.sistina.siaweb.service.impl.ContrattoServiceImpl;
import bdi.azd.sistina.siaweb.service.impl.FornitoreServiceImpl;
import bdi.azd.sistina.siaweb.util.ContrattoUtils;
import bdi.azd.sistina.siaweb.util.CsvGenerator;

@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
class ContrattoControllerTest {
	
	protected MockMvc mvc;
	
	@Mock
	private FornitoreContrattoRepo fornitoreContrattoRepo;
	
	@Mock
	private ContrattoController contrattoController;   
	
	@Mock
	private ContrattoService contrattoService;
	
	@Mock
	private FornitoreService fornitoreService;

	@Mock
	private FornitoreRepo fornitoreRepo;

	@Mock
	private CronologiaRepo cronologiaRepo;

	@Mock
	private ImportoRepo importoRepo;

	@Mock
	private ContrattoStrutturaRepo contrattoStrutturaRepo;

	@Mock
	private AttoreRepo attoreRepo;

	@Mock
	private RiferimentoRepo riferimentoRepo;

	@Mock
	private StatoProcessoRepo statoProcessoRepo;

	@Mock
	private StatoContrattoRepo statoContrattoRepo;

	@Mock
	private ContrattoRepo contrattoRepo;

	@Mock
	private TipoCigRepo tipoCigRepo;

	@Mock
	private TipoContrattoRepo tipoContrattoRepo;

	@Mock
	private TipoSubappaltoRepo tipoSubappaltoRepo;

	@Mock
	private TipoCronologiaRepo tipoCronologiaRepo;

	@Mock
	private RuoloFornitoreRepo ruoloFornitoreRepo;

	@Mock
	private RuoloAttoreRepo ruoloAttoreRepo;

	@Mock
	private AttoreContrattoRepo attoreContrattoRepo;
	
	@Mock
	private IntegrazioneRepo integrazioneRepo;
	
	@Mock
	private ContrattoServiceImpl contrattoServiceImpl;

	@Mock
	private FornitoreServiceImpl fornitoreServiceImpl;

	@Mock
	private ContrattoRepositoryCustom contrattoRepositoryCustom;

	@Mock
	private DtoMapper dtoMapper;

	@Mock
	private DettaglioContrattoConverter dettaglioContrattoConverter;

	@Mock
	private ProceduraRepo proceduraRepo;
	
	@Mock
	private CsvGenerator csvGenerator;
	
	@Mock 
	private FornitoreRepositoryCustom fornitoreRepoCustom;
	
	@Mock 
	private ModificaContrattoService modificaContrattoService;
	
	@Mock 
	private FornitoreContrattoService fornitoreContrattoService;
	
	@Mock 
	private DatiEssenzialiIService datiEssenzialiService;
	
	@Mock 
	private LDAPService ldapService;
	
	@Mock 
	private StoricoEssenzValService storicoEssenzValService;
	
	@Mock 
	@Autowired
	private AnagrafeStrutturaRepo anagrafeStrutturaRepo;
	
	@Mock 
	private AnagrafeStrutturaService anagrafeStrutturaService;
	
	@InjectMocks
	private AnagrafeStrutturaServiceImpl anagrafeStrutturaServiceImpl;
	
	@Mock 
	private AnagrafeStrutturaController anagrafeStrutturaController;
	
	@Mock
	private ContrattoUtils contrattoUtils;

	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
		UserDTO userDTO = new UserDTO();
		userDTO.setNome("Nome");
		userDTO.setCognome("Cognome");
		userDTO.setId("UserID");
		userDTO.setAuthToken("AUTH_TOKEN");
		RuoloDTO ruoloDTO = new RuoloDTO();
		ruoloDTO.setCodice("SISTINA_ADMIN");
		ruoloDTO.setDescrizione("Descrizione");
		ruoloDTO.setStrutture(Arrays.asList(new String[] {"111", "222"}));
		userDTO.setRuoli(Arrays.asList(new RuoloDTO[] {ruoloDTO}));
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getPrincipal()).thenReturn(userDTO);
		SecurityContextHolder.setContext(securityContext);
		fornitoreService    = new FornitoreServiceImpl(fornitoreRepo, dtoMapper, fornitoreRepoCustom);
		contrattoService    = new ContrattoServiceImpl(contrattoRepo, tipoCigRepo, tipoContrattoRepo, statoContrattoRepo, tipoSubappaltoRepo, tipoCronologiaRepo, ruoloFornitoreRepo, ruoloAttoreRepo,attoreContrattoRepo, dettaglioContrattoConverter, null, csvGenerator, dtoMapper, contrattoRepositoryCustom, null, statoProcessoRepo, riferimentoRepo, proceduraRepo, cronologiaRepo, importoRepo, contrattoStrutturaRepo, attoreRepo, fornitoreRepo, fornitoreService, fornitoreContrattoRepo, integrazioneRepo, anagrafeStrutturaRepo,contrattoUtils );
		contrattoController = new ContrattoController(contrattoService, modificaContrattoService, fornitoreContrattoService, datiEssenzialiService, ldapService, storicoEssenzValService);
		
	}

//	@Test
//	void testGetDettaglioContratto() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setServerName("www.example.com");
//		request.setRequestURI("/foo");
//		request.setQueryString("param1=value1&param");
//		request.setRemoteUser("user");
//		request.setRemoteAddr("0.0.0.0.1");
//
//		DettaglioContrattoDTO contrattoDTO= new DettaglioContrattoDTO();
//		contrattoDTO.setCigPadre("prova");
//		contrattoDTO.setCig("15454");
//
//		Contratto contratto= new Contratto();
//		contratto.setCigPadre("prova");
//		contratto.setCig("15454");
//		List<AttoreContratto> listAt = new ArrayList<>();
//		AttoreContratto at = new AttoreContratto();
//		at.setAttore(new Attore());
//		at.setDataFineVal(Timestamp.from(Instant.now()));
//		listAt.add(at);
//		contratto.setAttoreContrattos(listAt);
//
//		String codiceContratto="15454";
//		
//		Contratto contratto2 = this.getContratto();
//
//
//		doReturn(contratto2).when(contrattoRepo).findByCig(codiceContratto);
//		doReturn(contrattoDTO).when(dettaglioContrattoConverter).toDTO(contratto);
//
//		//test
//		ResponseEntity<ContrattoDTO>ret=contrattoController.getDettaglioContratto(request, codiceContratto);
//
//		assertTrue(ret.getStatusCodeValue()==200);
//	}
	
	@Test
	void testVerificaCigOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		Contratto contratto= new Contratto();
		contratto.setCigPadre("prova");
		contratto.setCig("15454");


		String cig="15454";
		


		doReturn(contratto).when(contrattoRepo).findByCig(cig);

		//test
		ResponseEntity<Boolean>ret=contrattoController.verificaCig(request, Mockito.anyString());

		assertTrue(ret.getStatusCodeValue()==200);
	}


	@Test
	void testGetContrattibyStatoProcesso() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<ContrattoSummaryDTO> sumList= new ArrayList<>();
		ContrattoSummaryDTO summary=new ContrattoSummaryDTO();
		summary.setCig("15454");
		summary.setCigPadre("prova");
		summary.setDescrizione("prova");
		sumList.add(summary);

		List<Contratto>conList=new ArrayList<>();
		Contratto con= new Contratto();
		con.setCig("15454");
		con.setCigPadre("prova");
		con.setDescrizioneCrtt("prova");
		conList.add(con);

		StatoProcessoDTO statoPrDTO= new StatoProcessoDTO(3,"Sospeso");
		StatoProcesso statoPr= new StatoProcesso();
		statoPr.setIdStProcesso(3);
		statoPr.setStatoProcesso("Sospeso");

		doReturn(conList).when(contrattoRepo).findAllByStatoProcesso(statoPr);
		doReturn(sumList).when(dettaglioContrattoConverter).toSummaryDTO(conList);
		doReturn(statoPr).when(dtoMapper).statoProcessoDTOToStatoProcesso(statoPrDTO);

		//test
		ResponseEntity<List<ContrattoSummaryDTO>>ret=contrattoController.getContrattiByStatoProcesso(statoPrDTO, request);

		assertTrue(ret.getBody()!=null);
	}

	@Test
	void testSearchContrattiOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<RicercaContrattoDTO> dtoList= new ArrayList<>();
		RicercaContrattoDTO dto=new RicercaContrattoDTO();
		dto.setCig("15454");
		dto.setCigPadre("prova");
		dto.setIdRuoloStr(new BigDecimal(3));
		dtoList.add(dto);

		RicercaContrattoDTO RCDto=new RicercaContrattoDTO();
		RCDto.setCig("15454");
		RCDto.setCigPadre("prova");
		RCDto.setIdRuoloStr(new BigDecimal(3));
		RCDto.setRagioneSociale("ragSoc");

		ContrattoFilterDTO contrattoFilterDTO= new ContrattoFilterDTO();
		contrattoFilterDTO.setCigPadre("prova");
		contrattoFilterDTO.setCig("15454");
		contrattoFilterDTO.setRagioneSocialeFornitore("ragSoc");

		Contratto contratto2= this.getContratto();
		List<Contratto> listCon2 = new ArrayList<>();
		listCon2.add(contratto2);
		doReturn(RCDto).when(dtoMapper).VRicercaContrattoToRicercaContrattoDTO(Mockito.any());
		doReturn(listCon2).when(contrattoRepositoryCustom).findContratti(contrattoFilterDTO);

		//test
		ResponseEntity<Set<RicercaContrattoDTO>>ret=contrattoController.searchContratti(contrattoFilterDTO, request);

		assertTrue(ret.getBody()!=null);
	}

	@Test
	void testDeleteContrattoOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		boolean esito = false;

		Contratto contratto = new Contratto();
		contratto.setCig("1212");
		contratto.setCigPadre("1341");
		contratto.setComparto("prova");

		ContrattoDTO contrattoDTO = new ContrattoDTO();
		contrattoDTO.setCig("1212");
		contrattoDTO.setCigPadre("1341");
		contrattoDTO.setComparto("prova");


		doReturn(contratto).when(contrattoRepo).findByIdContratto(2l);
		doReturn(contrattoDTO).when(dtoMapper).contrattoEntityToContrattoDTO(contratto);

		contrattoController.deleteContratto(2l, request);

		if (contratto.getIdContratto() == contrattoDTO.getIdContratto()) {
			contrattoController.deleteContratto(contrattoDTO.getIdContratto(), request);
			contrattoService.deleteContratto(contrattoDTO.getIdContratto());
			esito = true;
		}
		assertTrue(esito);
	}



	@Test
	void testGetRicercaCigFigli() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<Contratto> contrattoList= new ArrayList<>();
		Contratto contratto= new Contratto();
		contratto.setCigPadre("prova");
		Contratto contrattoTwo= new Contratto();
		contratto.setCigPadre("prova");
		contrattoList.add(contratto);
		contrattoList.add(contrattoTwo);


		RicercaContrattoDTO ricConDto = new RicercaContrattoDTO();
		contratto.setCigPadre("prova");
		contratto.setCig("15454");

		RicercaContrattoDTO ricConDto2 = new RicercaContrattoDTO();
		contratto.setCigPadre("prova");
		contratto.setCig("11111");

		List<RicercaContrattoDTO> ricContrattoDTO= new ArrayList<>();
		ricContrattoDTO.add(ricConDto);
		ricContrattoDTO.add(ricConDto2);

		String cigContratto="prova";

		doReturn(contrattoList).when(contrattoRepo).findAllByCigPadre(cigContratto);
		doReturn(ricContrattoDTO).when(contrattoServiceImpl).getRicercaCigPadre(cigContratto);

		//test
		ResponseEntity<List<RicercaContrattoDTO>>ret=contrattoController.getRicercaCigFigli(request, cigContratto);

		assertTrue(ret.getBody()!=null);
	}
	
	@Test
	void testNuovoContrattoOk() {
		boolean esito = false;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		ContrattoDTO inputContrattoDTO = this.getNuovoContrattoDTO();

		Contratto contratto = this.getContratto();
		contratto.setIdContratto(333);
		contratto.setCig(inputContrattoDTO.getCig());
		Contratto contratto_NULL = null;
		doReturn(contratto_NULL).when(contrattoRepo).findByCig(contratto.getCig());
		
		
		ContrattoDTO contrattoDTO = new ContrattoDTO();
		contrattoDTO.setIdContratto(22L);
		contrattoDTO.setCig("C90000");
		contrattoDTO.setCigPadre("C111");
		
		

		FornitoreDTO fornitoreDTO = inputContrattoDTO.getFornitoreContrattos().get(0).getFornitore();
		Fornitore fornitore = new Fornitore();
		fornitore.setIdFornitore(Long.valueOf(100));
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		doReturn(fornitore).when(fornitoreRepo).save(fornitore);
		doReturn(fornitore).when(dtoMapper).fornitoreDtoTofornitore(fornitoreDTO);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);	
		Procedura proc = new Procedura();
		ProceduraDTO procDTO=new ProceduraDTO();
		contrattoDTO.setProceduraOrg(procDTO);
		doReturn(proc).when(dtoMapper).proceduraDTOToProcedura(Mockito.any(ProceduraDTO.class));
		Mockito.when(proceduraRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(proc));
//		doReturn(Optional.of(proc)).when(proceduraRepo).findById(Mockito.anyLong());
		AnagrafeStruttura anagrafeStruttura = new AnagrafeStruttura();
    	anagrafeStruttura.setIdStruttura(99);
		Optional<AnagrafeStruttura> anagrafeStruttura1 = Optional.of(anagrafeStruttura);
		doReturn(anagrafeStruttura1).when(anagrafeStrutturaRepo).findById(Mockito.anyLong());
		
		Attore attore=new Attore();
		List<AttoreContratto>attoriContratto=new ArrayList<>();
		AttoreContratto attoreContratto= new AttoreContratto();
		attoreContratto.setAttore(attore);
		attoriContratto.add(attoreContratto);
		contratto.setAttoreContrattos(attoriContratto);
		doReturn(attore).when(attoreRepo).findByUserid(Mockito.anyString());
		AttoreDTO attoreDTO=new AttoreDTO();
		List<AttoreContrattoDTO> attoriContrattoDTO=new ArrayList<>();
		AttoreContrattoDTO attoreContrattoDTO= new AttoreContrattoDTO();
		attoreContrattoDTO.setAttore(attoreDTO);
		attoriContrattoDTO.add(attoreContrattoDTO);
		contrattoDTO.setAttoreContrattos(attoriContrattoDTO);
		inputContrattoDTO.setAttoreContrattos(attoriContrattoDTO);
		doReturn(attore).when(dtoMapper).attoreDTOToAttore(Mockito.any(AttoreDTO.class));
		doReturn(attoriContratto).when(dtoMapper).attoreContrattoDTOToAttoreContratto(attoriContrattoDTO);

		doReturn(contrattoDTO).when(contrattoServiceImpl).insertContratto(inputContrattoDTO);	
		doReturn(contratto).when(dtoMapper).contrattoDTOToContrattoEntity(inputContrattoDTO);
		doReturn(contratto).when(dtoMapper).contrattoDTOToContrattoEntity(Mockito.any(ContrattoDTO.class));
		ResponseEntity<ContrattoDTO> outputContrattoDTO = contrattoController.insertContratto(inputContrattoDTO,request);

		assertTrue(outputContrattoDTO.getBody()!= null);
	}

//	@Test
//	void testNuovoContrattoKo() {
//		boolean esito = false;
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setServerName("www.example.com");
//		request.setRequestURI("/foo");
//		request.setQueryString("param1=value1&param");
//		request.setRemoteUser("user");
//		request.setRemoteAddr("0.0.0.0.1");
//		ContrattoDTO inputContrattoDTO = this.getNuovoContrattoDTO();
//
//		Contratto contratto = new Contratto();
//		contratto.setCig(inputContrattoDTO.getCig());
//		
//		ContrattoDTO contrDto = new ContrattoDTO();
//		AttoreContrattoDTO attoreContr = new AttoreContrattoDTO();
//		attoreContr.setAttore(null);
//		List<AttoreContrattoDTO> attContrList = new ArrayList<>();
//		attContrList.add(null);
//		contrDto.setAttoreContrattos(attContrList);
//		FornitoreContrattoDTO fornitoreContrattoDTO = new FornitoreContrattoDTO();
//		FornitoreDTO fornitoreDTO = new FornitoreDTO();
//		fornitoreDTO.setIdFornitore(738);
//		fornitoreDTO.setCodiceFiscale("TNTPQG53D24E888Y");
//		fornitoreDTO.setPmi("S");
//		fornitoreContrattoDTO.setFornitore(fornitoreDTO);
//		contrDto.setFornitoreContrattos(Arrays.asList(fornitoreContrattoDTO));
//		AttoreContrattoDTO attoreContrattoDTO = new AttoreContrattoDTO();
//		attoreContrattoDTO.setIdAttoreContratto(45);
//		contrDto.setAttoreContrattos(Arrays.asList(attoreContrattoDTO));
//		contrDto.setCatMerceologica("prova");
//		contrDto.setCig("123");
//		contrDto.setCigPadre("124");
//		contrDto.setComparto("test");
//		ContrattoStrutturaDTO contrStrutt = new ContrattoStrutturaDTO();
//		contrStrutt.setDataAggior(Timestamp.from(Instant.now()));
//		contrStrutt.setDtInizioEvento(Timestamp.from(Instant.now()));
//		contrStrutt.setIdContrattoStruttura(1L);
//		List<ContrattoStrutturaDTO> contrStruttList = new ArrayList<>();
//		contrStruttList.add(contrStrutt);
//		contrDto.setContrattoStrutturas(contrStruttList);
//		doReturn(contratto).when(contrattoRepo).findByCig(Mockito.any());
//		doReturn(this.getContratto()).when(dtoMapper).contrattoDTOToContrattoEntity(Mockito.any());
//		doReturn(this.getContratto()).when(contrattoRepo).save(this.getContratto());
//		Optional<AnagrafeStruttura> anag;
//		AnagrafeStruttura anag2 =  new AnagrafeStruttura();
//		anag = Optional.of(anag2);
//		doReturn(anag).when(anagrafeStrutturaRepo).findById(Mockito.any());
//		try {
//		ResponseEntity<ContrattoDTO> outputContrattoDTO = contrattoController.insertContratto(inputContrattoDTO);
//		}catch(Exception ex) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}

	private ContrattoDTO getNuovoContrattoDTO() {
		ContrattoDTO contrattoDTO = new ContrattoDTO();
		contrattoDTO.setIdContratto(22L);
		contrattoDTO.setCigPadre("C111");
		contrattoDTO.setCig("cigProva");

		CronologiaDTO cronologiaDTO1 = new CronologiaDTO();
		cronologiaDTO1.setIdCronologia(8);
		CronologiaDTO cronologiaDTO2 = new CronologiaDTO();
		cronologiaDTO2.setIdCronologia(9);
		contrattoDTO.setCronologias(Arrays.asList(cronologiaDTO1, cronologiaDTO2));

		ImportoDTO importoDTO1 = new ImportoDTO();
		importoDTO1.setIdImporto(Long.valueOf(10));
		importoDTO1.setValoreImp(new BigDecimal(100.15));
		ImportoDTO importoDTO2 = new ImportoDTO();
		importoDTO2.setIdImporto(Long.valueOf(11));
		importoDTO2.setValoreImp(new BigDecimal(4500.99));
		contrattoDTO.setImportos(Arrays.asList(importoDTO1, importoDTO2));

		ContrattoStrutturaDTO contrattoStrutturaDTO = new ContrattoStrutturaDTO();
		contrattoStrutturaDTO.setIdContrattoStruttura(22);
		contrattoDTO.setContrattoStrutturas(Arrays.asList(contrattoStrutturaDTO));

		AttoreContrattoDTO attoreContrattoDTO = new AttoreContrattoDTO();
		attoreContrattoDTO.setIdAttoreContratto(45);
		contrattoDTO.setAttoreContrattos(Arrays.asList(attoreContrattoDTO));

		RiferimentoDTO riferimentoDTO1 = new RiferimentoDTO();
		riferimentoDTO1.setIdRiferimento(61);
		RiferimentoDTO riferimentoDTO2 = new RiferimentoDTO();
		riferimentoDTO2.setIdRiferimento(62);
		contrattoDTO.setRiferimentos(Arrays.asList(riferimentoDTO1, riferimentoDTO2));

		FornitoreContrattoDTO fornitoreContrattoDTO = new FornitoreContrattoDTO();
		FornitoreDTO fornitoreDTO = new FornitoreDTO();
		fornitoreDTO.setIdFornitore(Long.valueOf(738));
		fornitoreDTO.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitoreDTO.setPmi("S");
		fornitoreContrattoDTO.setFornitore(fornitoreDTO);
		contrattoDTO.setFornitoreContrattos(Arrays.asList(fornitoreContrattoDTO));
		
		ProceduraDTO proc = new ProceduraDTO();
		proc.setCodProcedura("1");
		proc.setDataAggior(Timestamp.from(Instant.now()));
		proc.setDataTrasmContratto(new Date());
		proc.setDescrizione("");
		proc.setIdProcedura(1L);
		proc.setUseridAggior("User");
		contrattoDTO.setProceduraOrg(proc);
		contrattoDTO.setProceduraRin(proc);

		return contrattoDTO;
	}

	@Test
	void testValidaContrattoOk() {
		boolean esito = false;

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		Contratto contratto = new Contratto();
		contratto.setIdContratto(22L);
		contratto.setCig("1212");
		contratto.setCigPadre("1341");
		contratto.setComparto("prova");

		StatoProcesso statoProcesso = new StatoProcesso();
		statoProcesso.setIdStProcesso(4);

		doReturn(contratto).when(contrattoRepo).findByIdContratto(22L);
		doReturn(statoProcesso).when(statoProcessoRepo).findByIdStProcesso(4);

		contrattoController.validaContrattoI(22L,request);

		if(statoProcesso.getIdStProcesso() == 4) {
			esito = true;
		}

		assertTrue(esito);
	}

	@Test
	void testModificaDatiGeneraliContrattoOk() {
		boolean esito = false;

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		ContrattoDTO contrattoIn = new ContrattoDTO();
		contrattoIn.setCig("11111");
		contrattoIn.setDescrizioneCrtt("Descrizione");
		contrattoIn.setCigPadre("1341");
		contrattoIn.setIdContratto(2l);
		contrattoIn.setNoteStato("Note");

		Contratto contratto = new Contratto();
		contratto.setIdContratto(22L);
		contratto.setCig("11111");
		contratto.setCigPadre("1341");

		doReturn(contratto).when(contrattoRepo).findByCig("11111");

		contrattoController.modificaContratto(contrattoIn,request);

		if(contratto.getCigPadre().equals("1341")) {
			esito = true;
		}

		assertTrue(esito);
	}

	@Test
	void testModificaRiferimentoContrattoOk() {
		boolean esito = false;

		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		RiferimentoDTO riferimentoDTO = new RiferimentoDTO();
		riferimentoDTO.setIdRiferimento(3);
		riferimentoDTO.setCodRiferimento("43");
		riferimentoDTO.setDataVal(new Date());
		riferimentoDTO.setDescrizione("Descrizione Riferimento 1");
		riferimentoDTO.setLink("https://www.google.it");
		riferimentoDTO.setUseridAggior("UnitTest");
		TipoRiferimentoDTO tipoRiferimentoDTO = new TipoRiferimentoDTO();
		tipoRiferimentoDTO.setIdTpRiferimento(99);
		riferimentoDTO.setTipoRiferimento(tipoRiferimentoDTO);

		Riferimento riferimento = new Riferimento();
		riferimento.setIdRiferimento(3);
		riferimento.setCodRiferimento("43");
		
		ContrattoDTO contr = new ContrattoDTO();

		doReturn(riferimento).when(riferimentoRepo).findByIdRiferimento(3);

		contrattoController.modificaContratto(contr,request);

		if(riferimento.getCodRiferimento().equals("43")) {
			esito = true;
		}

		assertTrue(esito);
	}

//	@Test
//	void testModificaIdProceduraContrattoOk() {
//		boolean esito = false;
//
//		ProgrammazioneDettaglioDTO programm = new ProgrammazioneDettaglioDTO();
//		programm.setProceduraOrigine("AA AA");
//		programm.setProceduraRinnovo("BB BB");
//
//		DettaglioContrattoDTO dettaglioContrattoDTO = new DettaglioContrattoDTO();
//		dettaglioContrattoDTO.setCig("11111");
//		dettaglioContrattoDTO.setProgrammazione(programm);
//
//		Procedura procedura = new Procedura();
//		procedura.setCodProcedura("AA");
//		procedura.setDescrizione("AA");
//		procedura.setDataAggior(Timestamp.from(Instant.now()));
//		procedura.setUseridAggior("SISTINA");;
//
//		Contratto contratto = new Contratto();
//		contratto.setProceduraOrg(procedura);
//		contratto.setProceduraRin(procedura);
//
//		doReturn(contratto).when(contrattoRepo).findByCig(dettaglioContrattoDTO.getCig());
//		doReturn(procedura).when(proceduraRepo).save(procedura);
//		doReturn(contratto).when(contrattoRepo).save(contratto);
//
//		ResponseEntity<Void> response = contrattoController.modificaIdProceduraContratto(dettaglioContrattoDTO);
//
//		if (response.getStatusCodeValue() == 200) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
//
//
//	@Test
//	void modificaDateCronologiaContrattoOK() {
//		boolean esito = false;
//		List<CronologiaUpdateFilterDTO> cronologiaDTO = new ArrayList<>();
//		CronologiaUpdateFilterDTO cron1 = new CronologiaUpdateFilterDTO();
//		cron1.setIdContratto(1l);
//		cron1.setMotivazione("Test");
//		cron1.setIdIntegrazione(1L);
//		cron1.setIdTpCronologia(1);
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
//		doReturn(output).when(cronologiaRepo).searchByIdContratto(1,1);
//
//		if(contrattoController.editDateCronologiaContratto(cronologiaDTO, request).getStatusCodeValue() == 200) {
//			esito = true;
//		}
//		assertTrue(esito);
//	}
//
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
//	//		doReturn(null).when(contrattoRepo).findByCig(dettaglioContrattoDTO.getCig());
//	//		doReturn(procedura).when(proceduraRepo).save(procedura);
//	//		doReturn(contratto).when(contrattoRepo).save(contratto);
//	//
//	//		ResponseEntity<Void> response = contrattoController.modificaIdProceduraContratto(dettaglioContrattoDTO);
//	//
//	//		if (response.getStatusCodeValue() == 204) {
//	//			esito = true;
//	//		}
//	//
//	//		assertTrue(esito);
//	//	}
//	//
//
//	@Test
//	void modificaImportoContrattoOK() {
//		boolean esito = false;
//
//		long idContratto = 2;
//		List<ImportoModificaDTO> listImpDTO = new ArrayList<ImportoModificaDTO>();
//		TipoImportoDTO tipImpDTO = new TipoImportoDTO();
//		tipImpDTO.setIdTipImporto(2);
//
//		ImportoModificaDTO impDTO = new ImportoModificaDTO();
//		impDTO.setTipoImporto(tipImpDTO);
//		impDTO.setValoreImp(new BigDecimal(35));
//		listImpDTO.add(impDTO);
//
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
//		ResponseEntity<Void> response = contrattoController.modificaImportoContratto(idContratto,listImpDTO);
//
//		if (response.getStatusCodeValue() == 200) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}

	@Test
	void testGetRupNominativo() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<String> nominativiList= new ArrayList<>();
		String uno="prova1";
		String due="prova2";
		String tre="prova3";
		nominativiList.add(uno);
		nominativiList.add(due);
		nominativiList.add(tre);

		String rupNominativo="nominativo";
		doReturn(nominativiList).when(attoreContrattoRepo).findRupNominativo(Mockito.any());

		ResponseEntity<List<String>> response = contrattoController.getRupNominativo(request, rupNominativo);

		assertTrue(!response.getBody().isEmpty());
	}
	
	
	@Test
	void testGetListUserId() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<String> userIdList= new ArrayList<>();
		String uno="123";
		String due="222";
		String tre="345";
		userIdList.add(uno);
		userIdList.add(due);
		userIdList.add(tre);

		String userId="userid";
		doReturn(userIdList).when(attoreContrattoRepo).findUseridAggior(Mockito.any());

		ResponseEntity<List<String>> response = contrattoController.getListUserId(request, userId);

		assertTrue(!response.getBody().isEmpty());
	}
	
	
	@Test
	void testGetListUserIdKo() {
		boolean esito = false;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");

		List<String> userIdList= new ArrayList<>();
		String uno="123";
		String due="222";
		String tre="345";
		userIdList.add(uno);
		userIdList.add(due);
		userIdList.add(tre);

		String userId="userid";
		doThrow(new ResourceNotContentException()).when(attoreContrattoRepo).findUseridAggior(Mockito.any());

		ResponseEntity<List<String>> response = contrattoController.getListUserId(request, userId);

		if(response.getStatusCodeValue() == 204) {
			esito = true;
		}
		
		assertTrue(esito);
	}
	
//	@Test
//    void getContractByRangeDateTest() throws Exception {
//        mvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/be/contratto/contracts")
//                                .param("page", String.valueOf(0))
//                                .param("limit", String.valueOf(10))
//                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().is2xxSuccessful());
//    }
	
	@Test
	void downlaodContrattiByStatoProcessoTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		ResponseEntity<String> prova = new ResponseEntity<String>(HttpStatus.OK);
		List<ContrattoSummaryDTO> listContrattoSummaryDTOList = new ArrayList<ContrattoSummaryDTO>();
		ContrattoSummaryDTO contrattoSummaryDTO = new ContrattoSummaryDTO();
		contrattoSummaryDTO.setCig("cig");
		contrattoSummaryDTO.setCigPadre("cigPadre");
		contrattoSummaryDTO.setDescrizione("prova");
		contrattoSummaryDTO.setImportoMassimo(new BigDecimal(0.01));
		TipoCigDettaglioDTO tipoCigDett = new TipoCigDettaglioDTO();
		tipoCigDett.setIdTipoCig(1L);
		tipoCigDett.setTipoCig("1");
		contrattoSummaryDTO.setTipoCig(tipoCigDett);
		TipoContrattoDettaglioDTO tipoContrDettDTO = new TipoContrattoDettaglioDTO();
		tipoContrDettDTO.setDescrizioneTipoContratto("");
		tipoContrDettDTO.setIdTipoContratto(1L);
		contrattoSummaryDTO.setTipoContratto(tipoContrDettDTO);
		
		listContrattoSummaryDTOList.add(contrattoSummaryDTO);
		contrattoController.downloadContrattiByStatoProcesso(listContrattoSummaryDTOList, request);
		var body = contrattoServiceImpl.downloadContrattiByStatoProcesso(listContrattoSummaryDTOList);
		prova.ok().body(body);
		
	}
	
	@Test
	void getContractByRangeDateTestOk() {
		Integer limit = 1;
		Integer page = 2;
		Pageable pageable = limit > 0 ? PageRequest.of(page, limit) : Pageable.unpaged();
		contrattoController.getContractByRangeDate(page, limit);
		ResponseEntity.ok().body(contrattoService.getContractByRangeDate(pageable));
	}
	
//	
//	@Test
//	void aggiungiDataTestOk() {
//		boolean esito = false;
//		CronologiaAggiungiDataFilterDTO contrattoIn = new CronologiaAggiungiDataFilterDTO();
//		contrattoIn.setDtInizioEvento(Timestamp.from(Instant.now()));
//		contrattoIn.setFlagPresunta("S");
//		contrattoIn.setIdContratto(181);
//		contrattoIn.setIdTpCronologia(6);
//		contrattoIn.setMotivazione("Test");
//		
//		List<Cronologia> cronList = new ArrayList<Cronologia>();
//		doReturn(cronList).when(cronologiaRepo).searchByIdContratto(contrattoIn.getIdContratto(), contrattoIn.getIdTpCronologia());
//		Contratto contratto = this.getContratto();
//		try {
//		doReturn(contratto).when(contrattoRepo).findByIdContratto(Mockito.anyLong());
//		}
//		catch(Exception ex) {
//			System.out.println(ex);
//		}
//		List<Cronologia> allCronologie = new ArrayList<Cronologia>();
//		Cronologia cronologia = new Cronologia();
//		cronologia.setContratto(contratto);
//		allCronologie.add(cronologia);
//		doReturn(allCronologie).when(cronologiaRepo).findByContrattoIdContratto(contrattoIn.getIdContratto());
//		
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setServerName("www.example.com");
//		request.setRequestURI("/foo");
//		request.setQueryString("param1=value1&param");
//		request.setRemoteUser("user");
//		request.setRemoteAddr("0.0.0.0.1");
//		
//		ResponseEntity<List<Cronologia>> response =  contrattoController.aggiungiData(contrattoIn, request);
//		if (response.getStatusCodeValue()==200) {
//			esito=true;
//		}
//		assertTrue(esito);
//	}
	
	
	private Contratto getContratto() {
		Contratto contratto = new Contratto();
		contratto.setIdContratto(99);
		/*
			CONTRATTO	DESCRIZIONE_CRTT
			CONTRATTO	ID_TP_CONTRATTO
			CONTRATTO	CIG_PADRE
			CONTRATTO	CIG
			CONTRATTO	CATEGORIA_MERCEOLOGICA
			CONTRATTO	MOTIVO_COLL
			CONTRATTO	NOTE_STATO
	    */
		contratto.setDescrizioneCrtt("DescrizioneCrtt");
		TipoContratto tipoContratto = new TipoContratto();
		tipoContratto.setIdTpContratto(1);
		contratto.setTipoContratto(tipoContratto);
		contratto.setCigPadre("CigPadre");
		contratto.setCig("Cig");
		contratto.setCatMerceologica("CatMerceologica");
		contratto.setMotivazioneCollegamento("MotivoColl");
		contratto.setNoteStato("NoteStato");
		/*
			ATTORE_CONTRATTO	ID_ATTORE - RUOLO_ATTORE=1
			ATTORE_CONTRATTO	DEC_DL_PRESENTE
			ATTORE_CONTRATTO	ID_ATTORE
		 */
    	AttoreContratto attoreContratto = new AttoreContratto();
    	attoreContratto.setDecDlPresente("DecDlPresente");
    	Attore attore = new Attore();
    	attore.setIdAttore(99);
    	attoreContratto.setAttore(attore);
    	RuoloAttore ruoloAttore1 = new RuoloAttore();
    	ruoloAttore1.setIdRuoloAttore(1);
    	attoreContratto.setRuoloAttoreBean(ruoloAttore1);
    	RuoloAttore ruoloAttore2 = new RuoloAttore();
    	ruoloAttore2.setIdRuoloAttore(2);
    	attoreContratto.setRuoloAttoreBean(ruoloAttore2);
    	contratto.setAttoreContrattos(Arrays.asList(attoreContratto));
    	/*
    		CONTRATTO_STRUTTURA	ID_STRUTTURA - ID_RUOLO_STR=2
			CONTRATTO_STRUTTURA	ID_STRUTTURA - ID_RUOLO_STR=3
			CONTRATTO_STRUTTURA	ID_STRUTTURA - ID_RUOLO_STR=1
    	 */
    	ContrattoStruttura contrattoStruttura1 = new ContrattoStruttura();
    	AnagrafeStruttura anagrafeStruttura1 = new AnagrafeStruttura();
    	anagrafeStruttura1.setIdStruttura(99);
    	contrattoStruttura1.setAnagrafeStruttura(anagrafeStruttura1);
    	RuoloStruttura ruoloStruttura1 = new RuoloStruttura();
    	ruoloStruttura1.setIdRuoloStr(1);
    	contrattoStruttura1.setRuoloStruttura(ruoloStruttura1);
    	//
    	ContrattoStruttura contrattoStruttura2 = new ContrattoStruttura();
    	AnagrafeStruttura anagrafeStruttura2 = new AnagrafeStruttura();
    	anagrafeStruttura2.setIdStruttura(99);
    	contrattoStruttura2.setAnagrafeStruttura(anagrafeStruttura2);
    	RuoloStruttura ruoloStruttura2 = new RuoloStruttura();
    	ruoloStruttura2.setIdRuoloStr(2);
    	contrattoStruttura2.setRuoloStruttura(ruoloStruttura2);
    	//
    	ContrattoStruttura contrattoStruttura3 = new ContrattoStruttura();
    	AnagrafeStruttura anagrafeStruttura3 = new AnagrafeStruttura();
    	anagrafeStruttura3.setIdStruttura(99);
    	contrattoStruttura3.setAnagrafeStruttura(anagrafeStruttura3);
    	RuoloStruttura ruoloStruttura3 = new RuoloStruttura();
    	ruoloStruttura3.setIdRuoloStr(3);
    	contrattoStruttura3.setRuoloStruttura(ruoloStruttura3);
    	//
    	contratto.setContrattoStrutturas(Arrays.asList(contrattoStruttura1, contrattoStruttura2, contrattoStruttura3));
		/*
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=1
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=2
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=3
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=4
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=5
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=7
			CRONOLOGIA	DATA_EVENTO - ID_TIP_CRONOL=3
		 */
    	Cronologia cronologia1 = new Cronologia();
    	TipoCronologia tipoCronologia1 = new TipoCronologia();
    	tipoCronologia1.setIdTpCronologia(1);
    	cronologia1.setTipoCronologia(tipoCronologia1);
    	cronologia1.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia1.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia2 = new Cronologia();
    	TipoCronologia tipoCronologia2 = new TipoCronologia();
    	tipoCronologia2.setIdTpCronologia(2);
    	cronologia2.setTipoCronologia(tipoCronologia2);
    	cronologia2.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia2.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia3 = new Cronologia();
    	TipoCronologia tipoCronologia3 = new TipoCronologia();
    	tipoCronologia3.setIdTpCronologia(3);
    	cronologia3.setTipoCronologia(tipoCronologia3);
    	cronologia3.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia3.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia4 = new Cronologia();
    	TipoCronologia tipoCronologia4 = new TipoCronologia();
    	tipoCronologia4.setIdTpCronologia(4);
    	cronologia4.setTipoCronologia(tipoCronologia4);
    	cronologia4.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia4.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia5 = new Cronologia();
    	TipoCronologia tipoCronologia5 = new TipoCronologia();
    	tipoCronologia5.setIdTpCronologia(5);
    	cronologia5.setTipoCronologia(tipoCronologia5);
    	cronologia5.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia5.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia6 = new Cronologia();
    	TipoCronologia tipoCronologia6 = new TipoCronologia();
    	tipoCronologia6.setIdTpCronologia(6);
    	cronologia6.setTipoCronologia(tipoCronologia6);
    	cronologia6.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia6.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	Cronologia cronologia7 = new Cronologia();
    	TipoCronologia tipoCronologia7 = new TipoCronologia();
    	tipoCronologia7.setIdTpCronologia(7);
    	cronologia7.setTipoCronologia(tipoCronologia7);
    	cronologia7.setDtInizioEvento(Timestamp.from(Instant.now()));
    	cronologia7.setDtFineEvento(Timestamp.from(Instant.now()));
    	//
    	contratto.setCronologias(Arrays.asList(cronologia1, cronologia2, cronologia3, cronologia4, cronologia5, cronologia6, cronologia7));
		/*
			FORNITORE	PARTITA_IVA
			FORNITORE	CODICE_FISCALE
			FORNITORE	RAGIONE_SOCIALE
		*/
    	Fornitore fornitore = new Fornitore();
    	fornitore.setPartitaIva("PartitaIva");
    	fornitore.setCodiceFiscale("CodiceFiscale");
    	fornitore.setRagioneSociale("RagioneSociale");
		/*
			FORNITORE_CONTRATTO	TIPO_FORNITORE
			FORNITORE_CONTRATTO	RUOLO_FORNITORE
		 */
    	FornitoreContratto fornitoreContratto = new FornitoreContratto();
    	TipologiaFornitore tipologiaFornitore = new TipologiaFornitore();
    	tipologiaFornitore.setIdTpFornitore(1);
    	fornitoreContratto.setTipologiaFornitore(tipologiaFornitore);
    	RuoloFornitore ruoloFornitore = new RuoloFornitore();
    	ruoloFornitore.setIdRlFornitore(1);
    	fornitoreContratto.setRuoloFornitore(ruoloFornitore);
    	fornitoreContratto.setFornitore(fornitore);
    	contratto.setFornitoreContrattos(Arrays.asList(fornitoreContratto));
		/*
			IMPORTO	VALORE_IMP - ID_TIPO_IMPORTO=1
			IMPORTO	VALORE_IMP - ID_TIPO_IMPORTO=6
		 */
    	Importo importo1 = new Importo();
    	importo1.setValoreImp(new BigDecimal(1000.99));
    	TipoImporto tipoImporto1 = new TipoImporto();
    	tipoImporto1.setIdTipImporto(1);
    	importo1.setTipoImporto(tipoImporto1);
    	//
    	Importo importo2 = new Importo();
    	importo2.setValoreImp(new BigDecimal(1000.99));
    	TipoImporto tipoImporto2 = new TipoImporto();
    	tipoImporto2.setIdTipImporto(6);
    	importo2.setTipoImporto(tipoImporto2);
    	//
    	contratto.setImportos(Arrays.asList(importo1, importo2));
    	/*
			RIFERIMENTO	LINK
    	 */
    	Riferimento riferimento = new Riferimento();
    	riferimento.setLink("Link");
    	contratto.setRiferimentos(Arrays.asList(riferimento));
    	
		return contratto;
	}
	
//	@Test 
//	void AggiungiDataTestOk() {
//		CronologiaAggiungiDataFilterDTO contrattoIn = new CronologiaAggiungiDataFilterDTO();
//		contrattoIn.setDataInizioVal(new Date());
//		contrattoIn.setDtFineEvento(new Date());
//		contrattoIn.setDtInizioEvento(new Date());
//		contrattoIn.setFlagPresunta("S");
//		contrattoIn.setIdContratto(1L);
//		contrattoIn.setIdIntegrazione(1L);
//		contrattoIn.setIdTpCronologia(1L);
//		contrattoIn.setMotivazione("Test");
//		List<Cronologia> cronologie = new ArrayList<Cronologia>();
//		Cronologia cron = new Cronologia();
//		Contratto con = new Contratto();
//		con.setIdContratto(1L);
//		cron.setContratto(con);
//		TipoCronologia tc = new TipoCronologia();
//		tc.setIdTpCronologia(2L);
//		cron.setTipoCronologia(tc);
//		
//		Cronologia cron2 = new Cronologia();
//		Contratto con2 = new Contratto();
//		con2.setIdContratto(1L);
//		cron2.setContratto(con);
//		TipoCronologia tc2 = new TipoCronologia();
//		tc2.setIdTpCronologia(3L);
//		cron2.setTipoCronologia(tc2);
//		cronologie.add(cron);
//		cronologie.add(cron2);
//		
//		Contratto contratto = new Contratto();
//		contratto.setIdContratto(1L);
//		contratto.setCronologias(cronologie);
//		doReturn(contratto).when(contrattoRepo).findByIdContratto(1L);
//		doReturn(cronologie).when(cronologiaRepo).searchByIdContratto(1L, 1L);
//		
//		
//		StatoProcesso stProc = new StatoProcesso();
//		stProc.setIdStProcesso(1L);
//		stProc.setStatoProcesso("test");
//		StatoContratto stCon = new StatoContratto();
//		stCon.setIdStContratto(1L);
//		stCon.setStatoContratto("test");
//		doReturn(Optional.of(stCon)).when(statoContrattoRepo).findById(Mockito.anyLong());
//		doReturn(Optional.of(stProc)).when(statoProcessoRepo).findById(Mockito.anyLong());
//		List<Cronologia> response =  contrattoService.aggiungiDateCronologiaContratto(contrattoIn);
//		
//	}
}
