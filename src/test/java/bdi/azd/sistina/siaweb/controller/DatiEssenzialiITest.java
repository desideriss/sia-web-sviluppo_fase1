package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import bdi.azd.sistina.siaweb.controller.ContrattoController;
import bdi.azd.sistina.siaweb.dto.CheckValidazioneIDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
import bdi.azd.sistina.siaweb.entity.Attore;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.DatiEssenzialiI;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Riferimento;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.RuoloFornitore;
import bdi.azd.sistina.siaweb.entity.RuoloStruttura;
import bdi.azd.sistina.siaweb.entity.TipoContratto;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.entity.TipologiaFornitore;
import bdi.azd.sistina.siaweb.repository.ContrattoRepo;
import bdi.azd.sistina.siaweb.repository.DatiEssenzialiIRepo;
import bdi.azd.sistina.siaweb.repository.StoricoEssenzValRepo;
import bdi.azd.sistina.siaweb.service.ContrattoService;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import bdi.azd.sistina.siaweb.service.impl.ContrattoServiceImpl;
import bdi.azd.sistina.siaweb.service.impl.DatiEssenzialiIServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
class DatiEssenzialiITest {
	
	@Mock
	private ContrattoController contrattoController;
	
	private DatiEssenzialiIService datiEssenzialiIService;

	@Mock
	private DatiEssenzialiIRepo datiEssenzialiIRepo;
	
	@Mock
	private StoricoEssenzValRepo storicoEssenzValRepo;
	
