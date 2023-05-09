package bdi.azd.sistina.siaweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ValidazioneContrattoException extends Exception {

	private static final long serialVersionUID = -8192301892161757750L;
	
    public ValidazioneContrattoException() {
    	super();
    }

    public ValidazioneContrattoException(String message) {
    	super(message);
    }

    public ValidazioneContrattoException(Throwable cause) {
    	super(cause);
    }
    
    public ValidazioneContrattoException(String message, Throwable cause) {
    	super(message, cause);
    }

}
