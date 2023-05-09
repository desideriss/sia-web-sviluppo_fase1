package bdi.azd.sistina.siaweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import bdi.azd.sistina.siaweb.service.TipologicheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("tipologiche")
@Tag(name = "Tipologiche", description = "Recupero dati tipologiche")
@Slf4j
@Filter(name = "LoggingFilter")
public class TipologicheController {

	public final TipologicheService tipologicheService;

	public TipologicheController(TipologicheService tipologicheService) {
		this.tipologicheService = tipologicheService;

	}


	/**
	 * Recupera la Lista tipo subappalto.
	 *
	 * @param request la request
	 * @return lista tipo subappalto
	 */
	@GetMapping(value = "/tipoSubappalto", produces = {"application/json"})
	@Operation(summary = "tipo subappalto", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoSubappaltoDettaglioDTO>> getTipoSubappaltoList(HttpServletRequest request) {

		try {
			List<TipoSubappaltoDettaglioDTO> tipoSubappaltoDTO = tipologicheService.getListaTipoSubappalto();
   
			return ResponseEntity.ok().body(tipoSubappaltoDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
	
	/**
	 * Recupera la Lista tipo importo.
	 *
	 * @param request the request
	 * @return Lista tipo importo
	 */
	@GetMapping(value = "/tipoImporto", produces = {"application/json"})
	@Operation(summary = "tipo importo", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoImportoDTO>> getTipoImportoList(HttpServletRequest request) {

		try {
			List<TipoImportoDTO> tipoImportoDTO = tipologicheService.getListaTipoImporto();
   
			return ResponseEntity.ok().body(tipoImportoDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
	/**
	 * Recupera la lista tipo cig .
	 *
	 * @param request the request
	 * @return lista tipo cig 
	 */
	@GetMapping(value = "/tipoCig", produces = {"application/json"})
	@Operation(summary = "tipo cig", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoCigDettaglioDTO>> getTipoCigList(HttpServletRequest request) {

		try {
			List<TipoCigDettaglioDTO> tipoCigDTO = tipologicheService.getListaTipoCig();
   
			return ResponseEntity.ok().body(tipoCigDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
	
	/**
	 * Recupera la lista tipo fornitore.
	 *
	 * @param request the request
	 * @return the tipo fornitore list
	 */
	@GetMapping(value = "/tipoFornitore", produces = {"application/json"})
	@Operation(summary = "tipo fornitore", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoFornitoreDettaglioDTO>> getTipoFornitoreList(HttpServletRequest request) {

		try {
			List<TipoFornitoreDettaglioDTO> tipoFornitoreDTO = tipologicheService.getListaTipoFornitore();
   
			return ResponseEntity.ok().body(tipoFornitoreDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
	
	/**
	 * Recupera la lista tipo ruolo fornitore .
	 *
	 * @param request the request
	 * @return the tipo ruolo fornitore list
	 */
	@GetMapping(value = "/tipoRuoloFornitore", produces = {"application/json"})
	@Operation(summary = "tipo ruolo fornitore", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoRuoloFornitoreDTO>> getTipoRuoloFornitoreList(HttpServletRequest request) {

		try {
			List<TipoRuoloFornitoreDTO> tipoRuoloFornitoreDTO = tipologicheService.getListaTipoRuoloFornitore();
   
			return ResponseEntity.ok().body(tipoRuoloFornitoreDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
	 /**
     * chiamata per il recupero dei dati dalla tipologica Stato Contratto
     * @param request
     * @return ResponseEntity<List<StatoContrattoDTO>>
     */
    @GetMapping(value = "/statoContratto", produces = {"application/json"})
    @Operation(summary = "stato contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<StatoContrattoDTO>> getStatoContratto(
            HttpServletRequest request) {

        log.info("searchStatoContratto() init...");

        try {
            List<StatoContrattoDTO> statoContrattoDTOs;
            
            statoContrattoDTOs= tipologicheService.getStatoContratto();
            return ResponseEntity.ok().body(statoContrattoDTOs);
        } catch (Exception ex) {
            log.info("searchStatoContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata che recupera la tipologica ruolo attore
     * @param request
     * @return ResponseEntity<List<RuoloAttoreDTO>>
     */
    @GetMapping(value = "/ruoloAttore", produces = {"application/json"})
    @Operation(summary = "ruolo attore", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<RuoloAttoreDTO>> getRuoloAttoreList(
            HttpServletRequest request) {

        log.info("searchRuoloAttore() init...");

        try {
            List<RuoloAttoreDTO> ruoloAttoreDTOs;
            
            ruoloAttoreDTOs= tipologicheService.getListaRuoloAttore();
            return ResponseEntity.ok().body(ruoloAttoreDTOs);
        } catch (Exception ex) {
            log.info("searchRuoloAttore()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
	 /**
     * chiamata che recupera la tipologica tipo contratto
     * @param request
     * @return ResponseEntity<List<TipoContrattoDTO>>
     */
    @GetMapping(value = "/tipoContratto", produces = {"application/json"})
    @Operation(summary = "tipo contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<TipoContrattoDTO>> getTipoContrattoList(
            HttpServletRequest request) {

        log.info("searchTipoContratto() init...");

        try {
            List<TipoContrattoDTO> tipoContrattoDTOs;
            
            tipoContrattoDTOs= tipologicheService.getListaTipoContratto();
            return ResponseEntity.ok().body(tipoContrattoDTOs);
        } catch (Exception ex) {
            log.info("searchTipoContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
	 /**
     * chiamata per il recupero dei dati dalla tipologica Tipo Cronologia
     * @param request
     * @return ResponseEntity<List<TipoCronologiaDTO>>
     */
    @GetMapping(value = "/tipoCronologia", produces = {"application/json"})
    @Operation(summary = "tipo cronologia", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<TipoCronologiaDTO>> getTipoCronologiaList(
            HttpServletRequest request) {

        log.info("searchTipoCronologia() init...");

        try {
            List<TipoCronologiaDTO> tipoCronologiaDTOs;
            
            tipoCronologiaDTOs= tipologicheService.getListaTipoCronologia();
            return ResponseEntity.ok().body(tipoCronologiaDTOs);
        } catch (Exception ex) {
            log.info("searchTipoCronologia()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per il recupero dei dati dalla tipologica Tipo Riferimento
     * @param request
     * @return ResponseEntity<List<TipoRiferimentoDTO>>
     */
    @GetMapping(value = "/tipoRiferimento", produces = {"application/json"})
    @Operation(summary = "tipo riferimento", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<TipoRiferimentoDTO>> getTipoRiferimentoList(
            HttpServletRequest request) {

        log.info("searchTipoRiferimento() init...");

        try {
            List<TipoRiferimentoDTO> tipoRiferimentoDTOs;
            
            tipoRiferimentoDTOs= tipologicheService.getListaTipoRiferimento();
            return ResponseEntity.ok().body(tipoRiferimentoDTOs);
        } catch (Exception ex) {
            log.info("searchTipoRiferimento()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per il recupero dei dati dalla tipologica Tipo Integrazione
     * @param request
     * @return ResponseEntity<List<TipoIntegrazioneDTO>>
     */
    @GetMapping(value = "/tipoIntegrazione", produces = {"application/json"})
    @Operation(summary = "tipo integrazione", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<TipoIntegrazioneDTO>> getTipoIntegrazioneList(
            HttpServletRequest request) {

        log.info("searchTipoIntegrazione() init...");

        try {
            List<TipoIntegrazioneDTO> tipoIntegrazioneDTOs;
            
            tipoIntegrazioneDTOs= tipologicheService.getListaTipoIntegrazione();
            return ResponseEntity.ok().body(tipoIntegrazioneDTOs);
        } catch (Exception ex) {
            log.info("searchTipoCronologia()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
	 * Recupera la lista tipo ruolo struttura .
	 *
	 * @param request the request
	 * @return the tipo ruolo struttura list
	 */
	@GetMapping(value = "/tipoRuoloStruttura", produces = {"application/json"})
	@Operation(summary = "tipo ruolo struttura", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

	public ResponseEntity<List<TipoRuoloStrutturaDTO>> getTipoRuoloStrutturaList(HttpServletRequest request) {

		try {
			List<TipoRuoloStrutturaDTO> tipoRuoloStrutturaDTO = tipologicheService.getListaTipoRuoloStruttura();
   
			return ResponseEntity.ok().body(tipoRuoloStrutturaDTO);
		} catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
	}
	
}
