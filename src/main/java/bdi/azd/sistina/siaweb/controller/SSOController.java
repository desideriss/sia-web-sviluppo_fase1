package bdi.azd.sistina.siaweb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bdi.azd.sistina.siaweb.dto.EnvironmentDTO;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("")
@Filter(name = "LoggingFilter")	
@Slf4j
public class SSOController {
	
	@Value("${redirectFE}")
    private String redirectFE;
	
	
	@GetMapping("/home")
	@Secured({"GESTORE", "VISUALIZZATORE", "VALIDATORE", "UTENTE", "TECNICO_INTERNO", "TECNICO_ESTERNO" })
    public String showHomePage(HttpServletResponse response) throws IOException {
		
		String sistinaDomain=System.getProperty("sistina.domain");//  https://svilsistina.bancaditalia.it
		log.info("sistinaDomain: "+sistinaDomain);
		if(sistinaDomain!=null) {
			log.info("sistinaDomain not null "+sistinaDomain);
			response.sendRedirect(sistinaDomain+redirectFE);
		}else {
			log.info("sistinaDomain is null ");
			response.sendRedirect("https://svilsistina.bancaditalia.it"+redirectFE);
		}
        return null;
    }
	
	
	

}
