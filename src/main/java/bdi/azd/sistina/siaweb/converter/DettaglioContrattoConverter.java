package bdi.azd.sistina.siaweb.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoSummaryDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.DettaglioContrattoDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreContrattoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.IntegrazioneDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.ProgrammazioneDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.RiferimentiDocumentaliDTO;
import bdi.azd.sistina.siaweb.dto.RuoloAttoreDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.RuoloFornitoreDTO;
import bdi.azd.sistina.siaweb.dto.StatoContrattoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.StrutturaDTO;
import bdi.azd.sistina.siaweb.dto.TipoCigDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoContrattoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoCronologiaDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoFornitoreDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoImportoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoIntegrazioneDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoRiferimentoDettaglioDTO;
import bdi.azd.sistina.siaweb.dto.TipoSubappaltoDettaglioDTO;
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
import bdi.azd.sistina.siaweb.entity.Riferimento;

@Mapper( componentModel = "spring" )
public class DettaglioContrattoConverter {
	
	public DettaglioContrattoDTO toDTO(Contratto in) {
		DettaglioContrattoDTO dto=new DettaglioContrattoDTO();
		
		//contratto
		dto.setMotivazioneColl(in.getMotivazioneCollegamento()!=null?in.getMotivazioneCollegamento():null);
		dto.setDescrizione(in.getDescrizioneCrtt()!=null?in.getDescrizioneCrtt():null);
		dto.setCig(in.getCig()!=null?in.getCig():null);
		dto.setContrattoPk(in.getIdContratto());
		if(in.getTipoCig()!=null) {
			dto.setTipoCig(new TipoCigDettaglioDTO(in.getTipoCig().getIdTipoCig(),in.getTipoCig().getTipoCig()));
		}
		dto.setCigPadre(in.getCigPadre()!=null?in.getCigPadre():null);
		if(in.getTipoContratto()!=null) {
			dto.setTipoContratto(new TipoContrattoDettaglioDTO(in.getTipoContratto().getIdTpContratto(),in.getTipoContratto().getTipoContratto()));
		}
		if(in.getStatoContratto()!=null) {
			dto.setStatoContratto(new StatoContrattoDettaglioDTO(in.getStatoContratto().getIdStContratto(),in.getStatoContratto().getStatoContratto()));
		}
		Importo importo=in.getImportos().stream().filter(c->c != null && c.getDataFineVal()==null && c.getTipoImporto().getIdTipImporto()==1).findFirst().orElse(null);
		dto.setImportoMassimo(importo!=null && importo.getValoreImp()!=null?importo.getValoreImp():null);
		if(!in.getContrattoStrutturas().isEmpty()) {
			ContrattoStruttura conStrutturaValidante= in.getContrattoStrutturas().stream().filter(c->c.getDataFineVal()==null && c.getRuoloStruttura().getIdRuoloStr()==1).findFirst().orElse(null);
			ContrattoStruttura conStrutturaResponsabile= in.getContrattoStrutturas().stream().filter(c->c.getDataFineVal()==null && c.getRuoloStruttura().getIdRuoloStr()==2).findFirst().orElse(null);
			AnagrafeStruttura anStrutturaValidante=conStrutturaValidante!=null && conStrutturaValidante.getAnagrafeStruttura()!=null? conStrutturaValidante.getAnagrafeStruttura():null;
			AnagrafeStruttura anStrutturaResponsabile=conStrutturaResponsabile!=null && conStrutturaResponsabile.getAnagrafeStruttura()!=null?conStrutturaResponsabile.getAnagrafeStruttura():null;	
			
			List<ContrattoStruttura> conStrutturaDestinariaList=in.getContrattoStrutturas().stream().filter(c->c.getDataFineVal()==null && c.getRuoloStruttura().getIdRuoloStr()==3).collect(Collectors.toList());
			if(anStrutturaResponsabile!=null && anStrutturaResponsabile.getCodiceStruttura()!=null && anStrutturaResponsabile.getDenominazione()!=null) {
				StrutturaDTO strutturaResponsabile= new StrutturaDTO();
				strutturaResponsabile.setIdStruttura(conStrutturaResponsabile.getIdContrattoStruttura());
				strutturaResponsabile.setDenominazione(anStrutturaResponsabile.getCodiceStruttura()+" "+anStrutturaResponsabile.getDenominazione());
				dto.setStrutturaResponsabile(strutturaResponsabile);
			}
			if(anStrutturaValidante!=null && anStrutturaValidante.getCodiceStruttura()!=null && anStrutturaValidante.getDenominazione()!=null) {
				StrutturaDTO strutturaValidante= new StrutturaDTO();
				strutturaValidante.setIdStruttura(conStrutturaValidante.getIdContrattoStruttura());
				strutturaValidante.setDenominazione(anStrutturaValidante.getCodiceStruttura()+" "+anStrutturaValidante.getDenominazione());
				dto.setStrutturaValidante(strutturaValidante);
			}
			List<StrutturaDTO> struttureDest= new ArrayList<>();
			for(ContrattoStruttura conStr:conStrutturaDestinariaList) {
				if(conStr.getAnagrafeStruttura()!=null && conStr.getAnagrafeStruttura().getCodiceStruttura()!=null && conStr.getAnagrafeStruttura().getDenominazione()!=null) {
					StrutturaDTO strutturaDestinataria= new StrutturaDTO();
					strutturaDestinataria.setIdStruttura(conStr.getIdContrattoStruttura());
					strutturaDestinataria.setDenominazione(conStr.getAnagrafeStruttura().getCodiceTipologia()+" "+conStr.getAnagrafeStruttura().getDenominazione());
					struttureDest.add(strutturaDestinataria);
				}
			}
			dto.setElencoStruttDestinataria(struttureDest);
			
		}
		if(in.getTipoSubappalto()!=null) {
			dto.setTipoSubappalto(new TipoSubappaltoDettaglioDTO(in.getTipoSubappalto().getIdTipoSub(),in.getTipoSubappalto().getTipoSubappalto()));
		}
		dto.setNoteStato(in.getNoteStato()!=null?in.getNoteStato():null);
		
		//cronologia
		List<CronologiaDettaglioDTO>cronologiaDTOList= new ArrayList<>();
		if(!in.getCronologias().isEmpty()) {
			List<Cronologia>cronologieList=in.getCronologias().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
			for(Cronologia cronologia:cronologieList){
				CronologiaDettaglioDTO cronDTO= new CronologiaDettaglioDTO();
				if(cronologia.getTipoCronologia()!=null) {
					cronDTO.setEvento(new TipoCronologiaDettaglioDTO(cronologia.getTipoCronologia().getIdTpCronologia(),cronologia.getTipoCronologia().getTipoCronologia()));
				}
				cronDTO.setIdCronologia(cronologia.getIdCronologia());
				cronDTO.setDtInizioEvento(cronologia.getDtInizioEvento()!=null?cronologia.getDtInizioEvento():null);
				cronDTO.setDtFineEvento(cronologia.getDtFineEvento()!=null?cronologia.getDtFineEvento():null);
				cronDTO.setDataPresunta(cronologia.getFlagPresunta()!=null?cronologia.getFlagPresunta():null);
				cronDTO.setMotivazione(cronologia.getMotivazione()!=null?cronologia.getMotivazione():null);
				cronDTO.setIntegrazione(cronologia.getIntegrazione()!=null && cronologia.getIntegrazione().getTipoIntegrazione()!=null && cronologia.getIntegrazione().getDescrizione()!=null?cronologia.getIntegrazione().getTipoIntegrazione()+" "+cronologia.getIntegrazione().getDescrizione():null);
				cronologiaDTOList.add(cronDTO);
			}
		}
		dto.setCronologie(cronologiaDTOList);
		
		
		//fornitori
		List<FornitoreContrattoDettaglioDTO>fornitoriDTOList= new ArrayList<>();
		if(!in.getFornitoreContrattos().isEmpty()) {
			List<FornitoreContratto>fornitoriList=in.getFornitoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
			for(FornitoreContratto fornitoreCont: fornitoriList) {
				Fornitore fornitore=fornitoreCont.getFornitore();
				FornitoreContrattoDettaglioDTO forn= new FornitoreContrattoDettaglioDTO();
				forn.setFornitoreContrattoPk(fornitoreCont.getIdFornitoreContratto());
				forn.setCodiceFiscale(fornitore.getCodiceFiscale()!=null? fornitore.getCodiceFiscale():null);
				forn.setPartitaIva(fornitore.getPartitaIva()!=null? fornitore.getPartitaIva():null);
				forn.setPmi(fornitore.getPmi()!=null?fornitoreCont.getFornitore().getPmi():null);
				forn.setRagioneSociale(fornitore.getRagioneSociale()!=null? fornitore.getRagioneSociale():null);
				forn.setInizioEvento(fornitoreCont.getDtInizioEvento()!=null?fornitoreCont.getDtInizioEvento():null);
				forn.setIntegrazione(fornitoreCont.getIntegrazione()!=null && fornitoreCont.getIntegrazione().getTipoIntegrazione()!=null && fornitoreCont.getIntegrazione().getDescrizione()!=null? fornitoreCont.getIntegrazione().getTipoIntegrazione()+" "+fornitoreCont.getIntegrazione().getDescrizione():null);
				//tipologica RuoloFornitore
				if(fornitoreCont.getRuoloFornitore()!=null) {
					forn.setRuoloFornitoreBean(new RuoloFornitoreDTO(fornitoreCont.getRuoloFornitore().getIdRlFornitore(),fornitoreCont.getRuoloFornitore().getRuoloFornitore()));
				}
				
				fornitoriDTOList.add(forn);
				
				//tipologica tipoFornitore
				if(fornitoreCont.getTipologiaFornitore()!=null) {
					forn.setTipoFornitore(new TipoFornitoreDettaglioDTO(fornitoreCont.getTipologiaFornitore().getIdTpFornitore(),fornitoreCont.getTipologiaFornitore().getTipoFornitore()));
				}
				fornitoriDTOList.add(forn);
			}dto.setFornitori(fornitoriDTOList);
		}
		
		
		//attori
		List<AttoreDTO> attoriDTOList= new ArrayList<>();
		if(!in.getAttoreContrattos().isEmpty()) {
			List<AttoreContratto> attoriList=in.getAttoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
			for(AttoreContratto attContr: attoriList) {
				Attore attore=attContr.getAttore();
				AttoreDTO attoreDTO= new AttoreDTO();
				attoreDTO.setIdAttoreContratto(attContr.getIdAttoreContratto());
				attoreDTO.setEmail(attore.getEmail()!=null?attore.getEmail():null);
				attoreDTO.setNominativo(attore.getNominativo());
				attoreDTO.setUserid(attore.getUserid()!=null?attore.getUserid():null);
				attoreDTO.setDataInizioEvento(attContr.getDtInizioEvento());
				if(attContr.getRuoloAttoreBean()!=null) {
					attoreDTO.setRuoloAttore(new RuoloAttoreDettaglioDTO(attContr.getRuoloAttoreBean().getIdRuoloAttore(),attContr.getRuoloAttoreBean().getRuoloAttore()));
				}
				attoreDTO.setServizioDiAppartenenza(attContr.getCodServAtt()!=null && attContr.getDenominServ()!=null?attContr.getCodServAtt()+" "+attContr.getDenominServ():null);
				attoriDTOList.add(attoreDTO);
			}
			dto.setAttori(attoriDTOList);
		}
		
		//integrazioni
		List<IntegrazioneDettaglioDTO> integrazioneDTOList= new ArrayList<>();
		if(!in.getIntegraziones().isEmpty()) {
			for(Integrazione integrazione:in.getIntegraziones()) {
				IntegrazioneDettaglioDTO intDTO=new IntegrazioneDettaglioDTO();
				if(integrazione.getTipoIntegrazione()!=null) {
					intDTO.setTipoIntegrazione(new TipoIntegrazioneDettaglioDTO(integrazione.getTipoIntegrazione().getIdTpIntegraz(),integrazione.getTipoIntegrazione().getTipoIntegr()));
				}
//				intDTO.setCigGenerato(integrazione.getContrattoCigGenerato().getCig()!=null && integrazione.getContrattoCigGenerato()!=null? integrazione.getContrattoCigGenerato().getCig():null);
				intDTO.setDescrizione(integrazione.getDescrizione()!=null?integrazione.getDescrizione():null);
				intDTO.setMotivazione(integrazione.getMotivazione()!=null?integrazione.getMotivazione():null);
//				intDTO.setData(integrazione.getDataIntegrazione()!=null?integrazione.getDataIntegrazione():null);
				if(integrazione.getProcedura()!=null) {
					intDTO.setProceduraProroga(integrazione.getProcedura().getCodProcedura()+" "+integrazione.getProcedura().getDescrizione());
				}
				integrazioneDTOList.add(intDTO);
			}
			dto.setIntegrazioni(integrazioneDTOList);
		}
		
		//importi
		List<ImportoDettaglioDTO>importiDTOList= new ArrayList<>();
		if(in.getImportos().isEmpty()) {
			List<Importo> importoList=in.getImportos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList());
			for(Importo importoBean:importoList) {
				ImportoDettaglioDTO importoDTO= new ImportoDettaglioDTO();
				importoDTO.setIdImporto(importoBean.getIdImporto());
				if(importoBean.getTipoImporto()!=null) {
					importoDTO.setTipoImporto(new TipoImportoDettaglioDTO(importoBean.getTipoImporto().getIdTipImporto(),importoBean.getTipoImporto().getTipoImporto()));
				}
				importoDTO.setValoreImp(importoBean.getValoreImp()!=null?importoBean.getValoreImp():null);
				importoDTO.setInizioEvento(importoBean.getDtInizioEvento()!=null?importoBean.getDtInizioEvento():null);
				importoDTO.setIntegrazione(importoBean.getIntegrazione()!=null && importoBean.getIntegrazione().getTipoIntegrazione()!=null && importoBean.getIntegrazione().getDescrizione()!=null?importoBean.getIntegrazione().getTipoIntegrazione()+" "+importoBean.getIntegrazione().getDescrizione():null);

				importiDTOList.add(importoDTO);
			}dto.setImporti(importiDTOList);
		}
		
