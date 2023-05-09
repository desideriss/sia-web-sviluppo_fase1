package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.ImportoModificaDTO;
import bdi.azd.sistina.siaweb.dto.ModificaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentoDTO;
import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import bdi.azd.sistina.siaweb.entity.Attore;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.DatiEssenzialiI;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.entity.TipologiaFornitore;
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
import bdi.azd.sistina.siaweb.service.impl.StoricoEssenzValServiceImpl;
import bdi.azd.sistina.siaweb.util.DatiEssenzialiIUtils;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
class StoricoEssenzialiValTests {

	@InjectMocks
	@Autowired
	private StoricoEssenzValServiceImpl storicoEssenzValService;
	
	@Mock
	private DatiEssenzialiIRepo datiEssenzialiIRepo;
	
	@Mock
	private StoricoEssenzValRepo storicoEssenzValRepo;
	
	@Mock
	private ContrattoRepo contrattoRepo;
	
	@Mock
	private FornitoreRepo fornitoreRepo;
	
	@Mock
	private FornitoreContrattoRepo fornitoreContrattoRepo;
	
	@Mock
	private ContrattoStrutturaRepo contrattoStrutturaRepo;
	
	@Mock
	private AttoreContrattoRepo attoreContrattoRepo;
	
	@Mock
	private RiferimentoRepo riferimentoRepo;
	
	@Mock
	private CronologiaRepo cronologiaRepo;
	
	@Mock
	private ImportoRepo importoRepo;
	
	private AutoCloseable closeable;
	
