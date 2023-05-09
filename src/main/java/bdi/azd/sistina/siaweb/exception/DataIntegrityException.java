package bdi.azd.sistina.siaweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataIntegrityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityException() {
	super();
    }

    public DataIntegrityException(String message, Throwable cause) {
	super(message, cause);
    }

    public DataIntegrityException(String message) {
	super(message);
    }

    public DataIntegrityException(Throwable cause) {
	super(cause);
    }
}
