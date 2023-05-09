package bdi.azd.sistina.siaweb.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.constants.CsvConstants;
import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.CheckValidazioneIDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoFilterDTO;
import bdi.azd.sistina.siaweb.dto.ContrattoScaduto;
import bdi.azd.sistina.siaweb.dto.ContrattoSummaryDTO;
import bdi.azd.sistina.siaweb.dto.CronologiaAggiungiDataFilterDTO;
import bdi.azd.sistina.siaweb.dto.DateContrattoDTO;
import bdi.azd.sistina.siaweb.dto.RicercaContrattoDTO;
import bdi.azd.sistina.siaweb.dto.StatoProcessoDTO;
import bdi.azd.sistina.siaweb.dto.StoricoEssenzValDTO;
import bdi.azd.sistina.siaweb.entity.Cronologia;
import bdi.azd.sistina.siaweb.exception.BadRequestException;
import bdi.azd.sistina.siaweb.exception.DataIntegrityException;
import bdi.azd.sistina.siaweb.exception.ResourceNotContentException;
import bdi.azd.sistina.siaweb.exception.ResourceNotFoundException;
import bdi.azd.sistina.siaweb.ldap.LDAPService;
import bdi.azd.sistina.siaweb.service.ContrattoService;
import bdi.azd.sistina.siaweb.service.DatiEssenzialiIService;
import bdi.azd.sistina.siaweb.service.FornitoreContrattoService;
import bdi.azd.sistina.siaweb.service.ModificaContrattoService;
import bdi.azd.sistina.siaweb.service.StoricoEssenzValService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("contratto")
@Tag(name = "Contratto", description = "CRUD Contratto")
@Slf4j
@Filter(name = "LoggingFilter")
public class ContrattoController {


    public final DatiEssenzialiIService datiEssenzialiIService;
    public final ContrattoService contrattoService;
    public final ModificaContrattoService modificaContrattoService;
    public final FornitoreContrattoService fornitoreContrattoService;
    public final LDAPService ldapService;
    public final StoricoEssenzValService storicoEssenzValService;

    public ContrattoController(ContrattoService contrattoService, ModificaContrattoService modificaContrattoService, FornitoreContrattoService fornitoreContrattoService, DatiEssenzialiIService datiEssenzialiIService, LDAPService ldapService, StoricoEssenzValService storicoEssenzValService) {
        this.datiEssenzialiIService = datiEssenzialiIService;
        this.contrattoService = contrattoService;
        this.modificaContrattoService = modificaContrattoService;
        this.fornitoreContrattoService = fornitoreContrattoService;
        this.ldapService = ldapService;
        this.storicoEssenzValService = storicoEssenzValService;
    }

