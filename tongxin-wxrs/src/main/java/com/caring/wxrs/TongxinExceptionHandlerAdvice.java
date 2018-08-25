package com.caring.wxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 *
 * @author james
 */
@ControllerAdvice
public class TongxinExceptionHandlerAdvice extends AbstractJsonpResponseBodyAdvice {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public TongxinExceptionHandlerAdvice() {
        super("rest controller response handler");
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity handle(Exception exception) throws Exception {
        if (exception instanceof NullPointerException) {
            throw exception;
        }
        Throwable throwable = exception.getCause();
        LOG.error("RestController Error: {}", throwable == null ? exception.fillInStackTrace().toString()
                                              : throwable.fillInStackTrace().toString());
        ExceptionRepresentation body = new ExceptionRepresentation(exception.getLocalizedMessage());
        HttpStatus responseStatus = resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<>(body, responseStatus);
    }

    public HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
