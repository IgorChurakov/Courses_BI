package bell.courses.controller;

import bell.courses.view.ApiException;
import bell.courses.view.ErrorView;
import bell.courses.view.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ResponseView badRequestException(Exception e) {
        log.warn("Invalid request catched in Controller: {}", e.getMessage());
        return new ErrorView("Bad request: " + e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseView apiException(Exception e) {
        return new ErrorView(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseView unhandledException(Exception e) {
        log.error("response {}", e.getMessage(), e);
        return new ErrorView("Internal Server Error");
    }
}