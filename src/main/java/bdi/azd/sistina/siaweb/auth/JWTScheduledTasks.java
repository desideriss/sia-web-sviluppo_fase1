package bdi.azd.sistina.siaweb.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bdi.azd.sistina.siaweb.repository.AuthRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTScheduledTasks {
	
	@Autowired
	private AuthRepo authRepo;
	
	@Value("${environment}")
	private String environment;
	
	public static String jwtSecret;

	/**
	 * Genera una nuova Secret Key utilizzata da JWT
	 */
	@Scheduled(fixedRateString = "${security.scheduled}")
	public void generateJwtSecret() {
		JWTScheduledTasks.jwtSecret = this.generateRandomUUID();
		log.info("Una nuova JWT Key è stata generata...");
		//log.trace("Random string {} at {}", JWTScheduledTasks.jwtSecret, new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if(environment.equalsIgnoreCase("prod")) {
			authRepo.deleteAll(); // Cancella tutti i token che sono stati salvati sul Database perchè non sono più validi
			log.info("La cache dei Token è stata eliminata...");
		}
	}

	  /**
	   * Genera una Stringa casuale di 256 caratteri 
	   * 
	   * @return Stringa con 256 caratteri casuali
	   */
	 private String generateRandomUUID() {
		 StringBuilder sb = new StringBuilder();
		 for(int i = 0; i < 8; i++) {
			 UUID randomUUID = UUID.randomUUID();
			 sb.append(randomUUID.toString().replaceAll("-", ""));
		 }
	    return sb.toString();
	  }
	 
}