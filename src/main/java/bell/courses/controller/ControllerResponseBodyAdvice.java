package bell.courses.controller;

import bell.courses.view.response.ResponseView;
import bell.courses.view.response.ResultView;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Aspect for wrapping various responses in their respective views
 * @see ResponseBodyAdvice implemented interface for further explanations on advice
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@RestControllerAdvice
public class ControllerResponseBodyAdvice implements ResponseBodyAdvice {

    /**
     * @see ResponseBodyAdvice
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * Wraps {@link Boolean} instances in {@link ResultView}, {@link List} instances in their respective {@link ResponseView} and {@link ResponseView} in their respective entity views;
     * @param object controller response
     * @param methodParameter see {@link ResponseBodyAdvice}
     * @param mediaType see {@link ResponseBodyAdvice}
     * @param aClass see {@link ResponseBodyAdvice}
     * @param serverHttpRequest servlet request, see {@link ResponseBodyAdvice}
     * @param serverHttpResponse servlet response, see {@link ResponseBodyAdvice}
     * @return {@link ResultView}, List of {@link ResponseView} or {@link ResponseView}
     */
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (object instanceof Boolean){
            if ((Boolean) object){
                return new ResultView("success");
            }
        }
        if (object instanceof List) {
            List<Object> responseList = new ArrayList<>();
            for (Object node: (List)object) {
                ResponseView data = (ResponseView) node;
                responseList.add(data.wrapInListView());
            }
            return responseList;
        }
        if (object instanceof ResponseView) {
            ResponseView office = (ResponseView) object;
            return office.wrapInView();
        }
        return object;
    }
}
