package bdi.azd.sistina.siaweb.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.dto.AnagrafeStrutturaDTO;
import bdi.azd.sistina.siaweb.dto.StrutturaDTO;
import bdi.azd.sistina.siaweb.service.AnagrafeStrutturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("struttura")
@Tag(name = "Struttura", description = "")
@Slf4j
@Filter(name = "LoggingFilter")
public class AnagrafeStrutturaController {

    
    public final AnagrafeStrutturaService anagrafeStrutturaService;


    public AnagrafeStrutturaController(AnagrafeStrutturaService anagrafeStrutturaService) {
        this.anagrafeStrutturaService=anagrafeStrutturaService;
    }
    
    /**
     * chiamata per il recupero dei dati dalla tipologica Anagrafe Struttura
     * @param flagValidante flag che indica se è una struttura validante 
     * @param request
     * @return ResponseEntity
     */
    @GetMapping(value = "/{flagValidante}",produces = {"application/json"})
    @Operation(summary = "ricerca anagrafe struttura", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<AnagrafeStrutturaDTO>> getAnagrafeStruttura(
    		@PathVariable(value="flagValidante",required=false) BigDecimal flagValidante,
            HttpServletRequest request) {

        log.info("getAnagrafeStruttura() init...");

        try {
            List<AnagrafeStrutturaDTO> anagrafeStrutturaDTOs;
            
            anagrafeStrutturaDTOs= anagrafeStrutturaService.getAnagrafeStruttura(flagValidante);
            return ResponseEntity.ok().body(anagrafeStrutturaDTOs);
        } catch (Exception ex) {
            log.info("getAnagrafeStruttura()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per il recupero del codice e denominazione e data fine evento dalle Strutture validanti
     * @param denominazione 
     * @param request
     * @return ResponseEntity una lista di StruttureDTO
     */
    @GetMapping(value = "/validante/{denominazione}",produces = {"application/json"})
    @Operation(summary = "ricerca denominazione struttura validante", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<StrutturaDTO>> getDenominazioneValidanteLike(
    		@PathVariable("denominazione") String denominazione,
            HttpServletRequest request) {

        log.info("getDenominazioneValidanteLike() init...");

        try {
            List <StrutturaDTO> res=anagrafeStrutturaService.getStrutturaValidanteLike(denominazione);
            return ResponseEntity.ok().body(res);
        } catch (Exception ex) {
            log.info("getDenominazioneValidanteLike()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per il recupero del codice e denominazione e data fine evento dalle Strutture responsabili
     * @param denominazione 
     * @param request
     * @return ResponseEntity una lista di StrutturaDTO
     */
    @GetMapping(value = "/responsabile/{denominazione}",produces = {"application/json"})
    @Operation(summary = "ricerca denominazione struttura responsabile", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<StrutturaDTO>> getDenominazioneResponsabileLike(
    		@PathVariable("denominazione") String denominazione,
            HttpServletRequest request) {

        log.info("getDenominazioneResponsabileLike() init...");

        try {
            List <StrutturaDTO> res=anagrafeStrutturaService.getStrutturaResponsabileLike(denominazione);
            return ResponseEntity.ok().body(res);
        } catch (Exception ex) {
            log.info("getDenominazioneResponsabileLike()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * chiamata per il recupero del codice e denominazione e data fine evento dalle Strutture destinatarie
     * @param denominazione 
     * @param request
     * @return ResponseEntity una lista di StrutturaDTO
     */
    @GetMapping(value = "/destinataria/{denominazione}",produces = {"application/json"})
    @Operation(summary = "ricerca denominazione struttura destinataria", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<StrutturaDTO>> getDenominazioneDestinatarieLike(
    		@PathVariable("denominazione") String denominazione,
            HttpServletRequest request) {

        log.info("getDenominazioneDestinatarieLike() init...");

        try {
            List <StrutturaDTO> res=anagrafeStrutturaService.getStrutturaDestinatariaLike(denominazione);
            return ResponseEntity.ok().body(res);
        } catch (Exception ex) {
            log.info("getDenominazioneDestinatarieLike()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }



}
