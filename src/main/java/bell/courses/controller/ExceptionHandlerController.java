package bell.courses.controller;

import bell.courses.error.ApiException;
import bell.courses.view.response.ErrorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Aspect for handling various exceptions throughout the application
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Triggers when {@link MissingServletRequestParameterException} or {@link MethodArgumentTypeMismatchException} are caught
     * @param e exception
     * @return {@link ErrorView} which contains exception info
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ErrorView badRequestException(Exception e) {
        log.warn("Invalid request catched in Controller: {}", e.getMessage());
        return new ErrorView("Bad request: " + e.getMessage());
    }

    /**
     * Triggers when {@link MethodArgumentNotValidException} is caught, which means that view validation is failed
     * @param e exception
     * @return {@link ErrorView} which contains exception info
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorView invalidRequestBodyException(MethodArgumentNotValidException e){
        return new ErrorView(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * Triggers when application's {@link ApiException} are caught during request processing
     * @param e exception
     * @return {@link ErrorView} which contains exception info
     */
    @ExceptionHandler(ApiException.class)
    public ErrorView apiException(Exception e) {
        return new ErrorView(e.getMessage());
    }

    /**
     * Handles all remaining exceptions
     * @param e exception
     * @return {@link ErrorView} with "Internal Server Error" message
     */
    @ExceptionHandler(Exception.class)
    public ErrorView unhandledException(Exception e) {
        log.error("response {}", e.getMessage(), e);
        return new ErrorView("Internal Server Error");
    }
}