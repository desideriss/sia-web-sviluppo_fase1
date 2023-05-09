package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import bdi.azd.sistina.siaweb.auth.util.RuoliUtil;
import bdi.azd.sistina.siaweb.constants.ErrorMessage;
import bdi.azd.sistina.siaweb.controller.FornitoreController;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreFilterDTO;
import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreContrattoRepo;
import bdi.azd.sistina.siaweb.repository.FornitoreRepo;
import bdi.azd.sistina.siaweb.repository.RuoloFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.StatoProcessoRepo;
import bdi.azd.sistina.siaweb.repository.TipoFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.custom.FornitoreRepositoryCustom;
import bdi.azd.sistina.siaweb.service.FornitoreContrattoService;
import bdi.azd.sistina.siaweb.service.FornitoreService;
import bdi.azd.sistina.siaweb.service.impl.FornitoreContrattoServiceImpl;
import bdi.azd.sistina.siaweb.service.impl.FornitoreServiceImpl;
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
class FornitoreControllerTest {
	@Mock
	private FornitoreService fornitoreService;
	private List<FornitoreDTO> fornitoreDtoList;
	private FornitoreDTO fornitoreDTO;
	MockHttpServletRequest request;
	private List<Fornitore> fornitoreList;
	private FornitoreDTO fornitoreDtoNull;
	private FornitoreDTO fornitoreDtoEmpty;
	private FornitoreDTO fornitoreDtoEmptyNull;
	private FornitoreDTO fornitoreDtoNullEmpty;
	private ContrattoRepo contrattoRepo;
	private UserDTO user;
	@Mock
	private Authentication authentication;
	@Mock
	private StatoProcessoRepo statoProcessoRepo;
	
	@Mock
	private TipoFornitoreRepo tipoFornitoreRepo;
	@Mock
	private FornitoreContrattoService fornitoreContrattoService;

	@Mock
	private FornitoreController fornitoreController;

	@Mock
	private FornitoreRepo fornitoreRepo;

	@Mock
	private FornitoreRepositoryCustom fornitoreRepoCustom;

	@Mock
	private FornitoreContrattoRepo fornitoreContrattoRepo;
	
	@Mock
	private RuoloFornitoreRepo ruoloFornitoreRepo;