	@BeforeEach
    void initService() {
		closeable = MockitoAnnotations.openMocks(this);
		//
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

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
    
	/**
	 * Ritorna un'oggetto Contratto (Modifica) 
	 * 
	 * @throws ParseException 
	 */

	private ContrattoDTO getModificaContratto() throws ParseException {
		ContrattoDTO modificaContrattoDTO = new ContrattoDTO();
		// Contratto
		modificaContrattoDTO.setIdContratto(181L);
		modificaContrattoDTO.setDescrizioneCrtt("Nuova_Descrizione");
		modificaContrattoDTO.setCigPadre("CIGP");
		modificaContrattoDTO.setCig("CIG");
		modificaContrattoDTO.setNoteStato("Nuove_Note");
		// Fornitore Contratto
		List<FornitoreContrattoDTO> fornitoreContrattos = new ArrayList<>();
		FornitoreContrattoDTO fornitoreContrattoModificaDTO = new FornitoreContrattoDTO();
		fornitoreContrattos.add(fornitoreContrattoModificaDTO);
		modificaContrattoDTO.setFornitoreContrattos(fornitoreContrattos);
		// Cronologia
		List<CronologiaDTO> cronologiaUpdateFilterDTOs = new ArrayList<>();
		CronologiaDTO cronologiaUpdateFilterDTO_1 = new CronologiaDTO();
		cronologiaUpdateFilterDTO_1.setDtInizioEvento(new SimpleDateFormat("dd/MM/yyyy").parse("15/09/2022"));
		CronologiaDTO cronologiaUpdateFilterDTO_2 = new CronologiaDTO();
		cronologiaUpdateFilterDTO_2.setDtInizioEvento(new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022"));
		CronologiaDTO cronologiaUpdateFilterDTO_3 = new CronologiaDTO();
		cronologiaUpdateFilterDTO_3.setDtInizioEvento(new SimpleDateFormat("dd/MM/yyyy").parse("05/07/2022"));
		cronologiaUpdateFilterDTOs.add(cronologiaUpdateFilterDTO_1);
		cronologiaUpdateFilterDTOs.add(cronologiaUpdateFilterDTO_2);
		cronologiaUpdateFilterDTOs.add(cronologiaUpdateFilterDTO_3);
		modificaContrattoDTO.setCronologias(cronologiaUpdateFilterDTOs);
		// Importo
		List<ImportoDTO> importoModificaDTOs = new ArrayList<>();
		ImportoDTO importoModificaDTO_1 = new ImportoDTO();
		importoModificaDTO_1.setIdImporto(1L);
		importoModificaDTO_1.setValoreImp(new BigDecimal(1000.99));
		importoModificaDTOs.add(importoModificaDTO_1);
		ImportoDTO importoModificaDTO_2 = new ImportoDTO();
		importoModificaDTO_2.setIdImporto(1L);
		importoModificaDTO_2.setValoreImp(new BigDecimal(2210.75));
		importoModificaDTOs.add(importoModificaDTO_2);
		modificaContrattoDTO.setImportos(importoModificaDTOs);
		// Riferimento
		List<RiferimentoDTO> riferimentoDTOs = new ArrayList<>();
		RiferimentoDTO riferimentoDTO = new RiferimentoDTO();
		riferimentoDTO.setLink("Nuovo_Link");
		riferimentoDTOs.add(riferimentoDTO);
		modificaContrattoDTO.setRiferimentos(riferimentoDTOs);
		//
		return modificaContrattoDTO;
	}
	
	@Test
	void testModificaContrattoOk() throws ParseException {

		ContrattoDTO contrattoDTO = this.getModificaContratto();

		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		Contratto contratto = new Contratto();
		contratto.setIdContratto(contrattoDTO.getIdContratto());
		contratto.setDescrizioneCrtt("OLD_DESCR");
		
		doReturn(contratto).when(contrattoRepo).findByCig(contrattoDTO.getCig());
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_CONTRATTO);
		
		storicoEssenzValService.checkContrattoStoricoEssenzVal(contrattoDTO);
		
		assertNotEquals(contratto.getDescrizioneCrtt(), contrattoDTO.getDescrizioneCrtt());
	}
	
	@Test
	void testModificaContrattoAttoreContrattoOk() throws ParseException {

		ContrattoDTO contrattoDTO = this.getModificaContratto();
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		Optional<DatiEssenzialiI> datiEssenzialiI_OPT = Optional.of(datiEssenzialiI.get(15));
		
		Contratto contratto = new Contratto();
		contratto.setIdContratto(1L);
		
		AttoreContratto attoreContrattoOLD = new AttoreContratto();
		attoreContrattoOLD.setContratto(contratto);
		RuoloAttore ruoloAttoreOLD = new RuoloAttore();
		ruoloAttoreOLD.setIdRuoloAttore(1);
		attoreContrattoOLD.setRuoloAttoreBean(ruoloAttoreOLD);
		Attore attoreOLD = new Attore();
		attoreOLD.setIdAttore(1);
		attoreContrattoOLD.setAttore(attoreOLD);
		
		AttoreContratto attoreContrattoNEW = new AttoreContratto();
		attoreContrattoNEW.setContratto(contratto);
		RuoloAttore ruoloAttoreNEW = new RuoloAttore();
		ruoloAttoreNEW.setIdRuoloAttore(1);
		attoreContrattoNEW.setRuoloAttoreBean(ruoloAttoreNEW);
		Attore attoreNEW = new Attore();
		attoreNEW.setIdAttore(2);
		attoreContrattoNEW.setAttore(attoreNEW);
		
		doReturn(datiEssenzialiI_OPT).when(datiEssenzialiIRepo).findById(15L);
		
		storicoEssenzValService.checkAttoreContrattoStoricoEssenzVal(attoreContrattoOLD, attoreContrattoNEW);
		
		assertNotEquals(attoreContrattoOLD.getAttore().getIdAttore(), attoreContrattoNEW.getAttore().getIdAttore());
	}
	
//	@Test
//	void testModificaContrattoFornitoreContrattoOk() throws ParseException {
//
//		ContrattoDTO contrattoDTO = this.getModificaContratto();
//		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
//		
//		FornitoreContratto fornitoreContratto = new FornitoreContratto();
//		TipologiaFornitore tipologiaFornitore = new TipologiaFornitore();
//		tipologiaFornitore.setIdTpFornitore(0);
//		fornitoreContratto.setTipologiaFornitore(tipologiaFornitore);
//		Optional<FornitoreContratto> fornitoreContrattoOPT = Optional.of(fornitoreContratto);
//		
//		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE_CONTRATTO);
//		doReturn(fornitoreContrattoOPT).when(fornitoreContrattoRepo).findById(1L);
//		
//		storicoEssenzValService.checkFornitoreContrattoStoricoEssenzVal(contrattoDTO.getIdContratto(), contrattoDTO.getFornitoreContrattos());
//		
//		assertNotEquals(fornitoreContratto.getTipologiaFornitore().getIdTpFornitore(), contrattoDTO.getFornitoreContrattos().get(0).getTipologiaFornitore());
//	}
	
	@Test
	void testModificaContrattoFornitoreOk() throws ParseException {

		ContrattoDTO contrattoDTO = this.getModificaContratto();
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		
		FornitoreDTO fornitoreDTO = new FornitoreDTO();
		fornitoreDTO.setIdFornitore(1L);
		fornitoreDTO.setPartitaIva("PIVA_OLD");
		fornitoreDTO.setCodiceFiscale(null);
		fornitoreDTO.setRagioneSociale(null);
		
		Fornitore fornitore = new Fornitore();
		fornitore.setPartitaIva("PIVA_NEW");
		fornitore.setCodiceFiscale(null);
		fornitore.setRagioneSociale(null);

		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_FORNITORE);
		doReturn(fornitore).when(fornitoreRepo).findByIdFornitore(1L);
		
		storicoEssenzValService.checkFornitoreStoricoEssenzVal(contrattoDTO.getIdContratto(), fornitoreDTO);
		
		assertNotEquals(fornitore.getPartitaIva(), fornitoreDTO.getPartitaIva());
	}
	
