package it.ASDevSolution.DemoSimone.Exceptions;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//È buona pratica centralizzare la gestione degli errori per evitare codice duplicato e restituire risposte coerenti al client.
//Spring permette di farlo estendendo ResponseEntityExceptionHandler e annotando la classe con @ControllerAdvice.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Gestisce errori JSON malformati o non leggibili
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException httpMessageNotReadableException,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest) {
        ProblemDetail problemDetail = createProblemDetail(
                httpMessageNotReadableException,
                httpStatusCode,
                httpMessageNotReadableException.getMostSpecificCause().getMessage(),
                null,
                null,
                webRequest);
        return handleExceptionInternal(
                httpMessageNotReadableException,
                problemDetail,
                httpHeaders,
                httpStatusCode,
                webRequest);
    }

    //Cattura errori di validazione dei parametri (annotazioni come @NotBlank, @Size, ecc.).
    //Restituisce una mappa con i campi errati e relativi messaggi.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = methodArgumentNotValidException.getBindingResult().getAllErrors();
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return  new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

}
