package bdi.azd.sistina.siaweb.service;

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


@Service
public interface TipologicheService {

	List<TipoSubappaltoDettaglioDTO> getListaTipoSubappalto();
	List<TipoImportoDTO> getListaTipoImporto();
	List<TipoCigDettaglioDTO> getListaTipoCig();
	List<TipoFornitoreDettaglioDTO> getListaTipoFornitore();
	List<TipoRuoloFornitoreDTO> getListaTipoRuoloFornitore();
    public List<StatoContrattoDTO> getStatoContratto();
	List<RuoloAttoreDTO> getListaRuoloAttore();
	List<TipoContrattoDTO> getListaTipoContratto();
	List<TipoCronologiaDTO> getListaTipoCronologia();
	List<TipoRiferimentoDTO> getListaTipoRiferimento();
	List<TipoIntegrazioneDTO> getListaTipoIntegrazione();
	List<TipoRuoloStrutturaDTO> getListaTipoRuoloStruttura();

}
