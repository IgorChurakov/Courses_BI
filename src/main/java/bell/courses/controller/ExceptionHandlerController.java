package bell.courses.controller;

import bell.courses.view.ErrorView;
import bell.courses.view.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseView unhandledException(Exception e){
        log.error("response {}",e.getMessage(),e);
        return new ErrorView("Internal Server Error");
    }
}
