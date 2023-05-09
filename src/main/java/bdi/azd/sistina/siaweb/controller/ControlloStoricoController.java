package bdi.azd.sistina.siaweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.dto.AttoreContrattoDTO;
import bdi.azd.sistina.siaweb.dto.CheckStoricoAttoreFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoCronologiaFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoImportiFilter;
import bdi.azd.sistina.siaweb.dto.CheckStoricoStruttureFilter;
import bdi.azd.sistina.siaweb.dto.ContrattoStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaDTO;
import bdi.azd.sistina.siaweb.dto.ImportoDTO;
import bdi.azd.sistina.siaweb.service.ContrattoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("storico")
@Tag(name = "Storico", description = "")
@Slf4j
@Filter(name = "LoggingFilter")
public class ControlloStoricoController {

    @Autowired
    public  ContrattoService contrattoService;


    
    /**
     * chiamata per la ricerca di uno storico struttura
     *
     * @param request, checkStoricoStruttureFilter
     * @return ContrattoStrutturaDTO
     */
    @PostMapping(value = "/strutture", produces = {"application/json"})
    @Operation(summary = "ricerca storico struttura", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<ContrattoStrutturaDTO> searchStoricoStruttura(
            @RequestBody CheckStoricoStruttureFilter checkStoricoStruttureFilter,
            HttpServletRequest request) {

        log.info("searchStoricoStruttura() init...");

        try {
            ContrattoStrutturaDTO contrattoStr = contrattoService.getStoricoStruttura(checkStoricoStruttureFilter);
            if(contrattoStr!=null) {
            	return ResponseEntity.ok().body(contrattoStr);
            }else {
            	return ResponseEntity.noContent().build();
            }
        } catch (Exception ex) {
            log.info("searchStoricoStruttura()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per la ricerca di uno storico attore
     *
     * @param request, checkStoricoAttoreFilter
     * @return AttoreContrattoDTO
     */
    @PostMapping(value = "/attori", produces = {"application/json"})
    @Operation(summary = "ricerca storico attore", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<AttoreContrattoDTO> searchStoricoAttore(
            @RequestBody CheckStoricoAttoreFilter checkStoricoAttoreFilter,
            HttpServletRequest request) {

        log.info("searchStoricoAttore() init...");

        try {
            AttoreContrattoDTO contrattoAtt = contrattoService.getStoricoAttore(checkStoricoAttoreFilter);
            if(contrattoAtt!=null) {
            	return ResponseEntity.ok().body(contrattoAtt);
            }else {
            	return ResponseEntity.noContent().build();
            }
        } catch (Exception ex) {
            log.info("searchStoricoAttore()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per la ricerca di uno storico Cronologia
     *
     * @param request, checkStoricoCronologiaFilter
     * @return CronologiaDTO
     */
    @PostMapping(value = "/cronologia", produces = {"application/json"})
    @Operation(summary = "ricerca storico cronologia", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<CronologiaDTO> searchStoricoCronologia(
            @RequestBody CheckStoricoCronologiaFilter checkStoricoCronologiaFilter,
            HttpServletRequest request) {

        log.info("searchStoricoCronologia() init...");

        try {
            CronologiaDTO cronologia = contrattoService.getStoricoCronologia(checkStoricoCronologiaFilter);
            if(cronologia!=null) {
            	return ResponseEntity.ok().body(cronologia);
            }else {
            	return ResponseEntity.noContent().build();
            }
        } catch (Exception ex) {
            log.info("searchStoricoCronologia()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per la ricerca di uno storico Importo
     *
     * @param request, checkStoricoImportoFilter
     * @return ImportoDTO
     */
    @PostMapping(value = "/importo", produces = {"application/json"})
    @Operation(summary = "ricerca storico cronologia", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<ImportoDTO> searchStoricoImporto(
            @RequestBody CheckStoricoImportiFilter checkStoricoImportoFilter,
            HttpServletRequest request) {

        log.info("searchStoricoCronologia() init...");

        try {
            ImportoDTO importo = contrattoService.getStoricoImporto(checkStoricoImportoFilter);
            if(importo!=null) {
            	return ResponseEntity.ok().body(importo);
            }else {
            	return ResponseEntity.noContent().build();
            }
        } catch (Exception ex) {
            log.info("searchStoricoCronologia()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }



}
