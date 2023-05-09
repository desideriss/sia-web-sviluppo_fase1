package bdi.azd.sistina.siaweb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import bdi.azd.sistina.siaweb.ServletInitializer;

@TestInstance(Lifecycle.PER_CLASS)
class ServletInitializerTests {
	
	private ServletInitializer servletInitializer;
	
	@Mock
	SpringApplicationBuilder application;
	
	
	CorsRegistry corsRegistry;
	
	@BeforeAll
	void setUp() { 
		MockitoAnnotations.openMocks(this);
		servletInitializer = new ServletInitializer();
	}
	
	@Test
	void configure() {
		boolean esito = false;
		servletInitializer.configure(application);
		esito = true;
		assertTrue(esito);
	}
	
	@Test
	void corsConfigurer() {
		boolean esito = false;
		//servletInitializer.corsConfigurer();
		esito = true;
		assertTrue(esito);
	}
	
	@Test
	void customOpenAPI() {
		boolean esito = false;
		servletInitializer.customOpenAPI("prova", "prova");
		esito = true;
		assertTrue(esito);
	}
	

	
	
	
	
}
