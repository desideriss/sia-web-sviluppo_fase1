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
public class ConfigController {
	
	
	
	
	@GetMapping("/config")
    public ResponseEntity<EnvironmentDTO> config(HttpServletResponse response) throws IOException {
		
		EnvironmentDTO env= new EnvironmentDTO();
		env.setTokenName("sistToken");
		String environment = System.getProperty("sistina.environment");
		if(environment==null) {
			env.setEnvironment("sviluppo");
		}else if(environment.equals("Cert")) {
			env.setEnvironment("certificazione");
		}else if(environment.equals("Prod")) {
			env.setEnvironment("produzione");
		}
		
		env.setRedirectLogOut("https://intranetbi.bancaditalia.it/");
		String sistinaDomain=System.getProperty("sistina.domain")!=null?System.getProperty("sistina.domain"):"https://svilsistina.bancaditalia.it";
		env.setBaseApi(sistinaDomain+"/sistina-be");
		return ResponseEntity.ok().body(env);
		
    }

}
