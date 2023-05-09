package bdi.azd.sistina.siaweb.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import bdi.azd.sistina.siaweb.dto.AnagrafeStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.AttoreContrattoModificaDTO;
import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoRecordDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoScaduto;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.dto.IntegrazioneDTO;
import bdi.azd.sistina.siaweb.dto.ProceduraDTO;
import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RuoloAttoreDTO;
import bdi.azd.sistina.siaweb.dto.RuoloFornitoreDTO;
import bdi.azd.sistina.siaweb.dto.StatoContrattoDTO;
import bdi.azd.sistina.siaweb.dto.StatoProcessoDTO;
import bdi.azd.sistina.siaweb.dto.StoricoEssenzValDTO;
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
import bdi.azd.sistina.siaweb.dto.TipologiaFornitoreDTO;
import bdi.azd.sistina.siaweb.entity.AnagrafeStruttura;
import bdi.azd.sistina.siaweb.entity.Attore;
import bdi.azd.sistina.siaweb.entity.AttoreContratto;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.Fornitore;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;
import bdi.azd.sistina.siaweb.entity.Importo;
import bdi.azd.sistina.siaweb.entity.Integrazione;
import bdi.azd.sistina.siaweb.entity.Procedura;
import bdi.azd.sistina.siaweb.entity.RuoloAttore;
import bdi.azd.sistina.siaweb.entity.RuoloFornitore;
import bdi.azd.sistina.siaweb.entity.RuoloStruttura;
import bdi.azd.sistina.siaweb.entity.StatoContratto;
import bdi.azd.sistina.siaweb.entity.StatoProcesso;
import bdi.azd.sistina.siaweb.entity.StoricoEssenzVal;
import bdi.azd.sistina.siaweb.entity.TipoCig;
import bdi.azd.sistina.siaweb.entity.TipoContratto;
import bdi.azd.sistina.siaweb.entity.TipoCronologia;
import bdi.azd.sistina.siaweb.entity.TipoImporto;
import bdi.azd.sistina.siaweb.entity.TipoIntegrazione;
import bdi.azd.sistina.siaweb.entity.TipoRiferimento;
import bdi.azd.sistina.siaweb.entity.TipoSubappalto;
import bdi.azd.sistina.siaweb.entity.TipologiaFornitore;
import bdi.azd.sistina.siaweb.entity.VRicercacontratto;

@Mapper(componentModel = "spring")
public interface DtoMapper {
	
	FornitoreDTO fornitoreToFornitoreDTO(Fornitore source);
	Fornitore fornitoreDtoTofornitore(FornitoreDTO destination);
	List<FornitoreDTO> listFornitoreToListFornitoreDTO(List<Fornitore> destination);
	List<StatoContrattoDTO> listStatoContrattoToListStatoContrattoDTO(List<StatoContratto> statoContrattoList);
	List<AnagrafeStrutturaDTO> listAnagrafeStrutturaToListAnagrafeStrutturaDTO(List<AnagrafeStruttura>anagrafeStrutturaList);
	Attore attoreDTOToAttore(AttoreDTO destination);
	
	List<TipoSubappaltoDettaglioDTO> listTipoSubappaltoToListTipoSubappaltoDTO(List<TipoSubappalto> destination);
	List<TipoImportoDTO> listTipoImportoToListTipoImportoDTO(List<TipoImporto> destination);
	List<TipoCigDettaglioDTO> listTipoCigToListTipoCigDTO(List<TipoCig> destination);
	List<TipoFornitoreDettaglioDTO> listTipoFornitoreToListTipoFornitoreDTO(List<TipologiaFornitore> destination);
	List<TipoRuoloFornitoreDTO> listTipoRuoloFornitoreToListTipoRuoloFornitoreDTO(List<RuoloFornitore> destination);
	List<RicercaContrattoDTO> listVRicercaContrattoContrattoToListRicercaContrattoDTO(List<VRicercacontratto> contrattos);
	StatoProcesso statoProcessoDTOToStatoProcesso(StatoProcessoDTO destination);
	StatoProcessoDTO statoProcessoDTOToStatoProcesso(StatoProcesso statoProcesso);
	ContrattoDTO contrattoEntityToContrattoDTO(Contratto destination);
	Contratto contrattoDTOToContrattoEntity(ContrattoDTO destination);
	ProceduraDTO proceduraEntityToProceduraDTO(Procedura destination);
	Procedura proceduraDTOToProcedura(ProceduraDTO destination);
//	List<AttoreContratto>listAttoreContrattoDTOToAttoreContratto(List<AttoreContrattoDTO> destination);