    /**
     * Metodo per la modifica di un contratto.
     *
     * @param contrattoIn
     * @return
     */
    @PostMapping(value = "/modificaContratto", produces = {"application/json"})
    @Operation(summary = "modifica di un contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<Void> modificaContratto(@RequestBody ContrattoDTO contrattoIn, HttpServletRequest request) {

        log.info("modificaDatiGeneraliContratto() init...CIG Contratto[{}]", contrattoIn.getCig());

        try {
            modificaContrattoService.modificaContratto(contrattoIn);
        } catch (ResourceNotFoundException ex) {
            log.error("Errore in modificaContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in modificaContratto()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (ResourceNotContentException ex) {
	        log.error("Errore in modificaContratto()..." + ex.getMessage(), ex);
	        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (Exception ex) {
            log.error("Errore in modificaContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * chiamata per il recupero dei dati del dettaglioContratto
     *
     * @param request, codice del contratto
     * @return ResponseEntity<ContrattoDto>
     */
    @GetMapping(value = "/{codiceContratto}", produces = {"application/json"})
    @Operation(summary = "dettaglio Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<ContrattoDTO> getDettaglioContratto(
            HttpServletRequest request, @PathVariable("codiceContratto") String codiceContratto) {

        log.info("getDettaglioContratto() init...");

        try {
            ContrattoDTO contrattoDTO;

            contrattoDTO = contrattoService.getContratto(codiceContratto);
            return ResponseEntity.ok().body(contrattoDTO);
        } catch (ResourceNotContentException ex) {
            log.info("getDettaglioContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * chiamata per la ricerca di una lista di contratti
     *
     * @param request, contrattoFilterDTO
     * @return ResponseEntity<List < RicercaContrattoDTO>>
     */
    @PostMapping(value = "/ricercaContratto", produces = {"application/json"})
    @Operation(summary = "ricerca contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<Set<RicercaContrattoDTO>> searchContratti(
            @RequestBody ContrattoFilterDTO contrattoFilterDTO,
            HttpServletRequest request) {

        log.info("searchContratti() init...");

        try {
            Set<RicercaContrattoDTO> ricercaContrattoDTOS = contrattoService.findContratti(contrattoFilterDTO);

            return ResponseEntity.ok().body(ricercaContrattoDTOS);
        } catch (Exception ex) {
            log.info("searchContratti()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }


    /**
     * chiamata per il recupero dei contratti  dato uno stato processo
     *
     * @param request, statoProcessoDTO
     * @return ResponseEntity<ContrattoDto>
     */
    @PostMapping(value = "/ricercaContrattoConSp", produces = {"application/json"})
    @Operation(summary = "ricerca contratti by statoProcesso", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<List<ContrattoSummaryDTO>> getContrattiByStatoProcesso(
            @RequestBody StatoProcessoDTO statoProcessoDTO, HttpServletRequest request) {

        log.info("getContrattibyStatoProcesso() init...");

        try {
            List<ContrattoSummaryDTO> contrattiList = contrattoService.getContrattiByStatoProcesso(statoProcessoDTO);
            return ResponseEntity.ok().body(contrattiList);
        } catch (BadRequestException ex) {
            log.error("Errore in getContrattibyStatoProcesso()..." + ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in getContrattibyStatoProcesso()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    /**
     * chiamata per l'export della lista contratti dato uno stato processo
     *
     * @param request, contrattoSummaryDTOList
     * @return ResponseEntity<String>
     */
    @PostMapping("/downloadContrattiByStatoProcesso")
    @Operation(summary = "download contratti by statoProcesso", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<String> downloadContrattiByStatoProcesso(
            @RequestBody List<ContrattoSummaryDTO> contrattoSummaryDTOList, HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CsvConstants.CSV_CONTRATTI_BY_STATOPROCESSO + ".csv");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.valueOf("text/csv"))
                .body(contrattoService.downloadContrattiByStatoProcesso(contrattoSummaryDTOList));
    }

    /**
     * Metodo per l'inserimento di un contratto
     *
     * @param inputContrattoDTO
     * @return l'oggetto inserito con l'id generato
     */
    @PostMapping()
    @Operation(summary = "inserisci contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContrattoDTO.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    public ResponseEntity<ContrattoDTO> insertContratto(@RequestBody ContrattoDTO inputContrattoDTO, HttpServletRequest request) {
        log.info("insertContratto() init...");
        // TODO Ritornare solo ID Contratto -> ResponseEntity<Long>
        ContrattoDTO outputContrattoDTO = null;
        try {
            outputContrattoDTO = contrattoService.insertContratto(inputContrattoDTO);
        } catch (BadRequestException ex) {
            log.error("Errore in insertContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in insertContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception ex) {
            log.error("Errore in insertContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(outputContrattoDTO);
    }

    /**
     * Metodo per l'eliminazione di un contratto.
     *
     * @param id
     * @param request
     * @return
     */

    @DeleteMapping("/{id}")
    @Operation(summary = "cancella contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content)})
    public ResponseEntity<Void> deleteContratto(
            @PathVariable("id") long id,
            HttpServletRequest request) {

        log.info("deleteContratto() init...idContratto[{}]", id);

//      //controllo se l'utente è abilitato alla cancellazione
//      if(!RuoliUtil.checkRuolo("SISTINA_GESTORE")) {
//      	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//      }

        // Procedo con la cancellazione del Contratto
        contrattoService.deleteContratto(id);

        return ResponseEntity.ok().build();
    }


    /**
     * Metodo per la validazione di un contratto.
     * Valido solo per la Fase 1
     *
     * @param id
     * @return
     */
    @PostMapping("/valida/{id}")
    @Operation(summary = "valida contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<Void> validaContratto(@PathVariable("id") long id, HttpServletRequest request) {

        log.info("validaContratto() init...idContratto[{}]", id);

        try {
            datiEssenzialiIService.validaContratto(id);
        } catch (ResourceNotFoundException ex) {
            log.error("Errore in validaContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error("Errore in validaContratto()..." + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Metodo per la validazione di Primo Livello di un contratto.
     *
     * @param id
     * @return
     */
    @PostMapping("/validazioneI/{id}")
    @Operation(summary = "valida contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<CheckValidazioneIDTO> validaContrattoI(@PathVariable("id") long id, HttpServletRequest request) {

        log.info("validaContrattoI() init...idContratto[{}]", id);

        CheckValidazioneIDTO isValid = null;
        try {
            isValid = datiEssenzialiIService.validaContrattoI(id);
        } catch (Exception ex) {
            log.error("Errore in validaContrattoI(): " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().body(isValid);
    }

    /**
     * Metodo per il controllo della validazione di Primo Livello di un contratto.
     *
     * @param id
     * @return
     */
    @PostMapping("/valida/check/{id}")
    @Operation(summary = "Controllo di Validazione di Primo Livello di un Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<CheckValidazioneIDTO> checkValidaContrattoI(@PathVariable("id") long id, HttpServletRequest request) {

        log.info("validaContrattoI() init...idContratto[{}]", id);

        CheckValidazioneIDTO isValid = null;
        try {
            isValid = datiEssenzialiIService.checkDatiEssenzialiI(id);
        } catch (Exception ex) {
            log.error("Errore in checkValidaContrattoI(): " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().body(isValid);
    }

    /**
     * chiamata per il recupero dei dati del dettaglioContratto
     *
     * @param request, codice del contratto
     * @return ResponseEntity<ContrattoDto>
     */
    @GetMapping(value = "/ricercaCigPadre/{cigContratto}", produces = {"application/json"})
    @Operation(summary = "dettaglio Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<RicercaContrattoDTO>> getRicercaCigFigli(
            HttpServletRequest request, String cigContratto) {

        log.info("getRicercaCigFigli() init...");

        try {

            List<RicercaContrattoDTO> ricContrattoDTO = contrattoService.getRicercaCigPadre(cigContratto);
            return ResponseEntity.ok().body(ricContrattoDTO);
        } catch (Exception ex) {
            log.info("getDettaglioContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }


    /**
     * chiamata per il recupero dei rup nominativo nella tabella attore contratto
     *
     * @param request, nominativo
     * @return ResponseEntity list di String contenente i nominativi
     */
    @GetMapping(value = "/rupNominativo/{nominativo}", produces = {"application/json"})
    @Operation(summary = "ricerca rupNominativo autocomplete", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<String>> getRupNominativo(
            HttpServletRequest request, @PathVariable("nominativo") String nominativo) {

        log.info("getRupNominativo() init...");

        try {

            List<String> nominativiList = contrattoService.getRupNominativoLike(nominativo);
            return ResponseEntity.ok().body(nominativiList);
        } catch (ResourceNotContentException ex) {
            log.info("getRupNominativo()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }


    @Operation(summary = "Retrieves the contracts of a specific range date", responses =
            {@ApiResponse(responseCode = "200", description = "I contratti  sono stati trovati"),
                    @ApiResponse(responseCode = "404", description = "Non è stato trovato alcun contratti "),
                    @ApiResponse(responseCode = "403", description = "Non sei AUTORIZZATO ad accedere alle informazioni"),
                    @ApiResponse(responseCode = "401", description = "Non sei AUTENTICATO"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    // ------------------- Search contracts By range Date  ------------------------------------//
    @GetMapping(value = "/contrattiInScadenza", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContrattoScaduto>> getContractByRangeDate(@RequestParam(defaultValue = "0", value = "page") Integer page,
                                                                         @RequestParam(value = "limit") Integer limit
    ) {

        Pageable pageable = limit > 0 ? PageRequest.of(page, limit) : Pageable.unpaged();
        return ResponseEntity.ok().body(contrattoService.getContractByRangeDate(pageable));
    }


    /**
     * chiamata per il recupero dei userID nella tabella attore contratto
     *
     * @param request, userId
     * @return ResponseEntity list di String contenente gli userId
     */
    @GetMapping(value = "/getListUserId/{userid}", produces = {"application/json"})
    @Operation(summary = "ricerca userId autocomplete", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<String>> getListUserId(
            HttpServletRequest request, @PathVariable("userid") String userid) {

        log.info("getListUserId() init...");

        try {

            List<String> userIdList = contrattoService.getListUserId(userid);
            return ResponseEntity.ok().body(userIdList);
        } catch (Exception ex) {
            log.info("getListUserId()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * chiamata per l'export della lista contratti in scadenza
     *
     * @param request, lista contratti scaduti
     * @return ResponseEntity<String>
     */
    @PostMapping("/downloadContrattiInScadenza")
    @Operation(summary = "download contratti in scadenza", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<String> downloaContrattiByStatoProcesso(
            @RequestBody List<ContrattoScaduto> contrattoScadutiList, HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CsvConstants.CSV_CONTRATTI_IN_SCADENZA + ".csv");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.valueOf("text/csv"))
                .body(contrattoService.downloadContrattiInScadenza(contrattoScadutiList));
    }


    /**
     * Metodo che inserisce le date della cronologia legata al contratto
     *
     * @param contrattoIn
     * @param request
     * @return l'oggetto con i campi modificati
     */

    @PutMapping(value = "/aggiungiData", produces = {"application/json"})
    @Operation(summary = "aggiungi data cronologia contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<List<Cronologia>> aggiungiData(
            @RequestBody CronologiaAggiungiDataFilterDTO contrattoIn,
            HttpServletRequest request) {

        log.info("init modificaDateContratto() init...");

        try {
            List<Cronologia> cronologia = contrattoService.aggiungiDateCronologiaContratto(contrattoIn);
            return ResponseEntity.ok().build();
        } catch (ResourceNotContentException ex) {
            log.info("Errore in modificaDateContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        } catch (BadRequestException ex) {
            log.error("Errore in modificaDateContratto()..." + ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (DataIntegrityException ex) {
            log.error("Errore in modificaDateContratto()..." + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }


    /**
     * chiamata per il recupero degli attori LDAP
     *
     * @param request, nominativo
     * @return ResponseEntity list di String contenente gli userId
     */
    @GetMapping(value = "/getListAttoriLdap/{nominativo}", produces = {"application/json"})
    @Operation(summary = "ricerca nominativo Ldap autocomplete", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<AttoreDTO>> getNominativoLDAP(
            HttpServletRequest request, @PathVariable("nominativo") String nominativo) {

        log.info("getNominativoLDAP() init...");

        try {

            List<AttoreDTO> attoriList = ldapService.getAttori(nominativo);
            return ResponseEntity.ok().body(attoriList);
        } catch (ResourceNotContentException ex) {
            log.info("getNominativoLDAP()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping(value = "/getDateContratto/{idContratto}", produces = {"application/json"})
    @Operation(summary = "date Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<DateContrattoDTO> getDateContratto(
            @PathVariable("idContratto") long idContratto, HttpServletRequest request) {

        log.info("getDateContratto() init...");

        try {

            DateContrattoDTO dto = contrattoService.getDateContratto(idContratto);
            return ResponseEntity.ok().body(dto);
        } catch (Exception ex) {
            log.info("getDateContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * chiamata per l'export della lista contratti dalla ricerca
     *
     * @param request, contrattoSummaryDTOList
     * @return ResponseEntity<String>
     */
    @PostMapping("/downloadContrattiRicerca")
    @Operation(summary = "download contratti ricerca", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    public ResponseEntity<String> downloadContrattiRicerca(
            @RequestBody List<RicercaContrattoDTO> ricercaContrattoDTOList, HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CsvConstants.CSV_CONTRATTI_BY_RICERCA + ".csv");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.valueOf("text/csv"))
                .body(contrattoService.downloadContrattiByRicerca(ricercaContrattoDTOList));
    }

    /**
     * chiamata per il recupero dei dati della tabella storicoEssenzVal
     *
     * @param request
     * @param idContratto
     * @return StoricoEssenzValDTO
     */
    @GetMapping(value = "/getDatiPrecedenti/{idContratto}", produces = {"application/json"})
    @Operation(summary = "date Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<List<StoricoEssenzValDTO>> getDatiPrecedenti(
            @PathVariable("idContratto") long idContratto, HttpServletRequest request) {

        log.info("getDatiPrecedenti() init...");

        try {
            List<StoricoEssenzValDTO> dtoList = storicoEssenzValService.getStoricoEssenzVal(idContratto);
            return ResponseEntity.ok().body(dtoList);
        } catch (Exception ex) {
            log.info("getDateContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Metodo per la modifica di un record nella tabella storico essenz val.
     *
     * @param storicoEssenzValDTO dati essenziali storico
     * @return
     */
    @PostMapping("/updateDatiPrecedenti")
    @Operation(summary = "Controllo di Validazione di Primo Livello di un Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<Void> updateDatiPrecedenti(@RequestBody List<StoricoEssenzValDTO> storicoEssenzValDTO, HttpServletRequest request) {

        try {
            storicoEssenzValDTO.stream().forEach(storicoEssenzValDTO1 -> {
                storicoEssenzValService.updateStoricoEssenzVal(storicoEssenzValDTO1);
            });
        } catch (Exception ex) {
            log.error("Errore in updateDatiPrecedenti(): " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }
    
    /**
     * chiamata per la verifica esistenza di un contratto
     *
     * @param request, cig contratto
     * @return ResponseEntity<ContrattoDto>
     */
    @GetMapping(value = "verificaCig/{cig}", produces = {"application/json"})
    @Operation(summary = "esistenza Contratto", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content)})

    public ResponseEntity<Boolean> verificaCig(
            HttpServletRequest request, @PathVariable("cig") String cig) {

        log.info("getEsistenzaContratto() init...");

        try {
            boolean contratto= contrattoService.getEsistenzaContratto(cig);
            return ResponseEntity.ok().body(contratto);
        } catch (ResourceNotContentException ex) {
            log.info("getEsistenzaContratto()..." + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
    
    
    
//    @PostMapping(value = "/modificaIntegrazioneContratto", produces = {"application/json"})
//    @Operation(summary = "modifica di un integrazione contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200"),
//            @ApiResponse(responseCode = "404", description = "Not Found")})
//    public ResponseEntity<Void> modificaIntegrazioneContratto(@RequestBody ContrattoIntegrazioneDTO contrattoIn) {
//
//        log.info("modificaIntegrazioneContratto() init...CIG Contratto[{}]", contrattoIn.getCig());
//
//        try {
//            modificaContrattoService.modificaContratto(contrattoIn);
//        } catch (ResourceNotFoundException ex) {
//            log.error("Errore in modificaIntegrazioneContratto()..." + ex.getMessage(), ex);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception ex) {
//            log.error("Errore in modificaIntegrazioneContratto()..." + ex.getMessage(), ex);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//        return ResponseEntity.ok().build();
//    }
}
