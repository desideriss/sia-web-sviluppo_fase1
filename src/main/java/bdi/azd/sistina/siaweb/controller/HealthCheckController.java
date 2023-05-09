package bdi.azd.sistina.siaweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//deve essere invocato da angular per il test e2e

@RestController
@RequestMapping("actuator")
public class HealthCheckController {


	@GetMapping("health")
    public ResponseEntity<String> health(){
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}