	List<AttoreContratto> attoreContrattoDTOToAttoreContratto(List<AttoreContrattoDTO> destination);

	FornitoreContrattoDTO fornitoreContrattoToFornitoreContrattoDTO(FornitoreContratto saved);
	FornitoreContratto fornitoreContrattoDtoTofornitoreContratto(FornitoreContrattoDTO fornitoreContrattoDTO);
	List<FornitoreContratto> listFornitoreContrattoDtoTofornitoreContratto(List<FornitoreContrattoDTO> fornitoreContrattoDTO);
	List<Importo> listImportoDTOToListImporto(List<ImportoDTO> importoDTO);
	TipoImporto tipoImportoDTOToListTipoImporto(TipoImportoDTO tipoImporto);
	ContrattoStruttura toContrattoStrutturaDto(ContrattoStrutturaDTO contrattoStrutturaDTO);
	ContrattoStrutturaDTO toContrattoStruttura(ContrattoStruttura contrattoStruttura);
	AttoreContratto attoreContrattoDTOToAttoreContratto(AttoreContrattoModificaDTO attoreContrattoModificaDTO);
	AttoreContrattoDTO attoreContrattoToAttoreContrattoDTO(AttoreContratto attoreContratto);
	List<Cronologia> listCronologiaDTOToListCronologia(List<CronologiaDTO> input);
	RicercaContrattoDTO VRicercaContrattoToRicercaContrattoDTO(VRicercacontratto contratto);

	List<ContrattoScaduto> toContrattoScaduto(List<ContrattoRecordDTO> contrattoRecord);
	List<RuoloAttoreDTO> listRuoloAttoreToListRuoloAttoreDTO(List<RuoloAttore> findAll);
	List<TipoContrattoDTO> listTipoContrattoToListTipoContrattoDTO(List<TipoContratto> findAll);
	List<TipoCronologiaDTO> listTipoCronologiaToListTipoCronologiaDTO(List<TipoCronologia> findAll);
	List<TipoRiferimentoDTO> listTipoRiferimentoToListTipoRiferimentoDTO(List<TipoRiferimento> findAll);
	List<TipoIntegrazioneDTO> listTipoIntegrazioneToListTipoIntegrazioneDTO(List<TipoIntegrazione> findAll);
	Integrazione integrazioneDtoTointegrazione(IntegrazioneDTO integrazioneDTO);
	IntegrazioneDTO integrazioneTointegrazioneDto(Integrazione integrazione);
	RuoloAttore ruoloAttoreDTOToRuoloAttore(RuoloAttoreDTO ruoloAttore);
	List<TipoRuoloStrutturaDTO> listTipoRuoloStrutturaToListTipoRuoloStrutturaDTO(List<RuoloStruttura> findAll);
	Importo importoDTOToImporto(ImportoDTO importoDTO);
	ImportoDTO importoToImportoDTO(Importo importo);
	Cronologia cronologiaDTOToCronologia(CronologiaDTO cronologia);
	CronologiaDTO cronologiaToCronologiaDTO(Cronologia cronologia);
	TipoCronologia tipoCronologiaDTOToTipoCronologia(TipoCronologiaDTO tipoCronologia);
	AttoreContratto attoreContrattoDTOToAttoreContratto(AttoreContrattoDTO attContr);
	List<StoricoEssenzValDTO> storicoEssenzValToStoricoEssenzValDTO(List<StoricoEssenzVal> storicoEssenzVal);
	List<StoricoEssenzVal> storicoEssenzValDTOStoricoEssenzVal(List<StoricoEssenzValDTO> storicoEssenzValDTO);
	
	List<RicercaContrattoDTO> listContrattoToListRicercaContrattoDTO(List<Contratto> contrattoi);
	TipologiaFornitore tipoFornitoreDTOToTipoFornitore(TipologiaFornitoreDTO destination);
	RuoloFornitore ruoloFornitoreDTOToRuoloFornitore(RuoloFornitoreDTO destination);
	TipoIntegrazione tipoIntegrazioneDTOToTipoIntegrazione(TipoIntegrazioneDTO destination);
	
	List<ProceduraDTO> proceduraEntityListToProceduraDTOList(List<Procedura> destination);
}