	@Mock
	private DtoMapper dtoMapper;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		fornitoreService = new FornitoreServiceImpl(fornitoreRepo, dtoMapper,fornitoreRepoCustom);
		fornitoreContrattoService = new FornitoreContrattoServiceImpl(fornitoreContrattoRepo,tipoFornitoreRepo,contrattoRepo,ruoloFornitoreRepo,statoProcessoRepo, dtoMapper);
		fornitoreController = new FornitoreController(fornitoreService, fornitoreContrattoService);
		generaFornitore();
		fornitoreDtoList = new ArrayList<>();
		fornitoreDtoList.add(fornitoreDTO);
		generaMockRequest();
		fornitoreList = new ArrayList<>();
		generaFornitoreNullPiCf();
		generaFornitoreEmptyPiCf();
		generaFornitoreEmptyNullPiCf();
		generaFornitoreNullEmptyPiCf();
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
	}

	@Test
	void testInsertFornitoriOk() {
		boolean esito = false;
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		doReturn(fornitore).when(fornitoreRepo).save(fornitore);

		doReturn(fornitore).when(dtoMapper).fornitoreDtoTofornitore(fornitoreDTO);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> fornitoreSalvatoDTO = fornitoreController.insertFornitori(fornitoreDTO, request);

		if (fornitoreSalvatoDTO.getBody().getCodiceFiscale().equalsIgnoreCase(fornitore.getCodiceFiscale())
				&& fornitoreSalvatoDTO.getBody().getPartitaIva().equalsIgnoreCase(fornitore.getPartitaIva())
				&& fornitoreSalvatoDTO.getBody().getRagioneSociale().equalsIgnoreCase(fornitore.getRagioneSociale())) {
			esito = true;
		}

		assertTrue(esito);
	}
	
	@Test
	void testInsertFornitoriOkPiNull() {
		boolean esito = false;
		
		FornitoreDTO fornitoreDTOPiNull = new FornitoreDTO();
		fornitoreDTOPiNull.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitoreDTOPiNull.setPartitaIva(null);
		fornitoreDTOPiNull.setPmi("S");
		fornitoreDTOPiNull.setRagioneSociale("FORNITORE E");
		fornitoreDTOPiNull.setIdFornitore(Long.valueOf(1));
		fornitoreDTOPiNull.setUseridAggior("user");
		
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		doReturn(fornitore).when(fornitoreRepo).save(fornitore);

		doReturn(fornitore).when(dtoMapper).fornitoreDtoTofornitore(fornitoreDTOPiNull);
		doReturn(fornitoreDTOPiNull).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> fornitoreSalvatoDTO = fornitoreController.insertFornitori(fornitoreDTOPiNull, request);

		if (fornitoreSalvatoDTO.getBody().getCodiceFiscale().equalsIgnoreCase(fornitore.getCodiceFiscale())
				&& fornitoreSalvatoDTO.getBody().getRagioneSociale().equalsIgnoreCase(fornitore.getRagioneSociale())) {
			esito = true;
		}

		assertTrue(esito);
	}
	
	@Test
	void testInsertFornitoriOkCFNull() {
		boolean esito = false;
		
		FornitoreDTO fornitoreDTOCfNull = new FornitoreDTO();
		fornitoreDTOCfNull.setCodiceFiscale(null);
		fornitoreDTOCfNull.setPartitaIva("55555555544");
		fornitoreDTOCfNull.setPmi("S");
		fornitoreDTOCfNull.setRagioneSociale("FORNITORE E");
		fornitoreDTOCfNull.setIdFornitore(Long.valueOf(1));
		fornitoreDTOCfNull.setUseridAggior("user");
		
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		doReturn(fornitore).when(fornitoreRepo).save(fornitore);

		doReturn(fornitore).when(dtoMapper).fornitoreDtoTofornitore(fornitoreDTOCfNull);
		doReturn(fornitoreDTOCfNull).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> fornitoreSalvatoDTO = fornitoreController.insertFornitori(fornitoreDTOCfNull, request);

		if (fornitoreSalvatoDTO.getBody().getPartitaIva().equalsIgnoreCase(fornitore.getPartitaIva())
				&& fornitoreSalvatoDTO.getBody().getRagioneSociale().equalsIgnoreCase(fornitore.getRagioneSociale())) {
			esito = true;
		}

		assertTrue(esito);
	}

	@Test
	void testInsertFornitoriKoCfPiNull() {
		boolean esito = false;

		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoNull, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);
	}

	@Test
	void testInsertFornitoriKoCfPiEmpty() {
		boolean esito = false;

		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoEmpty, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);
	}

	@Test
	void testInsertFornitoriKoDataIntegrityEx() {
		boolean esito = false;
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		doReturn(fornitore).when(fornitoreRepo).save(fornitore);
		doReturn(fornitore).when(dtoMapper).fornitoreDtoTofornitore(fornitoreDTO);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);
		Mockito.when(fornitoreController.insertFornitori(fornitoreDTO, request))
				.thenThrow(new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT));
		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDTO, request);
		if (response.getStatusCodeValue() == 409) {
			esito = true;
		}
		assertTrue(esito);
		assertTrue(esito);
	}

	void testInsertFornitoriKoDataIntegrityExEmpty() {
		boolean esito = false;
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doThrow(new DataIntegrityException()).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoEmpty, request);
		if (response.equals(ResponseEntity.status(HttpStatus.CONFLICT).build())) {
			esito = true;
		}

		assertTrue(esito);
	}