	@Mock
	private ContrattoRepo contrattoRepo;
		
	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
	}
	
	/**
	 * Ritorna un'oggetto Contratto con TUTTI i Dati Essenziali I
	 */
	private AutoCloseable closeable;
	
	@BeforeEach
    void initService() {
		closeable = MockitoAnnotations.openMocks(this);
		datiEssenzialiIService = new DatiEssenzialiIServiceImpl(datiEssenzialiIRepo, storicoEssenzValRepo);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
	
	/**
	 * Ritorna un'oggetto Contratto con TUTTI i Dati Essenziali I
	 */

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
	
	public static List<DatiEssenzialiI> getDatiEssenzialiI() {
		DatiEssenzialiI cond1 = new DatiEssenzialiI();
		cond1.setDato("Descrizione Contratto");
		cond1.setFlagEssenziale("S");
		cond1.setTabellaDato("CONTRATTO");
		cond1.setCondizioneRicerca("DESCRIZIONE_CRTT");
		cond1.setAreaDato("AREA_DATO");
		cond1.setDatoConfronto("S");
		//
		DatiEssenzialiI cond2 = new DatiEssenzialiI();
		cond2.setDato("Tipologia Contratto");
		cond2.setFlagEssenziale("S");
		cond2.setTabellaDato("CONTRATTO");
		cond2.setCondizioneRicerca("ID_TP_CONTRATTO");
		cond2.setAreaDato("AREA_DATO");
		cond2.setDatoConfronto("S");
		//
		DatiEssenzialiI cond3 = new DatiEssenzialiI();
		cond3.setDato("CIG Padre");
		cond3.setFlagEssenziale("S");
		cond3.setTabellaDato("CONTRATTO");
		cond3.setCondizioneRicerca("CIG_PADRE");
		cond3.setAreaDato("AREA_DATO");
		cond3.setDatoConfronto("S");
		//
		DatiEssenzialiI cond4 = new DatiEssenzialiI();
		cond4.setDato("CIG/SmartCig");
		cond4.setFlagEssenziale("S");
		cond4.setTabellaDato("CONTRATTO");
		cond4.setCondizioneRicerca("CIG");
		cond4.setAreaDato("AREA_DATO");
		cond4.setDatoConfronto("S");
		//
		DatiEssenzialiI cond5 = new DatiEssenzialiI();
		cond5.setDato("Struttura Responsabile");
		cond5.setFlagEssenziale("S");
		cond5.setTabellaDato("CONTRATTO_STRUTTURA");
		cond5.setCondizioneRicerca("ID_STRUTTURA - ID_RUOLO_STR=2");
		cond5.setAreaDato("AREA_DATO");
		cond5.setDatoConfronto("S");
		//
		DatiEssenzialiI cond6 = new DatiEssenzialiI();
		cond6.setDato("Struttura Destinataria");
		cond6.setFlagEssenziale("S");
		cond6.setTabellaDato("CONTRATTO_STRUTTURA");
		cond6.setCondizioneRicerca("ID_STRUTTURA - ID_RUOLO_STR=3");
		cond6.setAreaDato("AREA_DATO");
		cond6.setDatoConfronto("S");
		//
		DatiEssenzialiI cond7 = new DatiEssenzialiI();
		cond7.setDato("Struttura Validante");
		cond7.setFlagEssenziale("S");
		cond7.setTabellaDato("CONTRATTO_STRUTTURA");
		cond7.setCondizioneRicerca("ID_STRUTTURA - ID_RUOLO_STR=1");
		cond7.setAreaDato("AREA_DATO");
		cond7.setDatoConfronto("S");
		//
		DatiEssenzialiI cond8 = new DatiEssenzialiI();
		cond8.setDato("Tipologia Fornitore");
		cond8.setFlagEssenziale("S");
		cond8.setTabellaDato("FORNITORE_CONTRATTO");
		cond8.setCondizioneRicerca("TIPO_FORNITORE");
		cond8.setAreaDato("AREA_DATO");
		cond8.setDatoConfronto("S");
		//
		DatiEssenzialiI cond9 = new DatiEssenzialiI();
		cond9.setDato("Partita IVA Fornitore");
		cond9.setFlagEssenziale("S");
		cond9.setTabellaDato("FORNITORE");
		cond9.setCondizioneRicerca("PARTITA_IVA");
		cond9.setAreaDato("AREA_DATO");
		cond9.setDatoConfronto("S");
		//
		DatiEssenzialiI cond10 = new DatiEssenzialiI();
		cond10.setDato("Codice Fiscale Fornitore");
		cond10.setFlagEssenziale("S");
		cond10.setTabellaDato("FORNITORE");
		cond10.setCondizioneRicerca("CODICE_FISCALE");
		cond10.setAreaDato("AREA_DATO");
		cond10.setDatoConfronto("S");
		//
		DatiEssenzialiI cond11 = new DatiEssenzialiI();
		cond11.setDato("Ragione Sociale Fornitore");
		cond11.setFlagEssenziale("S");
		cond11.setTabellaDato("FORNITORE");
		cond11.setCondizioneRicerca("RAGIONE_SOCIALE");
		cond11.setAreaDato("AREA_DATO");
		cond11.setDatoConfronto("S");
		//
		DatiEssenzialiI cond12 = new DatiEssenzialiI();
		cond12.setDato("Ruolo Fornitore");
		cond12.setFlagEssenziale("S");
		cond12.setTabellaDato("FORNITORE_CONTRATTO");
		cond12.setCondizioneRicerca("RUOLO_FORNITORE");
		cond12.setAreaDato("AREA_DATO");
		cond12.setDatoConfronto("S");
		//
		DatiEssenzialiI cond13 = new DatiEssenzialiI();
		cond13.setDato("Importo Massimo Contratto");
		cond13.setFlagEssenziale("S");
		cond13.setTabellaDato("IMPORTO");
		cond13.setCondizioneRicerca("VALORE_IMP - ID_TIPO_IMPORTO=1");
		cond13.setAreaDato("AREA_DATO");
		cond13.setDatoConfronto("S");
		//
		DatiEssenzialiI cond14 = new DatiEssenzialiI();
		cond14.setDato("Categoria Merceologica");
		cond14.setFlagEssenziale("N");
		cond14.setTabellaDato("CONTRATTO");
		cond14.setCondizioneRicerca("CATEGORIA_MERCEOLOGICA");
		cond14.setAreaDato("AREA_DATO");
		cond14.setDatoConfronto("S");
		//
		DatiEssenzialiI cond15 = new DatiEssenzialiI();
		cond15.setDato("RUP");
		cond15.setFlagEssenziale("S");
		cond15.setTabellaDato("ATTORE_CONTRATTO");
		cond15.setCondizioneRicerca("ID_ATTORE - RUOLO_ATTORE=1");
		cond15.setAreaDato("AREA_DATO");
		cond15.setDatoConfronto("S");
		//
		DatiEssenzialiI cond16 = new DatiEssenzialiI();
		cond16.setDato("Data Aggiudicazione");
		cond16.setFlagEssenziale("S");
		cond16.setTabellaDato("CRONOLOGIA");
		cond16.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=1");
		cond16.setAreaDato("AREA_DATO");
		cond16.setDatoConfronto("S");
		//
		DatiEssenzialiI cond17 = new DatiEssenzialiI();
		cond17.setDato("Motivo di Collegamento");
		cond17.setFlagEssenziale("S");
		cond17.setTabellaDato("CONTRATTO");
		cond17.setCondizioneRicerca("MOTIVO_COLL");
		cond17.setAreaDato("AREA_DATO");
		cond17.setDatoConfronto("S");
		//
		DatiEssenzialiI cond18 = new DatiEssenzialiI();
		cond18.setDato("Data di Stipula");
		cond18.setFlagEssenziale("S");
		cond18.setTabellaDato("CRONOLOGIA");
		cond18.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=2");
		cond18.setAreaDato("AREA_DATO");
		cond18.setDatoConfronto("S");
		//
		DatiEssenzialiI cond19 = new DatiEssenzialiI();
		cond19.setDato("Decorrenza Contratto");
		cond19.setFlagEssenziale("S");
		cond19.setTabellaDato("CRONOLOGIA");
		cond19.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=3");
		cond19.setAreaDato("AREA_DATO");
		cond19.setDatoConfronto("S");
		//
		DatiEssenzialiI cond20 = new DatiEssenzialiI();
		cond20.setDato("Scadenza Contratto (sz proroghe)");
		cond20.setFlagEssenziale("S");
		cond20.setTabellaDato("CRONOLOGIA");
		cond20.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=4");
		cond20.setAreaDato("AREA_DATO");
		cond20.setDatoConfronto("S");
		//
		DatiEssenzialiI cond21 = new DatiEssenzialiI();
		cond21.setDato("Scadenza Contratto (con proroghe)");
		cond21.setFlagEssenziale("S");
		cond21.setTabellaDato("CRONOLOGIA");
		cond21.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=5");
		cond21.setAreaDato("AREA_DATO");
		cond21.setDatoConfronto("S");
		//
		DatiEssenzialiI cond22 = new DatiEssenzialiI();
		cond22.setDato("Link CAD Stipula");
		cond22.setFlagEssenziale("S");
		cond22.setTabellaDato("RIFERIMENTO");
		cond22.setCondizioneRicerca("LINK");
		cond22.setAreaDato("AREA_DATO");
		cond22.setDatoConfronto("S");
		//
		DatiEssenzialiI cond23 = new DatiEssenzialiI();
		cond23.setDato("Note");
		cond23.setFlagEssenziale("N");
		cond23.setTabellaDato("CONTRATTO");
		cond23.setCondizioneRicerca("NOTE_STATO");
		cond23.setAreaDato("AREA_DATO");
		cond23.setDatoConfronto("S");
		//
		DatiEssenzialiI cond24 = new DatiEssenzialiI();
		cond24.setDato("Data Sospensione");
		cond24.setFlagEssenziale("N");
		cond24.setTabellaDato("CRONOLOGIA");
		cond24.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=7");
		cond24.setAreaDato("AREA_DATO");
		cond24.setDatoConfronto("S");
		//
		DatiEssenzialiI cond25 = new DatiEssenzialiI();
		cond25.setDato("Data Riattivazione");
		cond25.setFlagEssenziale("N");
		cond25.setTabellaDato("CRONOLOGIA");
		cond25.setCondizioneRicerca("DATA_EVENTO - ID_TIP_CRONOL=3");
		cond25.setAreaDato("AREA_DATO");
		cond25.setDatoConfronto("S");
		//
		DatiEssenzialiI cond26 = new DatiEssenzialiI();
		cond26.setDato("Importo Proroga");
		cond26.setFlagEssenziale("N");
		cond26.setTabellaDato("IMPORTO");
		cond26.setCondizioneRicerca("VALORE_IMP - ID_TIPO_IMPORTO=6");
		cond26.setAreaDato("AREA_DATO");
		cond26.setDatoConfronto("S");
		//
		DatiEssenzialiI cond27 = new DatiEssenzialiI();
		cond27.setDato("DEC/DL presenza");
		cond27.setFlagEssenziale("S");
		cond27.setTabellaDato("ATTORE_CONTRATTO");
		cond27.setCondizioneRicerca("DEC_DL_PRESENTE");
		cond27.setAreaDato("AREA_DATO");
		cond27.setDatoConfronto("S");
		//
		DatiEssenzialiI cond28 = new DatiEssenzialiI();
		cond28.setDato("DEC/DL (obbligatorio se DEC/DL è presente)");
		cond28.setFlagEssenziale("N");
		cond28.setTabellaDato("ATTORE_CONTRATTO");
		cond28.setCondizioneRicerca("ID_ATTORE");
		cond28.setAreaDato("AREA_DATO");
		cond28.setDatoConfronto("S");
		
		return Arrays.asList(cond1, cond2, cond3, cond4, cond5, cond6, cond7, cond8, cond9, cond10, cond11, cond12, cond13, cond14, cond15, cond16,
				 cond17, cond18, cond19, cond20, cond21, cond22, cond23, cond24, cond25, cond26, cond27, cond28);
	}
	
	@Test
	void testValidaContrattoFullOk() {

		Contratto contratto = this.getContratto();
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		long countStoricoEssenzVal = 0;
		
		doReturn(countStoricoEssenzVal).when(storicoEssenzValRepo).countByContrattoIdContratto(22L);
		doReturn(contratto).when(contrattoRepo).findByIdContratto(22L);
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findAll();

		CheckValidazioneIDTO isValid = datiEssenzialiIService.checkDatiEssenzialiI(contratto);
		
		assertTrue(isValid.isValido());
		assertTrue(isValid.isStoricoEssenzValido());
		assertNull(isValid.getCampiNonValidi());
	}
	
	@Test
	void testValidaContrattoCond1Ko() {

		Contratto contratto = this.getContratto();
		
		// Imposto la Condizione per NON essere Valido
		contratto.setDescrizioneCrtt(null);
		
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		long countStoricoEssenzVal = 0;
		
		doReturn(countStoricoEssenzVal).when(storicoEssenzValRepo).countByContrattoIdContratto(22L);
		doReturn(contratto).when(contrattoRepo).findByIdContratto(22L);
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findAll();

		CheckValidazioneIDTO isValid = datiEssenzialiIService.checkDatiEssenzialiI(contratto);
		
		assertFalse(isValid.isValido());
		assertNotNull(isValid.getCampiNonValidi());
		assertTrue(isValid.getCampiNonValidi().size() > 0);
	}
	
	@Test
	void testValidaContrattoCond19Ko() {

		Contratto contratto = this.getContratto();
		
		// Imposto la Condizione per NON essere Valido
		List<Cronologia> cronologias = contratto.getCronologias();
		for(Cronologia cronologia : cronologias) {
			if(cronologia.getTipoCronologia().getIdTpCronologia() == 3) {
				cronologia.setDtInizioEvento(null);
			}
		}
		
		List<DatiEssenzialiI> datiEssenzialiI = DatiEssenzialiITest.getDatiEssenzialiI();
		long countStoricoEssenzVal = 0;
		
		doReturn(countStoricoEssenzVal).when(storicoEssenzValRepo).countByContrattoIdContratto(22L);
		doReturn(contratto).when(contrattoRepo).findByIdContratto(22L);
		doReturn(datiEssenzialiI).when(datiEssenzialiIRepo).findAll();

		CheckValidazioneIDTO isValid = datiEssenzialiIService.checkDatiEssenzialiI(contratto);
		
		assertFalse(isValid.isValido());
		assertNotNull(isValid.getCampiNonValidi());
		assertTrue(isValid.getCampiNonValidi().size() > 0);
	}

}
