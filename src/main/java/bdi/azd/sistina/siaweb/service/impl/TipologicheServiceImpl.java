package bdi.azd.sistina.siaweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
import bdi.azd.sistina.siaweb.entity.RuoloFornitore;
import bdi.azd.sistina.siaweb.entity.TipoCig;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.entity.TipoSubappalto;
import bdi.azd.sistina.siaweb.entity.TipologiaFornitore;
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
import lombok.extern.slf4j.Slf4j;


// TODO: Auto-generated Javadoc
/**
 * The Class TipologicheServiceImpl.
 */
@Service

/** The Constant log. */

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class TipologicheServiceImpl implements TipologicheService {
	
	/** The tipo subappalto repo. */
	final TipoSubappaltoRepo tipoSubappaltoRepo;
	
	/** The tipo importo repo. */
	final TipoImportoRepo tipoImportoRepo;
	
	/** The tipo cig repo. */
	final TipoCigRepo tipoCigRepo;
	
	/** The tipo fornitore repo. */
	final TipoFornitoreRepo tipoFornitoreRepo;
	
	/** The tipo ruolo fornitore repo. */
	final TipoRuoloFornitoreRepo tipoRuoloFornitoreRepo;
	
	/** The stato contratto repo. */
	final StatoContrattoRepo statoContrattoRepo;
	
	/** The ruolo attore repo. */
	final RuoloAttoreRepo ruoloAttoreRepo;
	
	/** The tipo contratto repo. */
	final TipoContrattoRepo tipoContrattoRepo;
	
	/** The tipo cronologia repo. */
	final TipoCronologiaRepo tipoCronologiaRepo;
	
	/** The tipo riferimento repo. */
	final TipoRiferimentoRepo tipoRiferimentoRepo;
	
	/** The tipo integrazione repo. */
	final TipoIntegrazioneRepo tipoIntegrazioneRepo;
	
	/** The tipo integrazione repo. */
	final TipoRuoloStrutturaRepo tipoRuoloStrutturaRepo;

	/** The dto mapper. */
	final DtoMapper dtoMapper;

	/**
	 * Instantiates a new tipologiche service impl.
	 *
	 * @param tipoSubappaltoRepo the tipo subappalto repo
	 * @param tipoImportoRepo the tipo importo repo
	 * @param tipoCigRepo the tipo cig repo
	 * @param tipoFornitoreRepo the tipo fornitore repo
	 * @param tipoRuoloFornitoreRepo the tipo ruolo fornitore repo
	 * @param statoContrattoRepo the stato contratto repo
	 * @param dtoMapper the dto mapper
	 */
	public TipologicheServiceImpl(TipoSubappaltoRepo tipoSubappaltoRepo, TipoImportoRepo tipoImportoRepo,
			TipoCigRepo tipoCigRepo, TipoFornitoreRepo tipoFornitoreRepo, TipoRuoloFornitoreRepo tipoRuoloFornitoreRepo,
			StatoContrattoRepo statoContrattoRepo, RuoloAttoreRepo ruoloAttoreRepo, TipoContrattoRepo tipoContrattoRepo, 
			TipoCronologiaRepo tipoCronologiaRepo,TipoRiferimentoRepo tipoRiferimentoRepo,TipoIntegrazioneRepo tipoIntegrazioneRepo,
			TipoRuoloStrutturaRepo tipoRuoloStrutturaRepo,DtoMapper dtoMapper) {
		super();
		this.tipoSubappaltoRepo = tipoSubappaltoRepo;
		this.tipoImportoRepo = tipoImportoRepo;
		this.tipoCigRepo = tipoCigRepo;
		this.tipoFornitoreRepo = tipoFornitoreRepo;
		this.tipoRuoloFornitoreRepo = tipoRuoloFornitoreRepo;
		this.statoContrattoRepo = statoContrattoRepo;
		this.ruoloAttoreRepo = ruoloAttoreRepo;
		this.tipoContrattoRepo = tipoContrattoRepo;
		this.tipoCronologiaRepo = tipoCronologiaRepo;
		this.tipoRiferimentoRepo = tipoRiferimentoRepo;
		this.tipoIntegrazioneRepo = tipoIntegrazioneRepo;
		this.tipoRuoloStrutturaRepo = tipoRuoloStrutturaRepo;
		this.dtoMapper = dtoMapper;
	}


	/**
	 * Metodo che restituisce la lista completa dei fornitori.
	 *
	 * @return the lista tipo subappalto
	 */
	@Override
	public List<TipoSubappaltoDettaglioDTO> getListaTipoSubappalto() {
		List<TipoSubappalto> lTipoSubappalto = tipoSubappaltoRepo.findAll();
		return dtoMapper.listTipoSubappaltoToListTipoSubappaltoDTO(lTipoSubappalto);
	}


	


	/**
	 *  Metodo che restituisce la lista tipo importo.
	 *
	 * @return the lista tipo importo
	 */
	@Override
	public List<TipoImportoDTO> getListaTipoImporto() {
		List<TipoImporto> lTipoImporto = tipoImportoRepo.findAll();
		return dtoMapper.listTipoImportoToListTipoImportoDTO(lTipoImporto);
	}


	/**
	 *  Metodo che restituisce la lista tipo cig.
	 *
	 * @return the lista tipo cig
	 */
	@Override
	public List<TipoCigDettaglioDTO> getListaTipoCig() {
		List<TipoCig> lTipoCig = tipoCigRepo.findAll();
		return dtoMapper.listTipoCigToListTipoCigDTO(lTipoCig);
	}


	/**
	 *  Metodo che restituisce la lista tipo fornitore.
	 *
	 * @return the lista tipo fornitore
	 */
	@Override
	public List<TipoFornitoreDettaglioDTO> getListaTipoFornitore() {
		List<TipologiaFornitore> lTipoFornitore = tipoFornitoreRepo.findAll();
		return dtoMapper.listTipoFornitoreToListTipoFornitoreDTO(lTipoFornitore);
	}


	/**
	 *  Metodo che restituisce la lista tipo ruolo fornitore.
	 *
	 * @return the lista tipo ruolo fornitore
	 */
	@Override
	public List<TipoRuoloFornitoreDTO> getListaTipoRuoloFornitore() {
		List<RuoloFornitore> lTipoRuoloFornitore = tipoRuoloFornitoreRepo.findAll();
		return dtoMapper.listTipoRuoloFornitoreToListTipoRuoloFornitoreDTO(lTipoRuoloFornitore);
	}
	
	/**
	 * metodo per il recupero dei dati sulla tipologica Stato Contratto
	 * @return una Lista di StatoContrattoDTO
	 */
	@Override
	public List<StatoContrattoDTO> getStatoContratto() {
		List<StatoContrattoDTO> listStatoContratto = dtoMapper.listStatoContrattoToListStatoContrattoDTO(statoContrattoRepo.findAll());
		listStatoContratto.add(new StatoContrattoDTO(0, "Tutti"));
		return listStatoContratto;
	}
	
	/**
	 * metodo per il recupero dei dati sulla tipologica Ruolo Attore
	 * @return una Lista di RuoloAttoreDTO
	 */
	@Override
	public List<RuoloAttoreDTO> getListaRuoloAttore() {
		return dtoMapper.listRuoloAttoreToListRuoloAttoreDTO(ruoloAttoreRepo.findAll());
	}

	/**
	 * metodo per il recupero dei dati sulla tipologica Tipo Contratto
	 * @return una Lista di TipoContrattoDTO
	 */
	@Override
	public List<TipoContrattoDTO> getListaTipoContratto() {
		return dtoMapper.listTipoContrattoToListTipoContrattoDTO(tipoContrattoRepo.findAll());
	}

	/**
	 * metodo per il recupero dei dati sulla tipologica Tipo Cronologia
	 * @return una Lista di TipoContrattoDTO
	 */
	@Override
	public List<TipoCronologiaDTO> getListaTipoCronologia() {
		return dtoMapper.listTipoCronologiaToListTipoCronologiaDTO(tipoCronologiaRepo.findAll());
	}

	/**
	 * metodo per il recupero dei dati sulla tipologica Tipo Riferimento
	 * @return una Lista di TipoRiferimentoDTO
	 */
	@Override
	public List<TipoRiferimentoDTO> getListaTipoRiferimento() {
		return dtoMapper.listTipoRiferimentoToListTipoRiferimentoDTO(tipoRiferimentoRepo.findAll());
	}


	@Override
	public List<TipoIntegrazioneDTO> getListaTipoIntegrazione() {
		
		return dtoMapper.listTipoIntegrazioneToListTipoIntegrazioneDTO(tipoIntegrazioneRepo.findAll());
	}


	@Override
	public List<TipoRuoloStrutturaDTO> getListaTipoRuoloStruttura() {
		// TODO Auto-generated method stub
		return dtoMapper.listTipoRuoloStrutturaToListTipoRuoloStrutturaDTO(tipoRuoloStrutturaRepo.findAll());
	}
}