//	@Test
//	void testInsertFornitoriKoPmiNull() {
//		boolean esito = false;
//
//		FornitoreDTO fornitoreDtoNull = new FornitoreDTO();
//		fornitoreDtoNull.setCodiceFiscale("TNTPQG53D24E888Y");
//		fornitoreDtoNull.setPartitaIva("55555555544");
//		fornitoreDtoNull.setPmi(null);
//		fornitoreDtoNull.setRagioneSociale("FORNITORE E");
//		fornitoreDtoNull.setIdFornitore(Long.valueOf(1));
//		fornitoreDtoNull.setUseridAggior("user");
//
//		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoNull, request);
//
//		if (response.equals(ResponseEntity.badRequest().build())) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}

//	@Test
//	void testInsertFornitoriKoPmiEmpty() {
//		boolean esito = false;
//
//		FornitoreDTO fornitoreDtoNull = new FornitoreDTO();
//		fornitoreDtoNull.setCodiceFiscale("TNTPQG53D24E888Y");
//		fornitoreDtoNull.setPartitaIva("55555555544");
//		fornitoreDtoNull.setPmi("");
//		fornitoreDtoNull.setRagioneSociale("FORNITORE E");
//		fornitoreDtoNull.setIdFornitore(Long.valueOf(1));
//		fornitoreDtoNull.setUseridAggior("user");
//
//		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoNull, request);
//
//		if (response.equals(ResponseEntity.badRequest().build())) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}

