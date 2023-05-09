package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import bdi.azd.sistina.siaweb.controller.AnagrafeStrutturaController;
import bdi.azd.sistina.siaweb.dto.AnagrafeStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.StrutturaDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.AnagrafeStrutturaRepo;
import bdi.azd.sistina.siaweb.repository.ContrattoStrutturaRepo;
import bdi.azd.sistina.siaweb.service.AnagrafeStrutturaService;
import bdi.azd.sistina.siaweb.service.impl.AnagrafeStrutturaServiceImpl;


@TestInstance(Lifecycle.PER_CLASS)
class AnagrafeStrutturaControllerTest {

	private AnagrafeStrutturaController anagrafeStrutturaController;    
	public AnagrafeStrutturaService anagrafeStrutturaService;



	@Mock
	private AnagrafeStrutturaRepo anagrafeStrutturaRepo;
	
	@Mock
	private AnagrafeStrutturaServiceImpl anagrafeStrutturaServiceImpl;
	
	@Mock
	private ContrattoStrutturaRepo contrattoStrutturaRepo;

	@Mock
	private DtoMapper dtoMapper;
	private MockHttpServletRequest request;


	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
		anagrafeStrutturaService = new AnagrafeStrutturaServiceImpl(anagrafeStrutturaRepo,contrattoStrutturaRepo, dtoMapper);
		anagrafeStrutturaController = new AnagrafeStrutturaController(anagrafeStrutturaService);
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
	void testGetAnagrafeStruttura() {

			List<AnagrafeStrutturaDTO> anagrafeStrutturaList=new ArrayList<>();
			AnagrafeStrutturaDTO asOne= new AnagrafeStrutturaDTO(1L, new BigDecimal(805013),"DIVISIONE SEGRETERIA DIPARTIMENTO CMP", null);
			AnagrafeStrutturaDTO asTwo= new AnagrafeStrutturaDTO(1L, new BigDecimal(802043),"DIVISIONE ARREDI E LAVORO AGILE", null);
			AnagrafeStrutturaDTO asThree= new AnagrafeStrutturaDTO(1L, new BigDecimal(802029),"DIVISIONE GESTIONE PATRIMONIO E CONTRATTI", null);
			anagrafeStrutturaList.add(asOne);
			anagrafeStrutturaList.add(asTwo);
			anagrafeStrutturaList.add(asThree);
			
			BigDecimal flagValidante= new BigDecimal(1);
			when(anagrafeStrutturaService.getAnagrafeStruttura(flagValidante)).thenReturn(anagrafeStrutturaList);
			
			//test
			ResponseEntity<List<AnagrafeStrutturaDTO>> ret=anagrafeStrutturaController.getAnagrafeStruttura(flagValidante, request);

			assertTrue(!ret.getBody().isEmpty());
		

	}
	
//	@Test
//	void testGetAnagrafeStrutturaKo() {
//		boolean esito = false;
//
//		doThrow(new ResourceNotContentException()).when(anagrafeStrutturaRepo).findAll();
//
//		BigDecimal flagValidante= new BigDecimal(1);
//		ResponseEntity<List<AnagrafeStrutturaDTO>> lista = anagrafeStrutturaController.getAnagrafeStruttura(flagValidante,request);
//
//		if (lista.getStatusCodeValue() == 204) {
//			esito = true;
//		}
//		assertTrue(esito);
//
//	}
	
	@Test
	void testGetDenominazioneStruttureLike() {
		List<StrutturaDTO>strutture=new ArrayList<StrutturaDTO>();
		StrutturaDTO s = new StrutturaDTO();
		s.setDenominazione("DIVISIONE SEGRETERIA DIPARTIMENTO CMP");
		s.setCodiceStruttura(new BigDecimal(805013));
		s.setDtFineEvento(null);
		strutture.add(s);
		
		List<AnagrafeStruttura>conStruttList=new ArrayList<>();
		
		AnagrafeStruttura an1=new AnagrafeStruttura();
		an1.setCodiceStruttura(new BigDecimal(2112));
		an1.setDenominazione("prova denom");
		AnagrafeStruttura an2=new AnagrafeStruttura();
		an2.setCodiceStruttura(new BigDecimal(11111));
		an2.setDenominazione("prova denom2");
		conStruttList.add(an1);
		conStruttList.add(an2);
		
		String struttura="prova";
		doReturn(conStruttList).when(anagrafeStrutturaRepo).findStrutturaValidante(Mockito.any(BigDecimal.class),Mockito.anyString());
		doReturn(strutture).when(anagrafeStrutturaServiceImpl).getStrutturaValidanteLike(struttura);
		
		ResponseEntity<List<StrutturaDTO>> ret=anagrafeStrutturaController.getDenominazioneValidanteLike(struttura, request);
		
		assertTrue(!ret.getBody().isEmpty());
	}
	
	
	@Test
	void testGetDenominazioneResponsabileLike() {
		List<StrutturaDTO>strutture=new ArrayList<StrutturaDTO>();
		StrutturaDTO s = new StrutturaDTO();
		s.setDenominazione("DIVISIONE SEGRETERIA DIPARTIMENTO CMP");
		s.setCodiceStruttura(new BigDecimal(805013));
		s.setDtFineEvento(null);
		strutture.add(s);
		
		List<AnagrafeStruttura>conStruttList=new ArrayList<>();
		
		AnagrafeStruttura an1=new AnagrafeStruttura();
		an1.setCodiceStruttura(new BigDecimal(2112));
		an1.setDenominazione("prova denom");
		AnagrafeStruttura an2=new AnagrafeStruttura();
		an2.setCodiceStruttura(new BigDecimal(11111));
		an2.setDenominazione("prova denom2");
		conStruttList.add(an1);
		conStruttList.add(an2);
		
		String struttura="prova";
		doReturn(conStruttList).when(anagrafeStrutturaRepo).findStrutture( Mockito.anyString());
		doReturn(strutture).when(anagrafeStrutturaServiceImpl).getStrutturaResponsabileLike(struttura);
		
		ResponseEntity<List<StrutturaDTO>> ret=anagrafeStrutturaController.getDenominazioneResponsabileLike(struttura, request);
		
		assertTrue(!ret.getBody().isEmpty());
	}
	
	@Test
	void testGetDenominazioneDestinatarieLike() {
		List<StrutturaDTO>strutture=new ArrayList<StrutturaDTO>();
		StrutturaDTO s = new StrutturaDTO();
		s.setDenominazione("DIVISIONE SEGRETERIA DIPARTIMENTO CMP");
		s.setCodiceStruttura(new BigDecimal(805013));
		s.setDtFineEvento(null);
		strutture.add(s);
		
		List<AnagrafeStruttura>conStruttList=new ArrayList<>();
		
		AnagrafeStruttura an1=new AnagrafeStruttura();
		an1.setCodiceStruttura(new BigDecimal(2112));
		an1.setDenominazione("prova denom");
		AnagrafeStruttura an2=new AnagrafeStruttura();
		an2.setCodiceStruttura(new BigDecimal(11111));
		an2.setDenominazione("prova denom2");
		conStruttList.add(an1);
		conStruttList.add(an2);
		
		String struttura="prova";
		doReturn(conStruttList).when(anagrafeStrutturaRepo).findStrutture(  Mockito.anyString());
		doReturn(strutture).when(anagrafeStrutturaServiceImpl).getStrutturaDestinatariaLike(struttura);
		
		ResponseEntity<List<StrutturaDTO>> ret=anagrafeStrutturaController.getDenominazioneDestinatarieLike(struttura, request);
		
		assertTrue(!ret.getBody().isEmpty());
	}
	
}
