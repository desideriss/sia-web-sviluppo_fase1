package bdi.azd.sistina.siaweb.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import bdi.azd.sistina.siaweb.ldap.LDAPService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    public LDAPService ldapService;
	
	@Value("${environment}")
	private String environment;
	
  /**
   * Cerca l'utente nel database LDAP
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  log.debug("loadUserByUsername()");
	  UserDTO loggedUser = null;
	  try {
		  if(username != null && !username.equalsIgnoreCase("admin") && (environment.equalsIgnoreCase("prod")|| environment.equalsIgnoreCase("simulaLdap"))) { // LDAP
			  loggedUser = ldapService.getUser(username);
			  if(loggedUser == null) {
				  log.error("Errore in loadUserByUsername()... Utente non trovato: " + username);
				  throw new UsernameNotFoundException("Utente non trovato: " + username);
			  }
		  }else { // Blocco per TEST in ambiente di sviluppo
			  log.debug("Username: " + username);
			  if(username != null) {
			  if(username.equals("admin")) {
				  loggedUser = new UserDTO();
				  loggedUser.setNome("Mario");
				  loggedUser.setCognome("Rossi");
				  loggedUser.setId(username);
				  List<RuoloDTO> ruoli = new ArrayList<>();
				  RuoloDTO ruoloDTO_1 = new RuoloDTO();
				  ruoloDTO_1.setCodice("SISTINA_VALIDATORE");
				  ruoloDTO_1.setDescrizione("Validatore");
				  ruoloDTO_1.setStrutture(Arrays.asList(new String[] { "861" }));
				  ruoli.add(ruoloDTO_1);
				  loggedUser.setRuoli(ruoli);
			  }else	if(username.equals("gestore")) {  
				  List<RuoloDTO> ruoli = new ArrayList<>();
				  loggedUser = new UserDTO();
				  loggedUser.setNome("Sandra");
				  loggedUser.setCognome("Milo");
				  loggedUser.setId(username);
				  RuoloDTO ruoloDTO_2 = new RuoloDTO();
				  ruoloDTO_2.setCodice("SISTINA_GESTORE");
				  ruoloDTO_2.setDescrizione("Gestore");
				  ruoloDTO_2.setStrutture(Arrays.asList(new String[] { "000" }));
//				  ruoli.add(ruoloDTO_1);
				  ruoli.add(ruoloDTO_2);
				  loggedUser.setRuoli(ruoli);
			  }else if(username.equals("bob")) {
				  loggedUser = new UserDTO();
				  loggedUser.setNome("Satoshi");
				  loggedUser.setCognome("Nakamoto");
				  loggedUser.setId(username);
				  List<RuoloDTO> ruoli = new ArrayList<>(2);
				  RuoloDTO ruoloDTO_1 = new RuoloDTO();
				  ruoloDTO_1.setCodice("SISTINA_VISUALIZZATORE");
				  ruoloDTO_1.setDescrizione("Visualizzatore");
				  ruoloDTO_1.setStrutture(Arrays.asList(new String[] { "444", "555", "222" }));
				  ruoli.add(ruoloDTO_1);
				  loggedUser.setRuoli(ruoli);
			  }else {
				  throw new UsernameNotFoundException("Utente non trovato: " + username);
			  }
			  }
		  }
	  }catch(Exception e) {
		  log.error("Errore in authenticateUser()..." + e.getMessage());
		  throw new UsernameNotFoundException("Utente non trovato o non valido: " + username, e);
	  }
	  log.debug("loadUserByUsername:\n" + loggedUser);
	  return UserDetailsImpl.build(loggedUser);
  }

}
