package bdi.azd.sistina.siaweb.controller;
//package bdi.azd.sia.siaweb.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.hibernate.annotations.Filter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import bdi.azd.sia.siaweb.dto.AttoreContrattoDTO;
//import bdi.azd.sia.siaweb.dto.ContrattoDTO;
//import bdi.azd.sia.siaweb.dto.ContrattoStrutturaDTO;
//import bdi.azd.sia.siaweb.dto.FornitoreContrattoDTO;
//import bdi.azd.sia.siaweb.dto.IntegrazioneFullDTO;
//import bdi.azd.sia.siaweb.dto.RiferimentoDTO;
//import bdi.azd.sia.siaweb.entity.Cronologia;
//import bdi.azd.sia.siaweb.exception.BadRequestException;
//import bdi.azd.sia.siaweb.exception.DataIntegrityException;
//import bdi.azd.sia.siaweb.exception.ResourceNotContentException;
//import bdi.azd.sia.siaweb.exception.ResourceNotFoundException;
//import bdi.azd.sia.siaweb.ldap.LDAPService;
//import bdi.azd.sia.siaweb.service.ContrattoService;
//import bdi.azd.sia.siaweb.service.DatiEssenzialiIService;
//import bdi.azd.sia.siaweb.service.FornitoreContrattoService;
//import bdi.azd.sia.siaweb.service.ModificaContrattoService;
//import bdi.azd.sia.siaweb.service.StoricoEssenzValService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//
//
//@RestController
//@RequestMapping("be/contratto")
//@Tag(name = "Contratto", description = "CRUD Contratto")
//@Slf4j
//@Filter(name = "LoggingFilter")
//public class ModificaContrattoController {
//
//	public final DatiEssenzialiIService datiEssenzialiIService;
//    public final ContrattoService contrattoService;
//    public final ModificaContrattoService modificaContrattoService;
//    public final FornitoreContrattoService fornitoreContrattoService;
//    public final LDAPService ldapService;
//    public final StoricoEssenzValService storicoEssenzValService;
//
//    public ModificaContrattoController(ContrattoService contrattoService, ModificaContrattoService modificaContrattoService, FornitoreContrattoService fornitoreContrattoService, DatiEssenzialiIService datiEssenzialiIService, LDAPService ldapService, StoricoEssenzValService storicoEssenzValService) {
//        this.datiEssenzialiIService = datiEssenzialiIService;
//        this.contrattoService = contrattoService;
//        this.modificaContrattoService = modificaContrattoService;
//        this.fornitoreContrattoService = fornitoreContrattoService;
//        this.ldapService = ldapService;
//        this.storicoEssenzValService = storicoEssenzValService;
//    }
//    
//   
//    /**
//     * Metodo per la modifica dei dati generali di un contratto. 
//     * @param id
//     * @return 
//     */
//    @PostMapping(value = "/modificaDatiDenerali", produces = {"application/json"})
//    @Operation(summary = "modifica dei dati generali di un contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200"),
//            @ApiResponse(responseCode = "404", description = "Not Found")})
//    public ResponseEntity<Void> modificaDatiGeneraliContratto(@RequestBody ContrattoDTO contrattoIn) {
//
//        log.info("modificaDatiGeneraliContratto() init...CIG Contratto[{}]", contrattoIn.getCig());
//
//        try {
//        	modificaContrattoService.modificaDatiGeneraliContratto(contrattoIn);
//        }catch(ResourceNotFoundException ex) {
//        	log.error("Errore in modificaDatiGeneraliContratto(DettaglioContrattoDTO)()..." + ex.getMessage(), ex);
//        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }catch(Exception ex) {
//        	log.error("Errore in modificaDatiGeneraliContratto(DettaglioContrattoDTO)()..." + ex.getMessage(), ex);
//        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//        
//        return ResponseEntity.ok().build();
//    }
//    
//    /**
//     * Metodo per modifica riferimento di un contratto. 
//     * @param id
//     * @return 
//     */
//    @PostMapping(value = "/modificaRiferimento", produces = {"application/json"})
//    @Operation(summary = "modifica riferimento di un contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200"),
//            @ApiResponse(responseCode = "404", description = "Not Found")})
//    public ResponseEntity<Void> modificaRiferimentoContratto(@RequestBody List<RiferimentoDTO> riferimentoDTO, @PathVariable("idContratto") Long idContratto) {
//
//        log.info("modificaRiferimentoContratto() init...");
//
//        try {
//        	modificaContrattoService.modificaRiferimentoContratto(riferimentoDTO,idContratto);
//        }catch(ResourceNotFoundException ex) {
//        	log.error("Errore in modificaRiferimentoContratto()..." + ex.getMessage(), ex);
//        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }catch(Exception ex) {
//        	log.error("Errore in modificaRiferimentoContratto()..." + ex.getMessage(), ex);
//        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//
//        return ResponseEntity.ok().build();
//    }
//    
//    
//    /**
//     * Metodo che modifica il fornitore legato al contratto
//     * @param DettaglioContrattoDTO
//     * @param request
//     * @return void
//     */
//
//    @PutMapping(value = "/modificaFornitoreContratto", produces = {"application/json"})
//    @Operation(summary = "modifica fornitore contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//    public ResponseEntity<Void> modificaFornitoreContratto(  
//    	    @RequestBody List<FornitoreContrattoDTO> fornitoreContrattoIn,  @PathVariable("idContratto") Long idContratto,
//            HttpServletRequest request) {
//
//        log.info("editFornitoreContratto() init...");
//
//        try {
//        	modificaContrattoService.modificaFornitoreContratto(fornitoreContrattoIn,idContratto);
//        	return ResponseEntity.ok().build();
//        } catch (ResourceNotContentException ex) {
//            log.info("updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.noContent().build();
//        } catch (BadRequestException ex) {
//            log.error("Errore in updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (DataIntegrityException ex) {
//            log.error("Errore in updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }
//    
//    /**
//     * Metodo per la modifica del id di procedura di un contratto 
//     * @param id
//     * @param contrattoDTO
//     * @param request
//     * @return l'oggetto con i campi modificati 
//     */
//
//    @PutMapping(value = "/modificaProceduraContratto", produces = {"application/json"})
//    @Operation(summary = "modifica id procedura contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//    public ResponseEntity<Void> modificaProceduraContratto(
//           
//            @RequestBody ContrattoDTO contrattoIn) {
//
//        log.info("updateFornitore() init...");
//
//        try {
//            modificaContrattoService.modificaProceduraContratto(contrattoIn);          
//        } catch (ResourceNotContentException ex) {
//            log.info("updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.noContent().build();
//        } catch (BadRequestException ex) {
//            log.error("Errore in updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (DataIntegrityException ex) {
//            log.error("Errore in updateFornitore()..." + ex.getMessage());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        return ResponseEntity.ok().build();
//    }
//    
//    
//    
//    /**
//     * Metodo che modifica le date della cronologia legata al contratto 
//     * @param cronologiaDTO
//     * @param request
//     * @return l'oggetto con i campi modificati 
//     */
//
//    @PutMapping(value = "/modificaDateCronologiaContratto", produces = {"application/json"})
//    @Operation(summary = "modifica cronologia contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//    public ResponseEntity<List<Cronologia>> modificaDateContratto(
//            @RequestBody ContrattoDTO contrattoIn,
//            HttpServletRequest request) {
//
//        log.info("init modificaDateContratto() init...");
//
//        try {
//        	 modificaContrattoService.modificaDateCronologiaContratto(contrattoIn.getCronologias(), contrattoIn.getIdContratto());
//        } catch (ResourceNotContentException ex) {
//            log.info("Errore in modificaDateContratto()..." + ex.getMessage());
//            return ResponseEntity.noContent().build();
//        } catch (BadRequestException ex) {
//            log.error("Errore in modificaDateContratto()..." + ex.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (DataIntegrityException ex) {
//            log.error("Errore in modificaDateContratto()..." + ex.getMessage());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        return ResponseEntity.ok().build();
//    }
//	/**
//	 * Metodo per la modifica dell'importo legato al contratto
//	 * @param importoDTO
//	 * @param request
//	 * @return l'oggetto con i campi modificati 
//	 */
//	@PutMapping(value = "/modificaImportoContratto", produces = {"application/json"})
//	@Operation(summary = "modifica importo contratto", responses = {
//			@ApiResponse(description = "Successful Operation", responseCode = "200",
//					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//	public ResponseEntity<Void> modificaImportoContratto(   
//			@RequestBody ContrattoDTO contrattoIn) {
//
//		log.info("modificaImportoContratto() init...");
//
//		try {
//			modificaContrattoService.modificaImportoContratto(contrattoIn);          
//		} catch (ResourceNotContentException ex) {
//			log.info("modificaImportoContratto()..." + ex.getMessage());
//			return ResponseEntity.noContent().build();
//		} catch (BadRequestException ex) {
//			log.error("Errore in modificaImportoContratto()..." + ex.getMessage());
//			return ResponseEntity.badRequest().build();
//		} catch (DataIntegrityException ex) {
//			log.error("Errore in modificaImportoContratto()..." + ex.getMessage());
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		return ResponseEntity.ok().build();
//	}
//
//    /**
//     * Update an already existing contrattoStruttura
//     *
//     * @param id        is the identifier of the contract-strutture to update
//     * @param contrattoStrutturaDTO is the request containing rules and role properties to
//     *                  update
//     */
//    @PutMapping(value = "/modificaContrattoStruttura")
//    @Operation(summary = "Update Contratto-Struttura", description = "On success a Contratto-Struttura is updated")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No content"),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
//            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
//            @ApiResponse(responseCode = "500", description = "Interval server error", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
//    })
//    public ResponseEntity<Void> modificaContrattoStruttura(
//             @RequestBody  List<ContrattoStrutturaDTO> contrattoStrutturaModifificaDTO,@PathVariable("idContratto") Long idContratto ) {
//    	
//    	
//    	log.info("modificaContrattoStruttura() init...");
//        
//		try {
//			modificaContrattoService.modificaContrattoStruttura(contrattoStrutturaModifificaDTO,idContratto);         
//		} catch (ResourceNotContentException ex) {
//			log.info("modificaContrattoStruttura()..." + ex.getMessage());
//			return ResponseEntity.noContent().build();
//		} catch (BadRequestException ex) {
//			log.error("Errore in modificaContrattoStruttura()..." + ex.getMessage());
//			return ResponseEntity.badRequest().build();
//		} catch (DataIntegrityException ex) {
//			log.error("Errore in modificaContrattoStruttura()..." + ex.getMessage());
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		return ResponseEntity.ok().build();
//   
//    }
//	
//	
//	/**
//	 * Metodo per la modifica l'attore legato al contratto
//	 * @param attoreContrattoModificaDTO
//	 * @return l'oggetto con i campi modificati 
//	 */
//	@PutMapping(value = "/modificaAttoreContratto", produces = {"application/json"})
//	@Operation(summary = "modifica attore contratto", responses = {
//			@ApiResponse(description = "Successful Operation", responseCode = "200",
//					content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//			@ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//	public ResponseEntity<Void> modificaAttoreContratto(   
//			@RequestBody List<AttoreContrattoDTO> attoreContrattoDTO,@PathVariable("idContratto") Long idContratto) {
//
//		log.info("modificaAttoreContratto() init...");
//
//		try {
//			modificaContrattoService.modificaAttoreContratto(attoreContrattoDTO,idContratto);          
//		} catch (ResourceNotContentException ex) {
//			log.info("modificaAttoreContratto()..." + ex.getMessage());
//			return ResponseEntity.noContent().build();
//		} catch (BadRequestException ex) {
//			log.error("Errore in modificaAttoreContratto()..." + ex.getMessage());
//			return ResponseEntity.badRequest().build();
//		} catch (DataIntegrityException ex) {
//			log.error("Errore in modificaAttoreContratto()..." + ex.getMessage());
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		return ResponseEntity.ok().build();
//	}
//	
//	
//	
//    
//    /**
//     * Metodo per la modifica del id di procedura di un contratto 
//     * @param id
//     * @param contrattoDTO
//     * @param request
//     * @return l'oggetto con i campi modificati 
//     */
//
//    @PutMapping(value = "/modificaIntegrazioneContratto", produces = {"application/json"})
//    @Operation(summary = "modifica integrazione contratto", responses = {
//            @ApiResponse(description = "Successful Operation", responseCode = "200",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "204", description = "Not Content", content = @Content),
//            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
//            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
//    public ResponseEntity<Void> modificaIntegrazioneContratto(
//           
//            @RequestBody List<IntegrazioneFullDTO> integrazioneModificaDTO,@PathVariable("idContratto") Long idContratto) {
//
//        log.info("modificaIntegrazioneContratto() init...");
//
//        try {
//            modificaContrattoService.modificaIntegrazione(integrazioneModificaDTO,idContratto);          
//        } catch (ResourceNotContentException ex) {
//            log.info("modificaIntegrazioneContratto()..." + ex.getMessage());
//            return ResponseEntity.noContent().build();
//        } catch (BadRequestException ex) {
//            log.error("Errore in modificaIntegrazioneContratto()..." + ex.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (DataIntegrityException ex) {
//            log.error("Errore in modificaIntegrazioneContratto()..." + ex.getMessage());
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        return ResponseEntity.ok().build();
//    }
//    
//
//    
//}
