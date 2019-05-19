package bell.courses.controller;

import bell.courses.error.ApiException;
import bell.courses.view.response.ErrorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ErrorView badRequestException(Exception e) {
        log.warn("Invalid request catched in Controller: {}", e.getMessage());
        return new ErrorView("Bad request: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorView invalidRequestBodyException(MethodArgumentNotValidException e){
        return new ErrorView(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ErrorView apiException(Exception e) {
        return new ErrorView(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorView unhandledException(Exception e) {
        log.error("response {}", e.getMessage(), e);
        return new ErrorView("Internal Server Error");
    }
}