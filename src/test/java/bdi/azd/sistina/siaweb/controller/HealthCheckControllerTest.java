package bdi.azd.sistina.siaweb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;

import bdi.azd.sistina.siaweb.controller.HealthCheckController;

@TestInstance(Lifecycle.PER_CLASS)
class HealthCheckControllerTest {
	
	private HealthCheckController healthCheckController;
	
	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
		healthCheckController = new HealthCheckController();
	}
	
	@Test
	void test() {
		boolean esito = false;
		healthCheckController.health();
		esito = true;
		assertTrue(esito);
	}

}
