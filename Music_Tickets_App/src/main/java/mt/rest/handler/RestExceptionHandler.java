package mt.rest.handler;

import mt.exception.NotFoundException;
import mt.exception.PersistenceException;
import mt.exception.UnacceptableAmountException;
import mt.exception.ValidationException;
import mt.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static void logException(Exception ex){
        LOGGER.error("Exception caught:", ex);
    }
    private static ErrorInfo errorInfo(HttpServletRequest request, Throwable e) {
        return new ErrorInfo(e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorInfo> persistenceException(HttpServletRequest request, PersistenceException e) {
        logException(e);
        return new ResponseEntity<>(errorInfo(request, e.getCause()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> resourceNotFound(HttpServletRequest request, NotFoundException e) {
        return new ResponseEntity<>(errorInfo(request, e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnacceptableAmountException.class)
    public ResponseEntity<ErrorInfo> insufficientAmount(HttpServletRequest request, UnacceptableAmountException e) {
        logException(e);
        return new ResponseEntity<>(errorInfo(request, e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorInfo> validation(HttpServletRequest request, ValidationException e) {
        logException(e);
        return new ResponseEntity<>(errorInfo(request, e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> accessDenied(HttpServletRequest request, AccessDeniedException e) throws AccessDeniedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw e;
        logException(e);
        return new ResponseEntity<>(errorInfo(request, e), HttpStatus.FORBIDDEN);
    }

}