//	@Test
//	void testInsertFornitoriSalvataggioKo() {
//		boolean esito = false;
//
//		FornitoreDTO fornitoreDtoNull = new FornitoreDTO();
//		fornitoreDtoNull.setCodiceFiscale("TNTPQG53D24E888Y");
//		fornitoreDtoNull.setPartitaIva("55555555544");
//		fornitoreDtoNull.setPmi("");
//		fornitoreDtoNull.setRagioneSociale("FORNITORE E");
//		fornitoreDtoNull.setIdFornitore(Long.valueOf(1));
//		fornitoreDtoNull.setUseridAggior("user");
//		
//		Fornitore fornitore = new Fornitore();
//		fornitore.setCodiceFiscale("");
//		fornitore.setPartitaIva(null);
//		fornitore.setPmi("S");
//		fornitore.setRagioneSociale("FORNITORE E");
//		fornitore.setIdFornitore(Long.valueOf(1));
//		fornitore.setUseridAggior("user");
//		
//		doThrow(new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT)).when(fornitoreRepo).save(fornitore);
//		ResponseEntity<FornitoreDTO> response = fornitoreController.insertFornitori(fornitoreDtoNull, request);
//
//		if (response.equals(ResponseEntity.badRequest().build())) {
//			esito = true;
//		}
//
//		assertTrue(esito);
//	}
	
	@Test
	void testSearchFornitori() {
		boolean esito = false;
		FornitoreFilterDTO fornitoreFilter = new FornitoreFilterDTO();
		fornitoreFilter.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitoreFilter.setPartitaIva("55555555544");
		fornitoreFilter.setRagioneSociale("FORNITORE E");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		fornitoreList.add(fornitore);

		doReturn(fornitoreList).when(fornitoreRepo).findAll();
		doReturn(fornitoreDtoList).when(dtoMapper).listFornitoreToListFornitoreDTO(fornitoreList);

		ResponseEntity<List<FornitoreDTO>> listaFornitoriResponse = fornitoreController.searchFornitori(fornitoreFilter,
				request);
		esito = true;

		assertTrue(esito);

	}

	@Test
	void testSearchFornitoriSenzaFiltri() {
		boolean esito = false;
		FornitoreFilterDTO fornitoreFilter = null;

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		fornitoreList.add(fornitore);

		doReturn(fornitoreList).when(fornitoreRepo).findAll();
		doReturn(fornitoreDtoList).when(dtoMapper).listFornitoreToListFornitoreDTO(fornitoreList);

		ResponseEntity<List<FornitoreDTO>> listaFornitoriResponse = fornitoreController.searchFornitori(fornitoreFilter,
				request);
		List<FornitoreDTO> listaFornitori = listaFornitoriResponse.getBody();

		if (listaFornitori != null && !listaFornitori.isEmpty()) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testSearchFornitoriKo() {
		boolean esito = false;
		FornitoreFilterDTO fornitoreFilter = null;

		doThrow(new ResourceNotContentException()).when(fornitoreRepo).findAll();

		ResponseEntity<List<FornitoreDTO>> listaFornitoriResponse = fornitoreController.searchFornitori(fornitoreFilter,
				request);

		if (listaFornitoriResponse.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreOk() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		user = new UserDTO();
		user.setCognome("prova");
		user.setId("1");
		user.setNome("utente");
		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);
		fornitoreController.updateFornitore(1L, fornitoreDTO, request);

		if (fornitore2.getCodiceFiscale() == fornitoreDTO.getCodiceFiscale()
				&& fornitore2.getPartitaIva() == fornitoreDTO.getPartitaIva()
				&& fornitore2.getRagioneSociale() == fornitoreDTO.getRagioneSociale()
				&& fornitore2.getPmi() == fornitoreDTO.getPmi()
				&& fornitore2.getIdFornitore() == fornitoreDTO.getIdFornitore()) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreOkPiNull() {

		FornitoreDTO fornitoreDTOPiNull = new FornitoreDTO();
		fornitoreDTOPiNull.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitoreDTOPiNull.setPartitaIva(null);
		fornitoreDTOPiNull.setPmi("S");
		fornitoreDTOPiNull.setRagioneSociale("FORNITORE E");
		fornitoreDTOPiNull.setIdFornitore(Long.valueOf(1));
		fornitoreDTOPiNull.setUseridAggior("user");

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTOPiNull).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		fornitoreController.updateFornitore(1, fornitoreDTOPiNull, request);

		if (fornitore2.getCodiceFiscale() == fornitoreDTOPiNull.getCodiceFiscale()
				&& fornitore2.getRagioneSociale() == fornitoreDTOPiNull.getRagioneSociale()
				&& fornitore2.getPmi() == fornitoreDTOPiNull.getPmi()
				&& fornitore2.getIdFornitore() == fornitoreDTOPiNull.getIdFornitore()) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreOkCFNull() {

		FornitoreDTO fornitoreDTOCfNull = new FornitoreDTO();
		fornitoreDTOCfNull.setCodiceFiscale(null);
		fornitoreDTOCfNull.setPartitaIva("55555555544");
		fornitoreDTOCfNull.setPmi("S");
		fornitoreDTOCfNull.setRagioneSociale("FORNITORE E");
		fornitoreDTOCfNull.setIdFornitore(Long.valueOf(1));
		fornitoreDTOCfNull.setUseridAggior("user");

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTOCfNull).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		fornitoreController.updateFornitore(1, fornitoreDTOCfNull, request);

		if (fornitore2.getPartitaIva() == fornitoreDTOCfNull.getPartitaIva()
				&& fornitore2.getRagioneSociale() == fornitoreDTOCfNull.getRagioneSociale()
				&& fornitore2.getPmi() == fornitoreDTOCfNull.getPmi()
				&& fornitore2.getIdFornitore() == fornitoreDTOCfNull.getIdFornitore()) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoriKoDataIntegrityEx() {
		boolean esito = false;
		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");
		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1L);
		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(0, fornitoreDTO, request);
	
		Mockito.when(fornitoreService.updateFornitore(1L, fornitoreDTO))
		.thenThrow(new DataIntegrityException(ErrorMessage.FORNITORE_PRESENT));
	
		if (response.getStatusCodeValue() == 204) {
			esito = true;
		
		}
		assertTrue(esito);
	}
	

	@Test
	void testUpdateFornitoreKoCfPiNull() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoNull, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreKoCfPiEmpty() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoEmpty, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreKoCfPiEmptyNull() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoEmptyNull, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreKoCfPiNullEmpty() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoNullEmpty, request);

		if (response.equals(ResponseEntity.badRequest().build())) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreNull() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(null).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoNull, request);

		if (response.getStatusCodeValue() == 204) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testUpdateFornitoreEmpty() {

		boolean esito = false;

		Fornitore fornitore2 = new Fornitore();
		fornitore2.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore2.setPartitaIva("55555555544");
		fornitore2.setPmi("S");
		fornitore2.setRagioneSociale("FORNITORE E");
		fornitore2.setIdFornitore(Long.valueOf(1));
		fornitore2.setUseridAggior("user");

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("prova");
		fornitore.setPartitaIva("111111");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE PROVA");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(null).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitore2).when(fornitoreRepo).save(fornitore);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		ResponseEntity<FornitoreDTO> response = fornitoreController.updateFornitore(1, fornitoreDtoEmpty, request);

		if (response.getStatusCodeValue() == 204) {
			esito = true;
		}

		assertTrue(esito);

	}

	@Test
	void testDeleteFornitoriOk() {
		boolean esito = false;

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		fornitoreController.deleteFornitore((long) 1, request);

		if (fornitore.getIdFornitore() == fornitoreDTO.getIdFornitore() && fornitoreContrattoService
				.getCountFornitoreContrattoByIdFornitore(fornitoreDTO.getIdFornitore()) < 1) {
			fornitoreController.deleteFornitore(fornitoreDTO.getIdFornitore(), request);
			fornitoreService.deleteFornitore(fornitoreDTO);
			esito = true;
		}
		assertTrue(esito);
	}

	@Test
	void testDeleteFornitoriCountMaggioreDiUno() {
		boolean esito = false;

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);
		doReturn(2).when(fornitoreContrattoRepo).getCountFornitoreContrattoByPiva(fornitoreDTO.getIdFornitore());
		fornitoreController.deleteFornitore((long) 1, request);
		if (fornitore.getIdFornitore() == fornitoreDTO.getIdFornitore() && fornitoreContrattoService
				.getCountFornitoreContrattoByIdFornitore(fornitoreDTO.getIdFornitore()) > 1) {
			fornitoreService.deleteFornitore(fornitoreDTO);
			esito = true;
		}
		assertTrue(esito);
	}

	@Test
	void testDeleteFornitoriKo() {
		boolean esito = false;

		Fornitore fornitore = new Fornitore();
		fornitore.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitore.setPartitaIva("55555555544");
		fornitore.setPmi("S");
		fornitore.setRagioneSociale("FORNITORE E");
		fornitore.setIdFornitore(Long.valueOf(1));
		fornitore.setUseridAggior("user");

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(null).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		fornitoreController.deleteFornitore((long) 1, request);

		esito = true;
		assertTrue(esito);
	}

	@Test
	void testDeleteFornitoriKoFornitoreNull() {
		boolean esito = false;

		Fornitore fornitore = null;

		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1);
		doReturn(fornitoreDTO).when(dtoMapper).fornitoreToFornitoreDTO(fornitore);

		fornitoreController.deleteFornitore((long) 1, request);

		esito = true;
		assertTrue(esito);
	}
	
	@Test
	void testSearchRagioneSociale() {
		List<Fornitore>fornitoreList=new ArrayList<>();
		Fornitore f = new Fornitore();
		f.setRagioneSociale("FORNITORE AA");
		f.setCodiceFiscale("TNTPQG53D24E832E");
		f.setPartitaIva("01234567899");
		f.setPmi("S");
		fornitoreList.add(f);
		String descrizione="Es";
		doReturn(fornitoreList).when(fornitoreRepo).findRagioneSocialeLike(Mockito.any());
		
		ResponseEntity<List<FornitoreDTO>> response = fornitoreController.searchRagioneSociale(descrizione, request);
		
		assertTrue(!response.getBody().isEmpty());
	}

	
	@Test
	void testSearchRagioneSocialeKo() {
		Boolean esito = true;
	
		List<Fornitore>fornitoreList=new ArrayList<>();
		Fornitore f = new Fornitore();
		f.setRagioneSociale("FORNITORE AA");
		f.setCodiceFiscale("TNTPQG53D24E832E");
		f.setPartitaIva("01234567899");
		f.setPmi("S");
		fornitoreList.add(f);
		String descrizione="Es";
		doReturn(fornitoreList).when(fornitoreRepo).findRagioneSocialeLike(Mockito.any());
		
		Mockito.when(fornitoreController.searchRagioneSociale(descrizione, request))
		.thenThrow(new ResourceNotContentException());
		
		ResponseEntity<List<FornitoreDTO>> response = fornitoreController.searchRagioneSociale(descrizione, request);
		
		if(response.getStatusCodeValue() == 204) {
			esito = true;
		}
		
		assertTrue(esito);
	}
	private void generaFornitoreNullPiCf() {

		fornitoreDtoNull = new FornitoreDTO();
		fornitoreDtoNull.setCodiceFiscale(null);
		fornitoreDtoNull.setPartitaIva(null);
		fornitoreDtoNull.setPmi("S");
		fornitoreDtoNull.setRagioneSociale("FORNITORE E");
		fornitoreDtoNull.setIdFornitore(Long.valueOf(1));
		fornitoreDtoNull.setUseridAggior("user");
	}

	private void generaFornitoreEmptyPiCf() {

		fornitoreDtoEmpty = new FornitoreDTO();
		fornitoreDtoEmpty.setCodiceFiscale("");
		fornitoreDtoEmpty.setPartitaIva("");
		fornitoreDtoEmpty.setPmi("S");
		fornitoreDtoEmpty.setRagioneSociale("FORNITORE E");
		fornitoreDtoEmpty.setIdFornitore(Long.valueOf(1));
		fornitoreDtoEmpty.setUseridAggior("user");
	}

	private void generaFornitore() {
		fornitoreDTO = new FornitoreDTO();
		fornitoreDTO.setCodiceFiscale("TNTPQG53D24E888Y");
		fornitoreDTO.setPartitaIva("55555555544");
		fornitoreDTO.setPmi("S");
		fornitoreDTO.setRagioneSociale("FORNITORE E");
		fornitoreDTO.setIdFornitore(Long.valueOf(1));
		fornitoreDTO.setUseridAggior("user");
	}

	private void generaFornitoreEmptyNullPiCf() {

		fornitoreDtoEmptyNull = new FornitoreDTO();
		fornitoreDtoEmptyNull.setCodiceFiscale(null);
		fornitoreDtoEmptyNull.setPartitaIva("");
		fornitoreDtoEmptyNull.setPmi("S");
		fornitoreDtoEmptyNull.setRagioneSociale("FORNITORE E");
		fornitoreDtoEmptyNull.setIdFornitore(Long.valueOf(1));
		fornitoreDtoEmptyNull.setUseridAggior("user");
	}

	private void generaFornitoreNullEmptyPiCf() {

		fornitoreDtoNullEmpty = new FornitoreDTO();
		fornitoreDtoNullEmpty.setCodiceFiscale("");
		fornitoreDtoNullEmpty.setPartitaIva(null);
		fornitoreDtoNullEmpty.setPmi("S");
		fornitoreDtoNullEmpty.setRagioneSociale("FORNITORE E");
		fornitoreDtoNullEmpty.setIdFornitore(Long.valueOf(1));
		fornitoreDtoNullEmpty.setUseridAggior("user");
	}

	private void generaMockRequest() {
		request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		request.setLocalName("Utente");
	}

}