		//programmazione
		ProgrammazioneDettaglioDTO programmazione= new ProgrammazioneDettaglioDTO();
		programmazione.setCategoriaMerceologica(in.getCatMerceologica()!=null?in.getCatMerceologica():null);
		programmazione.setComparto(in.getComparto()!=null?in.getComparto():null);
		if(in.getProceduraOrg()!=null) {
			programmazione.setProceduraOrigine(in.getProceduraOrg().getCodProcedura()!=null && in.getProceduraOrg().getDescrizione()!=null?in.getProceduraOrg().getCodProcedura()+" "+in.getProceduraOrg().getDescrizione():null);
		}
		if(in.getProceduraRin()!=null) {
			programmazione.setProceduraRinnovo(in.getProceduraRin().getCodProcedura()!=null && in.getProceduraRin().getDescrizione()!=null?in.getProceduraRin().getCodProcedura()+" "+in.getProceduraRin().getDescrizione():null);
		}
		programmazione.setRicorrente(in.getRicorrente()!=null?in.getRicorrente():null);
		
		dto.setProgrammazione(programmazione);
		
		//riferimentiDocumentali
		List<RiferimentiDocumentaliDTO> riferimentiList= new ArrayList<>();
		if(!in.getRiferimentos().isEmpty()) {
			for(Riferimento rif:in.getRiferimentos()) {
				RiferimentiDocumentaliDTO riferimento= new RiferimentiDocumentaliDTO();
				riferimento.setIdRiferimento(rif.getIdRiferimento());
				if(rif.getTipoRiferimento()!=null) {
					riferimento.setTipoRiferimento(new TipoRiferimentoDettaglioDTO(rif.getTipoRiferimento().getIdTpRiferimento(),rif.getTipoRiferimento().getTipoRiferimento()));
				}
				riferimento.setDataValidita(rif.getDataVal()!=null?rif.getDataVal():null);
				riferimento.setDescrizione(rif.getDescrizione()!=null?rif.getDescrizione():null);
				riferimento.setLink(rif.getLink()!=null?rif.getLink():null);
				riferimento.setCodRiferimento(rif.getCodRiferimento()!=null?rif.getCodRiferimento():null);
				riferimentiList.add(riferimento);
			}
			dto.setRiferimentiDocumentali(riferimentiList);
		}
		
