package bdi.azd.sistina.siaweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ResourceNotContentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotContentException() {
	super();
    }

    public ResourceNotContentException(String message, Throwable cause) {
	super(message, cause);
    }

    public ResourceNotContentException(String message) {
	super(message);
    }

    public ResourceNotContentException(Throwable cause) {
	super(cause);
    }
}
