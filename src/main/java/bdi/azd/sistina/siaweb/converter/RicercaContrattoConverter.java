package bdi.azd.sistina.siaweb.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RicercaStrutturaDTO;
import bdi.azd.sistina.siaweb.entity.Contratto;
import bdi.azd.sistina.siaweb.entity.ContrattoStruttura;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.entity.FornitoreContratto;

public class RicercaContrattoConverter {
	
	public static RicercaContrattoDTO contrattoToRicercaContrattoDTO(Contratto contratto) {
		RicercaContrattoDTO dto= new RicercaContrattoDTO();
		
		dto.setCig(contratto.getCig()); //OK
		dto.setCigPadre(contratto.getCigPadre());
		dto.setCodProcedura(null);
		dto.setCodProcOrg(contratto.getProceduraOrg()!=null?contratto.getProceduraOrg().getCodProcedura():null);//OK
		dto.setCodProcRin(contratto.getProceduraRin()!=null?contratto.getProceduraRin().getCodProcedura():null);//OK
		dto.setComparto(contratto.getComparto());
		dto.setDataFineEvento(null);
		dto.setDataInizioEvento(null);
		for (Cronologia elem : contratto.getCronologias()) {
			if(elem.getTipoCronologia().getIdTpCronologia()==4) {
				dto.setDataPrimaScadenza(elem.getDtInizioEvento());//OK
			}
			if(elem.getTipoCronologia().getIdTpCronologia()==5) {
				dto.setDataUltimaScadenza(elem.getDtInizioEvento());//OK
			}
		}
		dto.setDenominazione(contratto.getDescrizioneCrtt());//OK
		dto.setFlagValidante(null);
		dto.setIdProcedura(null);
		dto.setIdProcOrg(null);
		dto.setIdRuoloStr(null);
		dto.setIdStContratto(null);
		dto.setIdStruttura(null);
		dto.setIdTipCronol(null);
		dto.setIdTipoCig(null);
		dto.setNominativo(null);
		dto.setRagioneSociale(null);
		List<String> ragsocList= new ArrayList<>();//OK
		for (FornitoreContratto elem : contratto.getFornitoreContrattos().stream().filter(c->c.getDataFineVal()==null).collect(Collectors.toList())) {
			if(elem.getFornitore()!=null) {
				ragsocList.add(elem.getFornitore().getRagioneSociale());
			}
			//TODO
			dto.setTipoFornitore(elem.getTipologiaFornitore().getTipoFornitore());
		}
		dto.setRagioniSociali(ragsocList);
		dto.setRuoloAttore(null);

		dto.setStruttura(null);
		List<RicercaStrutturaDTO> strutturaList= new ArrayList<>();//OK
		for (ContrattoStruttura elem : contratto.getContrattoStrutturas().stream().filter(c->c.getRuoloStruttura().getIdRuoloStr()==3).collect(Collectors.toList())) {
			if(elem.getAnagrafeStruttura()!=null) {
				RicercaStrutturaDTO strutt=new RicercaStrutturaDTO();
				strutt.setDenominazione(elem.getAnagrafeStruttura().getDenominazione());
				strutt.setIdRuoloStruttura(elem.getRuoloStruttura().getIdRuoloStr());
				strutt.setCodStruttura(elem.getAnagrafeStruttura().getCodiceStruttura());
				strutturaList.add(strutt);
			}
		}
//		if(strutturaList.size()>1) {
//			strutturaList.clear();
//			strutturaList.add("Record Multipli");
//		}
		dto.setStrutturas(strutturaList);
		
		return dto;
		
	}

}