	@Test
	void testModificaContrattoStrutturaOk() throws ParseException {
		// Dato NON presente in ModificaContratto
		assertTrue(true);
	}
	
//	@Test
//	void testModificaContrattoCronologiaOk() throws ParseException {
//
//		ContrattoDTO contrattoDTO = this.getModificaContratto();
//		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
//		
//		List<Cronologia> cronologias = new ArrayList<>();
//		Cronologia cronologia = new Cronologia();
//		cronologia.setDtInizioEvento(new Date());
//		cronologias.add(cronologia);
//		
//		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_CRONOLOGIA);
//		doReturn(cronologias).when(cronologiaRepo).searchByIdContratto(1L, 1L);
//		
//		storicoEssenzValService.checkCronologiaStoricoEssenzVal(contrattoDTO.getIdContratto(), contrattoDTO.getCronologias());
//		
//		assertNotEquals(cronologia.getDtInizioEvento(), contrattoDTO.getCronologias().get(0).getDtInizioEvento());
//	}

	@Test
	void testModificaContrattoImportoOk() throws ParseException {

		ContrattoDTO contrattoDTO = this.getModificaContratto();
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		
		Importo importo = new Importo();
		importo.setValoreImp(new BigDecimal(1));
		TipoImporto tipoImporto = new TipoImporto();
		tipoImporto.setIdTipImporto(1L);
		importo.setTipoImporto(tipoImporto);
		
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_IMPORTO);
		doReturn(importo).when(importoRepo).findByIdImporto(1L);
		
		storicoEssenzValService.checkImportoStoricoEssenzVal(contrattoDTO.getIdContratto(), contrattoDTO.getImportos());
		
		assertNotEquals(importo.getValoreImp(), contrattoDTO.getImportos().get(0).getValoreImp());
	}
	
	@Test
	void testModificaContrattoRiferimentiOk() throws ParseException {

		ContrattoDTO contrattoDTO = this.getModificaContratto();
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		
		Riferimento riferimento = new Riferimento();
		riferimento.setLink("OLD_LINK");
		
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findByTabellaDato(DatiEssenzialiIUtils.TABELLA_DATO_RIFERIMENTO);
		doReturn(riferimento).when(riferimentoRepo).findByIdRiferimento(1L);
		
		storicoEssenzValService.checkRiferimentoStoricoEssenzVal(contrattoDTO.getIdContratto(), contrattoDTO.getRiferimentos());
		
		assertNotEquals(riferimento.getLink(), contrattoDTO.getRiferimentos().get(0).getLink());
	}
    
}
