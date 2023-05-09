package bdi.azd.sistina.siaweb.auth;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import bdi.azd.sistina.siaweb.entity.JWTToken;
import bdi.azd.sistina.siaweb.ldap.LDAPService;
import bdi.azd.sistina.siaweb.repository.AuthRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("auth")
@Tag(name = "Autenticazione", description = "Autenticazione dell'utente tramite JWT Token e Spring Security.")
@Filter(name = "LoggingFilter")	
@Slf4j
public class AuthController {
	
	@Autowired
    public LDAPService ldapService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthRepo authRepo;


	/**
	 * Da rimuovere serve solo per i test via GET e non POST
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Operation(summary = "Simulazione della chiamata POST. Solo per TEST!", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
	@GetMapping("/getUserGET")
	public ResponseEntity<UserDTO> authenticateUser_GET(@RequestParam String username, @RequestParam String password) {
		return authenticateUser(new LoginRequest(username, password));
	}
	
	/**
	 * Da rimuovere. Solo per TEST
	 * 
	 * @param request
	 * @return
	 */
	@Operation(summary = "Verifica autenticazione e ruoli. Solo per TEST! Potrebbe diventare una getUserDetails (o getUser) se serve al Front End.", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
	@GetMapping("/getUserDEMO")
	public ResponseEntity<?> demo(HttpServletRequest request) {
		log.info("demo()");
        Principal loggedUserId = request.getUserPrincipal();
        // Per recuperare i dati dell'utente loggato in una sezione che ha bisogno di autenticazione utilizzare le
        // seguenti righe di codice
        // NOTA: Se la url si trova nella lista delle public la classe ritornata non è UsernamePasswordAuthenticationToken
        // ma AnonymousAuthenticationToken
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("AUTH: " + authentication.getClass());
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
        	UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        	UserDTO userDTO = (UserDTO)currentUser;
        	log.info("UserDTO: " + userDTO);
        	// Chechk Ruoli
        	// 1) Prendere i Ruoli dall'oggetto UserDTO
        	List<RuoloDTO> ruoli = userDTO.getRuoli();
        	log.info("Ruoli 1): " + ruoli);
        	// 2) In alternativa per utilizzare le GRANT da Spring Security:
        	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)authentication.getAuthorities();
        	for(GrantedAuthority gauth : authorities) {
                String ruolo = gauth.getAuthority();
                log.info("Ruoli 2): " + ruolo);
            }
        	return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	/**
	 * Autenticazione (Login) utente.
	 * 
	 * @param loginRequest Username e Password
	 * @return JWT Token o Exception
	 */
	@Operation(summary = "Autenticazione dell'utente", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
	@PostMapping("/getUserJWT")
	public ResponseEntity<UserDTO> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("authenticateUser() init..." + loginRequest.getUsername());
		Authentication authentication = null;
        try {
	   		// Check User in UserDetailsServiceImpl.loadUserByUsername
        	// NOTA: L'autenticazione è effettuata via LDAP, non si conosce la password dell'utente quindi non si può effettuare un secondo
        	// controllo tramite le API Security di Spring
	   		//authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        	authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), "PreAuthenticated"));
        }catch(DisabledException e) {
        	log.error("Errore in authenticateUser()..." + e.getMessage());
            throw new DisabledException("Utente non abilitato", e);
        }catch(BadCredentialsException e) {
        	log.error("Errore in authenticateUser()..." + e.getMessage());
            throw new BadCredentialsException("Credenziali non valide", e);
        }
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		UserDTO userDTO = (UserDTO)userDetails;
		userDTO.setAuthToken(jwt);
		log.info("L'utente " + userDTO.getNome() + " " + userDTO.getCognome() + " ha effettuato il login.");
		// Salvo il JWT Token nel database che sarà utilizzato per invalidare il Token
		JWTToken token = new JWTToken();
		token.setJwtToken(jwt);
		token.setValido(Boolean.TRUE);
		authRepo.save(token);
		return ResponseEntity.ok(userDTO);
	}

	/**
	 * Logout, invalidazione del Token JWT
	 * 
	 * @param jwtToken parametro HttpHeaders.AUTHORIZATION
	 * @return OK
	 */
	@Operation(summary = "Logout dell'utente", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
	@GetMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		if(jwtToken != null) {
			try {
				jwtToken = jwtToken.replace("Bearer", "").trim();
				JWTToken dbToken = authRepo.findByJwtToken(jwtToken);
				if(dbToken != null) {
					dbToken.setValido(Boolean.FALSE);
					authRepo.save(dbToken);
					// Oppure
					//authRepo.delete(dbToken); // I records vengono cancellati durante la fase di generazione della nuova chiave (JWTScheduledTasks)
					return ResponseEntity.status(HttpStatus.OK).build();
				}
			}catch(Exception e) {
				log.error("", e);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@Operation(summary = "SSO Banca?")
	@GetMapping("/getUser")
	public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
		String loggedUserId = "admin";
		if(request.getUserPrincipal() != null) {
			loggedUserId = request.getUserPrincipal().getName();
		}
		return this.authenticateUser(new LoginRequest(loggedUserId, null));
/*
		else {
			// TODO: simulazione ambienti dedagroup
			loggedUserId = "1";
		}

		UserDTO loggedUserDTO = UserDTO.builder().id(loggedUserId).build();

		// TODO: simulazione ambienti dedagroup
		if (loggedUserId.equals("1")) {
			loggedUserDTO.setNome("Nome");
			loggedUserDTO.setCognome("Cognome");
		} else {
			loggedUserDTO.setNome("Principal");
			loggedUserDTO.setCognome("Ok");
		}
		List<RuoloDTO> ruoli = new ArrayList<RuoloDTO>();
		List<String> strutture = new ArrayList<String>();
		strutture.add("191");
		RuoloDTO ruolo1 = new RuoloDTO("SYSTEM_OWNER", "system owner",strutture);
		ruoli.add(ruolo1);
		loggedUserDTO.setRuoli(ruoli);
		loggedUserDTO.setAuthToken("1837734dhjdhh3838");


		log.info(loggedUserId);
		return ResponseEntity.ok(loggedUserDTO);
*/
	}
	
	@GetMapping("/getUserV")
    public ResponseEntity<UserDTO> getUserV(HttpServletRequest request) {
        String loggedUserId = request.getUserPrincipal().getName();
        log.info(loggedUserId); 
        //UserDTO loggedUser = ldapService.getUser(loggedUserId);
        return this.authenticateUser(new LoginRequest(loggedUserId, null));
    }

}