		return dto;
	}
	

	public List<ContrattoSummaryDTO> toSummaryDTO(List<Contratto> inList) {
		List<ContrattoSummaryDTO>contrattoList= new ArrayList<>();
		for(Contratto in:inList) {
			ContrattoSummaryDTO contratto= new ContrattoSummaryDTO();
			contratto.setCig(in.getCig()!=null?in.getCig():null);
			contratto.setCigPadre(in.getCigPadre()!=null?in.getCigPadre():null);
			contratto.setDescrizione(in.getDescrizioneCrtt()!=null?in.getDescrizioneCrtt():null);
			if(in.getTipoCig()!=null) {
				TipoCigDettaglioDTO tipoCig= new TipoCigDettaglioDTO(in.getTipoCig().getIdTipoCig(),in.getTipoCig().getTipoCig());
				contratto.setTipoCig(tipoCig);
			}
			if(in.getTipoContratto()!=null) {
				TipoContrattoDettaglioDTO tipoCon= new TipoContrattoDettaglioDTO(in.getTipoContratto().getIdTpContratto(),in.getTipoContratto().getTipoContratto());
				contratto.setTipoContratto(tipoCon);
			}
			var x = in.getImportos().stream().filter(c-> c != null && c.getDataFineVal()==null && c.getTipoImporto().getIdTipImporto()==1).findFirst();
			if(x.isPresent()) contratto.setImportoMassimo(x.get().getValoreImp());
			contrattoList.add(contratto);
		}
		return contrattoList;
	}
}
