package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import bdi.azd.sistina.siaweb.controller.TipologicheController;
import bdi.azd.sistina.siaweb.dto.RuoloAttoreDTO;
import bdi.azd.sistina.siaweb.dto.StatoContrattoDTO;
import bdi.azd.sistina.siaweb.dto.TipoCigDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoContrattoDTO;
import bdi.azd.sistina.siaweb.dto.TipoCronologiaDTO;
import bdi.azd.sistina.siaweb.dto.TipoFornitoreDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoImportoDTO;
import bdi.azd.sistina.siaweb.dto.TipoIntegrazioneDTO;
import bdi.azd.sistina.siaweb.dto.TipoRiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.TipoRuoloFornitoreDTO;
import bdi.azd.sistina.siaweb.dto.TipoRuoloStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.TipoSubappaltoDettaglioDTO;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.RuoloAttoreRepo;
import bdi.azd.sistina.siaweb.repository.StatoContrattoRepo;
import bdi.azd.sistina.siaweb.repository.TipoCigRepo;
import bdi.azd.sistina.siaweb.repository.TipoContrattoRepo;
import bdi.azd.sistina.siaweb.repository.TipoCronologiaRepo;
import bdi.azd.sistina.siaweb.repository.TipoFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.TipoImportoRepo;
import bdi.azd.sistina.siaweb.repository.TipoIntegrazioneRepo;
import bdi.azd.sistina.siaweb.repository.TipoRiferimentoRepo;
import bdi.azd.sistina.siaweb.repository.TipoRuoloFornitoreRepo;
import bdi.azd.sistina.siaweb.repository.TipoRuoloStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.TipoSubappaltoRepo;
import bdi.azd.sistina.siaweb.service.TipologicheService;
import bdi.azd.sistina.siaweb.service.impl.TipologicheServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class TipologicheControllerTest {
	
	
	
	private TipologicheController tipologicheController;    
	private TipologicheService tipologicheService;
	
	@Mock
	private TipoSubappaltoRepo tipoSubappaltoRepo; 
	@Mock
	private TipoImportoRepo tipoImportoRepo;
	@Mock
	private TipoCigRepo tipoCigRepo; 
	@Mock
	private TipoFornitoreRepo tipoFornitoreRepo; 
	@Mock
	private TipoRuoloFornitoreRepo tipoRuoloFornitoreRepo;
	@Mock
	private StatoContrattoRepo statoContrattoRepo; 
	@Mock
	private RuoloAttoreRepo ruoloAttoreRepo;
	@Mock
	private TipoContrattoRepo tipoContrattoRepo;
	@Mock
	private TipoCronologiaRepo tipoCronologiaRepo;
	@Mock
	private TipoRiferimentoRepo tipoRiferimentoRepo;
	@Mock
	private TipoIntegrazioneRepo tipoIntegrazioneRepo;
	@Mock
	private TipoRuoloStrutturaRepo  tipoRuoloStrutturaRepo;
	@Mock
	private DtoMapper dtoMapper;
	
	MockHttpServletRequest request;
	
	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);

		tipologicheService = new TipologicheServiceImpl(tipoSubappaltoRepo, tipoImportoRepo, tipoCigRepo, tipoFornitoreRepo, tipoRuoloFornitoreRepo, statoContrattoRepo, ruoloAttoreRepo,
														tipoContrattoRepo,tipoCronologiaRepo,tipoRiferimentoRepo,tipoIntegrazioneRepo,tipoRuoloStrutturaRepo,dtoMapper);
		tipologicheController = new TipologicheController(tipologicheService);
		generaMockRequest();
	}
	
	private void generaMockRequest() {
		request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
	}

	
	@Test
	void testGetStatoContratto() {

		List<StatoContrattoDTO> statoContrattoList = new ArrayList<>();
		StatoContrattoDTO scOne = new StatoContrattoDTO(1, "Attivo");
		StatoContrattoDTO scTwo = new StatoContrattoDTO(2, "Cessato");
		StatoContrattoDTO scThree = new StatoContrattoDTO(3, "Sospeso");
		statoContrattoList.add(scOne);
		statoContrattoList.add(scTwo);
		statoContrattoList.add(scThree);

		when(tipologicheService.getStatoContratto()).thenReturn(statoContrattoList);

		// test
		ResponseEntity<List<StatoContrattoDTO>> ret = tipologicheController.getStatoContratto(request);

		assertTrue(!ret.getBody().isEmpty());

	}
	
	
	
	@Test
	void testGetStatoContrattoKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(statoContrattoRepo).findAll();

		ResponseEntity<List<StatoContrattoDTO>> lista = tipologicheController.getStatoContratto(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoSubappaltoList() {

		List<TipoSubappaltoDettaglioDTO> tipoSubappaltoList = new ArrayList<>();
		TipoSubappaltoDettaglioDTO one = new TipoSubappaltoDettaglioDTO(1, "Non previsto");
		TipoSubappaltoDettaglioDTO two = new TipoSubappaltoDettaglioDTO(2, "Previsto e attivato");
		TipoSubappaltoDettaglioDTO three = new TipoSubappaltoDettaglioDTO(3, "Previsto non attivato");
		tipoSubappaltoList.add(one);
		tipoSubappaltoList.add(two);
		tipoSubappaltoList.add(three);
		when(tipologicheService.getListaTipoSubappalto()).thenReturn(tipoSubappaltoList);
		// test
		ResponseEntity<List<TipoSubappaltoDettaglioDTO>> ret = tipologicheController.getTipoSubappaltoList(request);
		assertTrue(!ret.getBody().isEmpty());
	}
	
	
	@Test
	void testGetTipoSubappaltoListKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoSubappaltoRepo).findAll();

		ResponseEntity<List<TipoSubappaltoDettaglioDTO>> lista = tipologicheController.getTipoSubappaltoList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoImportoList() {

		List<TipoImportoDTO> listDTO = new ArrayList<>();
		TipoImportoDTO one = new TipoImportoDTO();
		TipoImportoDTO two = new TipoImportoDTO();
		TipoImportoDTO three = new TipoImportoDTO();
		TipoImportoDTO four = new TipoImportoDTO();
		TipoImportoDTO five = new TipoImportoDTO();
		one.setIdTipImporto(1);
		one.setTipoImporto("Massimo");
		two.setIdTipImporto(2);
		two.setTipoImporto("Base Asta");
		three.setIdTipImporto(3);
		three.setTipoImporto("Oneri per la Sicurezza");
		four.setIdTipImporto(4);
		four.setTipoImporto("Importi Aggiuntivi Soggetti a Ribasso");
		five.setIdTipImporto(5);
		five.setTipoImporto("Importi Aggiuntivi Soggetti Non a Ribasso\"");
		listDTO.add(one);
		listDTO.add(two);
		listDTO.add(three);
		listDTO.add(four);
		listDTO.add(five);
		
		List<TipoImporto> list= new ArrayList<>();
		TipoImporto uno = new TipoImporto();
		TipoImporto due = new TipoImporto();
		TipoImporto tre = new TipoImporto();
		TipoImporto quattro = new TipoImporto();
		TipoImporto cinque = new TipoImporto();
		uno.setIdTipImporto(1);
		uno.setTipoImporto("Massimo");
		due.setIdTipImporto(2);
		due.setTipoImporto("Base Asta");
		tre.setIdTipImporto(3);
		tre.setTipoImporto("Oneri per la Sicurezza");
		quattro.setIdTipImporto(4);
		quattro.setTipoImporto("Importi Aggiuntivi Soggetti a Ribasso");
		cinque.setIdTipImporto(5);
		cinque.setTipoImporto("Importi Aggiuntivi Soggetti Non a Ribasso\"");
		list.add(uno);
		list.add(due);
		list.add(tre);
		list.add(quattro);
		list.add(cinque);
		
		doReturn(listDTO).when(dtoMapper).listTipoImportoToListTipoImportoDTO(list);
		doReturn(list).when(tipoImportoRepo).findAll();
		// test
		ResponseEntity<List<TipoImportoDTO>> ret = tipologicheController.getTipoImportoList(request);
		assertTrue(!ret.getBody().isEmpty());
	}
	
	@Test
	void testGetTipoImportoListKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoImportoRepo).findAll();

		ResponseEntity<List<TipoImportoDTO>> lista = tipologicheController.getTipoImportoList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoCigList() {

		List<TipoCigDettaglioDTO> list = new ArrayList<>();
		TipoCigDettaglioDTO one = new TipoCigDettaglioDTO(1, "CIG");
		TipoCigDettaglioDTO two = new TipoCigDettaglioDTO(2, "SmartCIG");
		list.add(one);
		list.add(two);
		when(tipologicheService.getListaTipoCig()).thenReturn(list);
		// test
		ResponseEntity<List<TipoCigDettaglioDTO>> ret = tipologicheController.getTipoCigList(request);
		assertTrue(!ret.getBody().isEmpty());
	}
	
	@Test
	void testGetTipoCigListKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoCigRepo).findAll();

		ResponseEntity<List<TipoCigDettaglioDTO>> lista = tipologicheController.getTipoCigList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	
	
	@Test
	void testGetTipoFornitoreList() {

		List<TipoFornitoreDettaglioDTO> list = new ArrayList<>();
		TipoFornitoreDettaglioDTO one = new TipoFornitoreDettaglioDTO(1, "RTI");
		TipoFornitoreDettaglioDTO two = new TipoFornitoreDettaglioDTO(2, "Impresa Singola");
		TipoFornitoreDettaglioDTO three = new TipoFornitoreDettaglioDTO(3, "GEIE");
		TipoFornitoreDettaglioDTO four = new TipoFornitoreDettaglioDTO(4, "Consorzi");
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		when(tipologicheService.getListaTipoFornitore()).thenReturn(list);
		// test
		ResponseEntity<List<TipoFornitoreDettaglioDTO>> ret = tipologicheController.getTipoFornitoreList(request);
		assertTrue(!ret.getBody().isEmpty());
	}
	
	@Test
	void testGetTipoFornitoreListKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoFornitoreRepo).findAll();

		ResponseEntity<List<TipoFornitoreDettaglioDTO>> lista = tipologicheController.getTipoFornitoreList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoRuoloFornitoreList() {

		List<TipoRuoloFornitoreDTO> list = new ArrayList<>();
		TipoRuoloFornitoreDTO one = new TipoRuoloFornitoreDTO(1, "Mandante");
		TipoRuoloFornitoreDTO two = new TipoRuoloFornitoreDTO(2, "Mandatario");
		TipoRuoloFornitoreDTO three = new TipoRuoloFornitoreDTO(3, "Unico fornitore");
		TipoRuoloFornitoreDTO four = new TipoRuoloFornitoreDTO(4, "Consorzio");
		TipoRuoloFornitoreDTO five = new TipoRuoloFornitoreDTO(4, "Consorziata");
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		list.add(five);
		when(tipologicheService.getListaTipoRuoloFornitore()).thenReturn(list);
		// test
		ResponseEntity<List<TipoRuoloFornitoreDTO>> ret = tipologicheController.getTipoRuoloFornitoreList(request);
		assertTrue(!ret.getBody().isEmpty());
	}
	
	@Test
	void testGetTipoRuoloFornitoreListKo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoRuoloFornitoreRepo).findAll();

		ResponseEntity<List<TipoRuoloFornitoreDTO>> lista = tipologicheController.getTipoRuoloFornitoreList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetRuoloAttore() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(ruoloAttoreRepo).findAll();

		ResponseEntity<List<RuoloAttoreDTO>> lista = tipologicheController.getRuoloAttoreList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoContratto() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoContrattoRepo).findAll();

		ResponseEntity<List<TipoContrattoDTO>> lista = tipologicheController.getTipoContrattoList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoCronologia() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoCronologiaRepo).findAll();

		ResponseEntity<List<TipoCronologiaDTO>> lista = tipologicheController.getTipoCronologiaList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoRiferimento() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoRiferimentoRepo).findAll();

		ResponseEntity<List<TipoRiferimentoDTO>> lista = tipologicheController.getTipoRiferimentoList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoIntegrazione() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoIntegrazioneRepo).findAll();

		ResponseEntity<List<TipoIntegrazioneDTO>> lista = tipologicheController.getTipoIntegrazioneList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
	@Test
	void testGetTipoRuoloStrutturaRepo() {
		boolean esito = false;

		doThrow(new ResourceNotContentException()).when(tipoRuoloStrutturaRepo).findAll();

		ResponseEntity<List<TipoRuoloStrutturaDTO>> lista = tipologicheController.getTipoRuoloStrutturaList(request);

		if (lista.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);

	}
	
}
