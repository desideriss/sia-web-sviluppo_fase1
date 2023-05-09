package bdi.azd.sistina.siaweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.dto.ProceduraDTO;
import bdi.azd.sistina.siaweb.exception.BadRequestException;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.service.ProceduraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("procedura")
@Tag(name = "Procedura", description = "CRUD Procedura")
@Slf4j
@Filter(name = "LoggingFilter")
public class ProceduraController {

    
    public final ProceduraService proceduraService;


    public ProceduraController(ProceduraService proceduraService) {
        this.proceduraService=proceduraService;
    }

   
    
    /**
     * chiamata per il recupero dei dati della procedura dato un codiceProcedura
     * @param request, codice della procedura
     * @return ResponseEntity<ProceduraDTO>
     */
    @GetMapping(value = "/{codiceProcedura}",produces = {"application/json"})
    @Operation(summary = "procedura", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<ProceduraDTO> getProceduraByCodiceProcedura(
            HttpServletRequest request,@PathVariable("codiceProcedura") String codiceProcedura) {

        log.info("getProceduraByCodiceProcedura() init...");

        try {
            ProceduraDTO proceduraDTO= proceduraService.getProceduraByCodiceProcedura(codiceProcedura);
            return ResponseEntity.ok().body(proceduraDTO);
        } catch (Exception ex) {
            log.info("getProceduraByCodiceProcedura()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * Metodo per l'inserimento di una procedura 
     * @param proceduraDTO
     * @param request
     * @return l'oggetto inserito con l'id generato
     */

    @PostMapping()
    @Operation(summary = "inserisci procedura", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<ProceduraDTO> insertProcedura(
            @RequestBody ProceduraDTO proceduraDTOIn, HttpServletRequest request) {

        log.info("insertProcedura() init...");

        try {
            ProceduraDTO proceduraDTO = proceduraService.insertProcedura(proceduraDTOIn);
            return ResponseEntity.ok().body(proceduraDTO);
        } catch (BadRequestException ex) {
            log.error("Errore in insertProcedura()..." + ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in insertProcedura()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    
    @GetMapping(value = "/getAllProcedure",produces = {"application/json"})
    @Operation(summary = "getAllProcedure", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<ProceduraDTO>> getAllProcedure(HttpServletRequest request) {

        log.info("getAllProcedure() init...");

        try {
            List<ProceduraDTO> proceduraDTO= proceduraService.getAllProcedure();
            return ResponseEntity.ok().body(proceduraDTO);
        } catch (Exception ex) {
            log.info("getAllProcedure()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    
    @GetMapping(value = "/getProcedureLikeCod/{codiceProcedura}",produces = {"application/json"})
    @Operation(summary = "getProcedureLikeCod", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<ProceduraDTO>> getProcedureLikeCod(HttpServletRequest request, @PathVariable("codiceProcedura") String codiceProcedura) {

        log.info("getProcedureLikeCod() init...");

        try {
            List<ProceduraDTO> proceduraDTO= proceduraService.getProceduraByCodiceProceduraLike(codiceProcedura);
            return ResponseEntity.ok().body(proceduraDTO);
        } catch (Exception ex) {
            log.info("getProcedureLikeCod()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    
}
