package bdi.azd.sistina.siaweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.constants.ErrorMessage;
import bdi.azd.sistina.siaweb.dto.FornitoreDTO;
import bdi.azd.sistina.siaweb.dto.FornitoreFilterDTO;
import bdi.azd.sistina.siaweb.exception.BadRequestException;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.service.FornitoreContrattoService;
import bdi.azd.sistina.siaweb.service.FornitoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("fornitori")
@Tag(name = "Fornitore", description = "CRUD Fornitore")
@Slf4j
@Filter(name = "LoggingFilter")
public class FornitoreController {

    public final FornitoreService fornitoreService;

    public final FornitoreContrattoService fornitoreContrattoService;

    public FornitoreController(FornitoreService fornitoreService, FornitoreContrattoService fornitoreContrattoService) {
        this.fornitoreService = fornitoreService;
        this.fornitoreContrattoService = fornitoreContrattoService;
    }

    /**
     * Metodo per la ricerca di un fornitore 
     * @param fornitoreFilterDTO
     * @param request
     * @return una lista di fornitoriDTO
     */
    @GetMapping(produces = {"application/json"})
    @Operation(summary = "ricerca fornitori", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<FornitoreDTO>> searchFornitori(
            FornitoreFilterDTO fornitoreFilterDTO,
            HttpServletRequest request) {

        log.info("searchFornitori() init...");

        try {
            List<FornitoreDTO> fornitoreDTOS;
            if(fornitoreFilterDTO != null)
                fornitoreDTOS = fornitoreService.findFornitori(fornitoreFilterDTO);
            else
                fornitoreDTOS = fornitoreService.getListaFornitori();
            return ResponseEntity.ok().body(fornitoreDTOS);
        } catch (Exception ex) {
            log.info("searchFornitori()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * Metodo per l'inserimento di un fornitore 
     * @param fornitoreDTO
     * @param request
     * @return l'oggetto inserito con l'id generato
     */

    @PostMapping()
    @Operation(summary = "inserisci fornitore", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<FornitoreDTO> insertFornitori(
            @RequestBody FornitoreDTO fornitoreDTO, HttpServletRequest request) {

        log.info("insertFornitori() init...");

        try {
            FornitoreDTO fornitoreDTOS = fornitoreService.saveFornitori(fornitoreDTO);
            return ResponseEntity.ok().body(fornitoreDTOS);
        } catch (BadRequestException ex) {
            log.error("Errore in insertFornitori()..." + ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in insertFornitori()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    /**
     * Metodo per la modifica di un fornitore precedentemente inserito 
     * @param id
     * @param fornitoreDTO
     * @param request
     * @return l'oggetto con i campi modificati 
     */

    @PutMapping(value = "/{id}", produces = {"application/json"})
    @Operation(summary = "modifica fornitore", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<FornitoreDTO> updateFornitore(
            @PathVariable("id") long id,
            @RequestBody FornitoreDTO fornitoreDTO,
            HttpServletRequest request) {

        log.info("updateFornitore() init...");

        try {
            FornitoreDTO fornitore = fornitoreService.updateFornitore(id, fornitoreDTO);
            return new ResponseEntity<>(fornitore, HttpStatus.OK);
        } catch (ResourceNotContentException ex) {
            log.info("updateFornitore()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        } catch (BadRequestException ex) {
            log.error("Errore in updateFornitore()..." + ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in updateFornitore()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    
    /**
     * Metodo per l'eliminazione di un fornitore. Prima dell'eliminazione verifica che non sia collegato a contratti
     * @param id
     * @param request
     * @return 
     */

    @DeleteMapping(value="/{id}")
    @Operation(summary = "cancella fornitore", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content)})
    public ResponseEntity<Void> deleteFornitore(
            @PathVariable("id") Long id,
            HttpServletRequest request) {

        log.info("deleteFornitore() init...idFornitore[{}]", id);

        // Verifico che il fornitore non sia collegato a qualche contratto
        if (fornitoreContrattoService.getCountFornitoreContrattoByIdFornitore(id) > 0) {
            log.info("deleteFornitore() ...idFornitore[{}] - {}", id, ErrorMessage.FORNITORE_NOT_DELETED);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }

        // Procedo con la cancellazione del Fornitore
        FornitoreDTO fornitoreDTO = fornitoreService.findByIdFornitore(id);
        if (fornitoreDTO != null)
            fornitoreService.deleteFornitore(fornitoreDTO);

        return ResponseEntity.ok().build();
    }
    
    /**
     * Metodo per l'autocomplete della ragione sociale
     * @param ragione sociale
     * @param request
     * @return una lista di FornitoreDTO
     */
    @GetMapping(value = {"/{ragioneSociale}"},produces = {"application/json"})
    @Operation(summary = "ricerca ragione sociale", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<FornitoreDTO>> searchRagioneSociale(
            @PathVariable("ragioneSociale")String ragioneSociale,
            HttpServletRequest request) {

        log.info("searchRagioneSociale() init...");

        try {
            List<FornitoreDTO>ragioneSocialeList=fornitoreService.findRagioneSociale(ragioneSociale, true);
            return ResponseEntity.ok().body(ragioneSocialeList);
        } catch (Exception ex) {
            log.info("searchRagioneSociale()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    @GetMapping(value = {"/exist/{ragioneSociale}"},produces = {"application/json"})
    public ResponseEntity<List<FornitoreDTO>> verifyExistRagioneSociale(
            @PathVariable("ragioneSociale")String ragioneSociale,
            HttpServletRequest request) {

        log.info("searchRagioneSociale() init...");

        try {
            List<FornitoreDTO>ragioneSocialeList=fornitoreService.findRagioneSociale(ragioneSociale,false);
            return ResponseEntity.ok().body(ragioneSocialeList);
        } catch (Exception ex) {
            log.info("searchRagioneSociale()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

}
