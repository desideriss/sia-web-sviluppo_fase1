package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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

import bdi.azd.sistina.siaweb.controller.ProceduraController;
import bdi.azd.sistina.siaweb.dto.ProceduraDTO;
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.exception.BadRequestException;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.mapper.DtoMapper;
import bdi.azd.sistina.siaweb.repository.ProceduraRepo;
import bdi.azd.sistina.siaweb.service.ProceduraService;
import bdi.azd.sistina.siaweb.service.impl.ProceduraServiceImpl;


@TestInstance(Lifecycle.PER_CLASS)
class ProceduraControllerTest {
	@Mock
	private ProceduraController proceduraController;   
	
	@Mock
	private ProceduraService proceduraService;

	@Mock
	private ProceduraRepo proceduraRepo;
	
	@Mock
	private DtoMapper dtoMapper;



	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
		proceduraService=new ProceduraServiceImpl(proceduraRepo,dtoMapper);
		proceduraController = new ProceduraController(proceduraService);
	}
	

	
	
	@Test
	void testGetProceduraByCodiceProcedura() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		

		String codiceProcedura="AA";
		String codiceProcedura2="BB";
		ProceduraDTO proceduraDTO= new ProceduraDTO();
		proceduraDTO.setCodProcedura(codiceProcedura);
		proceduraDTO.setIdProcedura(1l);
		proceduraDTO.setDescrizione("AA");
		ProceduraDTO proceduraDTO2= new ProceduraDTO();
		proceduraDTO2.setCodProcedura(codiceProcedura2);
		proceduraDTO2.setIdProcedura(2l);
		proceduraDTO2.setDescrizione("BB");
		
		Procedura procedura= new Procedura();
		procedura.setCodProcedura(codiceProcedura);
		procedura.setIdProcedura(1l);
		procedura.setDescrizione("AA");
		Procedura procedura2= new Procedura();
		procedura2.setCodProcedura(codiceProcedura2);
		procedura2.setIdProcedura(2l);
		procedura2.setDescrizione("BB");
		
		List<Procedura> proc=new ArrayList<>();
		proc.add(procedura);
		proc.add(procedura2);
		
		List<ProceduraDTO> procDTO=new ArrayList<>();
		procDTO.add(proceduraDTO);
		procDTO.add(proceduraDTO2);
		


		doReturn(procedura).when(proceduraRepo).findByCodProcedura(codiceProcedura);
		doReturn(proceduraDTO).when(dtoMapper).proceduraEntityToProceduraDTO(procedura);
		
		//test
		ResponseEntity<ProceduraDTO>ret=proceduraController.getProceduraByCodiceProcedura(request, codiceProcedura);
		
		assertTrue(ret.getBody()!=null);
	}
	
	@Test
	void testGetProceduraByCodiceProceduraKo() {
		boolean esito = false;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		

		String codiceProcedura="AA";
		ProceduraDTO proceduraDTO= new ProceduraDTO();
		proceduraDTO.setCodProcedura(codiceProcedura);
		proceduraDTO.setIdProcedura(1l);
		proceduraDTO.setDescrizione("AA");
		
		Procedura procedura= new Procedura();
		procedura.setCodProcedura(codiceProcedura);
		procedura.setIdProcedura(1l);
		procedura.setDescrizione("AA");
		
		doThrow(new ResourceNotContentException()).when(proceduraRepo).findByCodProcedura(codiceProcedura);
		ResponseEntity<ProceduraDTO> response = proceduraController.getProceduraByCodiceProcedura(request, codiceProcedura);
		
		if (response.getStatusCodeValue() == 204) {
			esito = true;
		}
		assertTrue(esito);
		}

	
	
	@Test
	void testInsertProcedura() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		ProceduraDTO proceduraDTO= new ProceduraDTO();
		proceduraDTO.setCodProcedura("AB");
		proceduraDTO.setIdProcedura(1l);
		proceduraDTO.setDescrizione("AB");
		
		Procedura procedura= new Procedura();
		procedura.setCodProcedura("AB");
		procedura.setIdProcedura(1l);
		procedura.setDescrizione("AB");
		

		doReturn(procedura).when(dtoMapper).proceduraDTOToProcedura(proceduraDTO);
		doReturn(procedura).when(proceduraRepo).save(procedura);
		doReturn(proceduraDTO).when(dtoMapper).proceduraEntityToProceduraDTO(procedura);
		
		//test
		ResponseEntity<ProceduraDTO>ret=proceduraController.insertProcedura(proceduraDTO, request);
		
		assertTrue(ret.getBody()!=null);
	}
	
	

	@Test
	void testInsertProceduraKoBadRequest() {
		boolean esito = false;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		ProceduraDTO proceduraDTO= new ProceduraDTO();
		proceduraDTO.setCodProcedura("AB");
		proceduraDTO.setIdProcedura(1l);
		proceduraDTO.setDescrizione("AB");
		
		Procedura procedura= new Procedura();
		procedura.setCodProcedura("AB");
		procedura.setIdProcedura(1l);
		procedura.setDescrizione("AB");
		

		doThrow(new BadRequestException()).when(proceduraRepo).save(Mockito.any());
		
		ResponseEntity<ProceduraDTO> response = proceduraController.insertProcedura(proceduraDTO, request);
		
		if (response.getStatusCodeValue() == 400) {
			esito = true;
		}
		assertTrue(esito);
		}
	

	@Test
	void testInsertProceduraKo() {
		boolean esito = false;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.example.com");
		request.setRequestURI("/foo");
		request.setQueryString("param1=value1&param");
		request.setRemoteUser("user");
		request.setRemoteAddr("0.0.0.0.1");
		
		ProceduraDTO proceduraDTO= new ProceduraDTO();
		proceduraDTO.setCodProcedura("AB");
		proceduraDTO.setIdProcedura(1l);
		proceduraDTO.setDescrizione("AB");
		
		Procedura procedura= new Procedura();
		procedura.setCodProcedura("AB");
		procedura.setIdProcedura(1l);
		procedura.setDescrizione("AB");
		

		doThrow(new DataIntegrityException()).when(proceduraRepo).save(Mockito.any());
		
		ResponseEntity<ProceduraDTO> response = proceduraController.insertProcedura(proceduraDTO, request);
		
		if (response.getStatusCodeValue() == 409) {
			esito = true;
		}
		assertTrue(esito);
		}
}
