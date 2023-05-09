package bdi.azd.sistina.siaweb.ldap;

import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class LDAPServiceImpl implements LDAPService {
		
	private static final String STAGE_TEST = "Test";
	private static final String STAGE_CERT = "Cert";
	private static final String STAGE_PROD = "Prod";
	private static final String CURRENT_STAGE = System.getProperty("sistina.environment", STAGE_PROD);
	
	private static final String SISTINA_RUOLI_PREFIX = "SISTINA_";
	private static final String SISTINA_RUOLI_PATTERN_RUOLO = "((?!SISTINA_VALIDATORE|SISTINA_UTENTE).*)$";
	private static final String SISTINA_RUOLI_PATTERN_RUOLO_STRUTTURA = "^(SISTINA_UTENTE|SISTINA_VALIDATORE)-(.*$)";
	
	private static final String SISTINA_RUOLO_TECNICO_INTERNO = "SISTINA_TECNICO_INTERNO";
	private static final String SISTINA_RUOLO_TECNICO_ESTERNO = "SISTINA_TECNICO_ESTERNO";
	private static final String SISTINA_RUOLO_GESTORE = "SISTINA_GESTORE";
	private static final String SISTINA_RUOLO_VISUALIZZATORE = "SISTINA_VISUALIZZATORE";
	
	private static final String LDAP_CONNECTION_JNDI = "java:/global/LDAPConnection";
	private static final String LDAP_CONTEXT_ROOT = "OU=RisorseUtente,DC=UTENZE,DC=bankit,DC=it";
	private static final String LDAP_ATTRBS_GIVENNAME = "givenName";
	private static final String LDAP_ATTRBS_SN = "sn";
	private static final String LDAP_ATTRBS_MEMBEROF = "memberOf";
	private static final String LDAP_ATTRBS_DESCRIPTION = "description";
	private static final String LDAP_ATTRBS_CN = "CN";
	private static final String LDAP_ATTRBS_OU = "OU";
	private static final String LDAP_ATTRBS_DISPLAYNAME = "displayName";
	private static final String LDAP_ATTRBS_MAIL = "mail";
	
	@Value("${environment}")
	private String environment;
    
	/**
	 * @param userId
	 * @return null in caso di errori di connessione. 
	 * Altrimenti dati dell'utente associato allo userId in input. 
	 * synchronized non necessario : un nuovo InitialLdapContext e' creato per ogni richiesta (https://docs.oracle.com/javase/tutorial/jndi/ldap/faq.html)
	 */
	public UserDTO getUser(String userId) {
		log.debug("Inizio metodo getUser");
		DirContext ldapContext = null;
		NamingEnumeration<SearchResult> enumeration = null;
		
		try {

			ldapContext = (DirContext)new InitialLdapContext().lookup(LDAP_CONNECTION_JNDI);

			String filters = "CN={0}";
			String[] attributes = { LDAP_ATTRBS_GIVENNAME, LDAP_ATTRBS_SN, LDAP_ATTRBS_MEMBEROF };
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(attributes);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			enumeration = ldapContext.search(LDAP_CONTEXT_ROOT, filters, new String[] {userId}, controls);

			log.debug("Context search LDAP eseguito.");

			if(enumeration.hasMore()) { 
				SearchResult searchResult = enumeration.next();
				Attributes attribs = searchResult.getAttributes();
				String nome = extractValue(attribs, LDAP_ATTRBS_GIVENNAME);
				log.debug("Nome: " + nome);
				String cognome = extractValue(attribs, LDAP_ATTRBS_SN);
				log.debug("Cognome: " + cognome);
				List<RuoloDTO> ruoli = getListaRuoli(attribs);
				log.debug("memberOf: " + Arrays.toString(ruoli.toArray()));
	
				return new UserDTO(userId, nome, cognome, ruoli, "");
			} else {
				log.error("Utente: " + userId + " non trovato nella ricerca LDAP.");
			}
		} catch (Exception ex) {
			log.error("Errore nel recupero delle informazioni dell'utente con LDAP : " + userId);
			log.debug("Errore nel recupero delle informazioni dell'utente con LDAP : " + userId, ex);
		} finally {
			try {
				if(ldapContext!=null) {
					ldapContext.close();
				}
				if(enumeration!=null) {
					enumeration.close();
				}
			}catch(Exception ex) {
				log.debug("Errore nella chiusura delle risorse LDAP:", ex);
			}
			log.debug("Fine metodo getUser");
		}

		return null;
	}
	
	/**
	 * @param nominativoAttore
	 * @return null in caso di input errato(vuoto o di lunghezza inferiore a 3 caratteri) o errori di connessione.
	 * Altrimenti, i dati degli attori il cui nominativo 
	 * corrisponde a quello passato in input secondo la ricerca "Ambiguous Name Resolution" (https://www.rlmueller.net/AmbiguousNameResolution.htm)
	 */
	public List<AttoreDTO> getAttori(String nominativoAttore) {
		log.debug("Inizio metodo getAttori");
		if(nominativoAttore==null || nominativoAttore.length()<3) {
			log.debug("Nominativo in input non valido: " + nominativoAttore);
			log.debug("Fine metodo getAttori");
			return null;
		}
		
		if(environment.equalsIgnoreCase("prod") || environment.equalsIgnoreCase("simulaLdap")) {
		
		DirContext ldapContext = null;
		NamingEnumeration<SearchResult> enumeration = null;
		List<AttoreDTO> attori = new ArrayList<>();
		try {

			ldapContext = (DirContext)new InitialLdapContext().lookup(LDAP_CONNECTION_JNDI);

			String filters = "(&(&((!(company=PERSONALE ESTERNO))(mail=*)))(anr={0}))";
			String[] attributes = { LDAP_ATTRBS_CN, LDAP_ATTRBS_GIVENNAME, LDAP_ATTRBS_SN, LDAP_ATTRBS_DISPLAYNAME, LDAP_ATTRBS_DESCRIPTION, LDAP_ATTRBS_MAIL };
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(attributes);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			enumeration = ldapContext.search(LDAP_CONTEXT_ROOT, filters, new String[] {nominativoAttore}, controls);

			log.debug("Context search LDAP eseguito.");

			while(enumeration.hasMore()) {
				SearchResult searchResult = enumeration.next();
				Attributes attribs = searchResult.getAttributes();
				String userId = extractValue(attribs, "cn");
				log.debug("UserId: " + userId);
				String nome = extractValue(attribs, LDAP_ATTRBS_GIVENNAME);
				log.debug("Nome: " + nome);
				String cognome = extractValue(attribs, LDAP_ATTRBS_SN);
				log.debug("Cognome: " + cognome);
				String nominativo = extractValue(attribs, LDAP_ATTRBS_DISPLAYNAME);
				log.debug("Nominativo: " + nominativo);
				String servizio = extractValue(attribs, LDAP_ATTRBS_DESCRIPTION);
				log.debug("Servizio: " + servizio);
				String email = extractValue(attribs, LDAP_ATTRBS_MAIL);
				log.debug("Email: " + servizio);

				AttoreDTO attoreDTO= new AttoreDTO();
				attoreDTO.setUserid(userId);
				attoreDTO.setNome(nome);
				attoreDTO.setCognome(cognome);
				attoreDTO.setNominativo(nominativo);
				attoreDTO.setServizioDiAppartenenza(servizio);
				attoreDTO.setEmail(email);
				
				attori.add(attoreDTO);
			}	
			
			return attori;

		} catch (Exception ex) {
			log.error("Errore nel recupero delle informazioni degli attori da LDAP");
			log.debug("Errore nel recupero delle informazioni degli attori da LDAP", ex);
		} finally {
			try {
				if(ldapContext!=null) {
					ldapContext.close();
				}
				if(enumeration!=null) {
					enumeration.close();
				}
			}catch(Exception ex) {
				log.debug("Errore nella chiusura delle risorse LDAP:", ex);
			}
			log.debug("Fine metodo getAttori");
		}
		
		}else {
			return getAttoriProva(nominativoAttore);
		}

		return null;
	}

	/**
	 * @param attribs
	 * @param attribute
	 * @return il valore associato all'attributo passato come parametro.
	 * @throws NamingException
	 */
	private String extractValue(Attributes attribs, String attribute) throws NamingException {
		String value = null;
		Attribute a = attribs.get(attribute);
		if (a != null) {
			value = (String) a.get();
		}
		return value;
	}

	/**
	 * @param gruppiLdapAttribs
	 * @return la lista dei ruoli ricavati a partire dai gruppi LDAP SISTINA per l'ambiente di esecuzione corrente.
	 * @throws NamingException
	 */
	private List<RuoloDTO> getListaRuoli(Attributes gruppiLdapAttribs) throws NamingException {
		SortedSet<RuoloDTO> setRuoli = new TreeSet<>((x, y) -> (x.getCodice().compareTo(y.getCodice())));
		BasicAttribute basicAttribute = (BasicAttribute) gruppiLdapAttribs.get(LDAP_ATTRBS_MEMBEROF);
		if (basicAttribute == null) {
			return new ArrayList<>(setRuoli);
		}
		NamingEnumeration<?> values = basicAttribute.getAll();
		while (values.hasMore()) {
			String ruolo = getRuolo(values.next().toString());
			if (ruolo != null) {
				ruolo = mappaRuoloTecnico(ruolo);
				if(ruolo!=null) {
					addRuoloToSet(setRuoli, ruolo);
				}
			}
		}
		return new ArrayList<>(setRuoli);
	}

	
	/**
	 * @param gruppoLdap
	 * @return il ruolo a partire da un gruppo LDAP se il gruppo LDAP e' relativo all'ambiente 
	 * di esecuzione corrente e segue i pattern dei gruppi LDAP SISTINA.
	 */
	private String getRuolo(String gruppoLdap) {
		boolean isSameStage = false;
		String cn = "";
		log.debug("Gruppo LDAP: " + gruppoLdap);
		for (String item : gruppoLdap.split(",")) {
			String[] value = item.split("=");
			if (LDAP_ATTRBS_OU.equalsIgnoreCase(value[0]) && CURRENT_STAGE.equals(value[1])) {
				isSameStage = true;
			}
			if (LDAP_ATTRBS_CN.equalsIgnoreCase(value[0]) && value[1]!=null && value[1].startsWith(SISTINA_RUOLI_PREFIX)) {
				cn = value[1];
			}
		}
		if (isSameStage && !cn.isEmpty()) {
			log.debug("Ruolo trovato: " + cn);
			return cn;
		} else {
			log.debug("Gruppo LDAP non di interesse.");
			return null;
		}
	}
	
	/**
	 * @param ruolo
	 * @return il ruolo pasato in input se non si tratta di un ruolo tecnico, altrimenti il ruolo applicativo corrisponte al ruolo
	 * tecnico secondo la seguente logica:
	 * se ruolo=SISTINA_TECNICO_INTERNO e stage=Test --> ruolo=SISTINA_GESTORE
	 * se ruolo=SISTINA_TECNICO_INTERNO e stage=Cert --> ruolo=SISTINA_VISUALIZZATORE
	 * se ruolo=SISTINA_TECNICO_INTERNO e stage=Prod --> ruolo=SISTINA_VISUALIZZATORE
	 * se ruolo=SISTINA_TECNICO_ESTERNO e stage=Test --> ruolo=SISTINA_GESTORE
	 * se ruolo=SISTINA_TECNICO_ESTERNO e stage=Cert --> ruolo=SISTINA_VISUALIZZATORE
	 * se ruolo=SISTINA_TECNICO_ESTERNO e stage=Prod --> nessun ruolo
	 */
	private String mappaRuoloTecnico(String ruolo) {	
		if(SISTINA_RUOLO_TECNICO_INTERNO.equals(ruolo)||SISTINA_RUOLO_TECNICO_ESTERNO.equals(ruolo)) {
			if(CURRENT_STAGE.equals(STAGE_TEST)) {
				return SISTINA_RUOLO_GESTORE;
			} else {
				if(CURRENT_STAGE.equals(STAGE_CERT) || SISTINA_RUOLO_TECNICO_INTERNO.equals(ruolo)) {
					return SISTINA_RUOLO_VISUALIZZATORE;
				} else {
						return null;
				}
			}
		}
		return ruolo;
	}
	
	
	/**
	 * @param setRuoli
	 * @param ruolo
	 * Aggiunge all'insieme ordinato di input l'oggetto RuoloDTO ottenuto dal ruolo in input e ordina le strutture dei ruoli in ordine lessicografico
	 */
	private void addRuoloToSet(SortedSet<RuoloDTO> setRuoli, String ruolo) {		
		Pattern pattern = Pattern.compile(SISTINA_RUOLI_PATTERN_RUOLO);
		Matcher matcher = pattern.matcher(ruolo);
		if(matcher.matches()) {
			setRuoli.add(new RuoloDTO(ruolo, generaDescrizioneRuolo(ruolo), null));
		} else {
			pattern = Pattern.compile(SISTINA_RUOLI_PATTERN_RUOLO_STRUTTURA);
			matcher = pattern.matcher(ruolo);
			if(matcher.matches()) {
				String ruoloBase = matcher.group(1);
				String strutturaRuolo = matcher.group(2).replace("-", "");
				
				Optional<RuoloDTO> optCurrRuolo = setRuoli.stream().filter(x -> x.getCodice().equals(ruoloBase)).findAny();
				if(optCurrRuolo.isPresent()) {
					RuoloDTO currRuolo = optCurrRuolo.get();
					boolean strutturaPresente = currRuolo.getStrutture().stream().anyMatch(x -> x.equals(strutturaRuolo));
					if(!strutturaPresente) {
						currRuolo.getStrutture().add(strutturaRuolo);
						Collections.sort(currRuolo.getStrutture());
					}
				} else {
					List<String> strutture = new ArrayList<>();
					strutture.add(strutturaRuolo);
					setRuoli.add(new RuoloDTO(ruoloBase, generaDescrizioneRuolo(ruoloBase), strutture));	
				}
				
			}
		}
	}

	/**
	 * @param ruolo
	 * @return una descrizione del ruolo, determinata in base al pattern riscontrato nel ruolo
	 */
	private String generaDescrizioneRuolo(String ruolo) {		
		if(ruolo.contains(SISTINA_RUOLI_PREFIX)) {
			return ruolo.substring(SISTINA_RUOLI_PREFIX.length());
		}
		return "";
		
	}
	/**
	 * @param nominativoAttore
	 * @return null in caso di input errato(vuoto o di lunghezza inferiore a 3 caratteri) o errori di connessione.
	 * Altrimenti, i dati degli attori il cui nominativo 
	 * corrisponde a quello passato in input secondo la ricerca "Ambiguous Name Resolution" (https://www.rlmueller.net/AmbiguousNameResolution.htm)
	 */
	public List<AttoreDTO> getAttoriProva(String nominativoAttore) {
//		log.debug("Inizio metodo getAttori");
//		if(nominativoAttore==null || nominativoAttore.length()<3) {
//			log.debug("Nominativo in input non valido: " + nominativoAttore);
//			log.debug("Fine metodo getAttori");
//			return null;
//		}
//		DirContext ldapContext = null;
//		NamingEnumeration<SearchResult> enumeration = null;
		List<AttoreDTO> attori = new ArrayList<>();
//		try {
//
//			ldapContext = (DirContext)new InitialLdapContext().lookup(LDAP_CONNECTION_JNDI);
//
//			String filters = "(&(&((!(company=PERSONALE ESTERNO))(mail=*)))(anr={0}))";
//			String[] attributes = { LDAP_ATTRBS_CN, LDAP_ATTRBS_GIVENNAME, LDAP_ATTRBS_SN, LDAP_ATTRBS_DISPLAYNAME, LDAP_ATTRBS_DESCRIPTION, LDAP_ATTRBS_MAIL };
//			SearchControls controls = new SearchControls();
//			controls.setReturningAttributes(attributes);
//			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//			enumeration = ldapContext.search(LDAP_CONTEXT_ROOT, filters, new String[] {nominativoAttore}, controls);
//
//			log.debug("Context search LDAP eseguito.");
//
//			while(enumeration.hasMore()) {
//				SearchResult searchResult = enumeration.next();
//				Attributes attribs = searchResult.getAttributes();
//				String userId = extractValue(attribs, "cn");
//				log.debug("UserId: " + userId);
//				String nome = extractValue(attribs, LDAP_ATTRBS_GIVENNAME);
//				log.debug("Nome: " + nome);
//				String cognome = extractValue(attribs, LDAP_ATTRBS_SN);
//				log.debug("Cognome: " + cognome);
//				String nominativo = extractValue(attribs, LDAP_ATTRBS_DISPLAYNAME);
//				log.debug("Nominativo: " + nominativo);
//				String servizio = extractValue(attribs, LDAP_ATTRBS_DESCRIPTION);
//				log.debug("Servizio: " + servizio);
//				String email = extractValue(attribs, LDAP_ATTRBS_MAIL);
//				log.debug("Email: " + servizio);
//
//				AttoreDTO attoreDTO= new AttoreDTO();
//				attoreDTO.setUserid(userId);
//				attoreDTO.setNome(nome);
//				attoreDTO.setCognome(cognome);
//				attoreDTO.setNominativo(nominativo);
//				attoreDTO.setServizioDiAppartenenza(servizio);
//				attoreDTO.setEmail(email);
//				
//				attori.add(attoreDTO);
//			}		
			AttoreDTO attore= new AttoreDTO();
			attore.setEmail("email@esempio.it");
			attore.setNome("nome");
			attore.setCognome("cognome");
			attore.setNominativo("nome cognome");
			attore.setUserid("userid");
			attore.setServizioDiAppartenenza("3132 descrizioneservizio");
			AttoreDTO attore2= new AttoreDTO();
			attore2.setEmail("email@esempio2.it");
			attore2.setNome("nome2");
			attore2.setCognome("cognome2");
			attore2.setNominativo("nome cognome2");
			attore2.setUserid("userid2");
			attore2.setServizioDiAppartenenza("3131 descrizioneservizio2");
			attori.add(attore);
			attori.add(attore2);
			
			return attori;

//		} catch (Exception ex) {
//			log.error("Errore nel recupero delle informazioni degli attori da LDAP");
//			log.debug("Errore nel recupero delle informazioni degli attori da LDAP", ex);
//		} finally {
//			try {
//				if(ldapContext!=null) {
//					ldapContext.close();
//				}
//				if(enumeration!=null) {
//					enumeration.close();
//				}
//			}catch(Exception ex) {
//				log.debug("Errore nella chiusura delle risorse LDAP:", ex);
//			}
//			log.debug("Fine metodo getAttori");
//		}
//
//		return null;
	}

}
